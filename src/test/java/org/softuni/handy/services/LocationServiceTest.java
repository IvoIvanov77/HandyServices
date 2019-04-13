package org.softuni.handy.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.Location;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.softuni.handy.exception.InvalidServiceModelException;
import org.softuni.handy.exception.ResourceNotFoundException;
import org.softuni.handy.repositories.LocationRepository;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LocationServiceTest {

    private static final Location SOFIA = new Location();
    private static final Location PLOVDIV = new Location();
    private static final Location VARNA = new Location();
    private static final LocationServiceModel PRESLAV = new LocationServiceModel();

    @Autowired
    private LocationRepository locationRepository;
    private DtoMapper mapper;
    private LocationServiceImpl locationService;

    private Validator validator;


    @Before
    public void init(){
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.mapper = new DtoMapper(new ModelMapper());
        this.locationService =
                new LocationServiceImpl(this.locationRepository, this.mapper, validator);
    }

    private void seedDB(){
        SOFIA.setTown("Sofia");
        PLOVDIV.setTown("Plovdiv");
        VARNA.setTown("Varna");
        SOFIA.setPriority(1);
        PLOVDIV.setPriority(2);
        VARNA.setPriority(3);
        this.locationRepository.saveAndFlush(VARNA);
        this.locationRepository.saveAndFlush(PLOVDIV);
        this.locationRepository.saveAndFlush(SOFIA);

    }

    @Test
    public void getOrderedLocations_returnOrderedLocationByPriority(){
        this.seedDB();
        List<LocationServiceModel> locations = this.locationService.getOrderedLocations();

        Assert.assertEquals("Sofia", locations.get(0).getTown());
        Assert.assertEquals("Plovdiv", locations.get(1).getTown());
        Assert.assertEquals("Varna", locations.get(2).getTown());
    }

    @Test
    public void addLocation_updateOrderLocationPriority(){
        this.seedDB();
        PRESLAV.setTown("Great Preslav");
        PRESLAV.setPriority(1);
        PRESLAV.setLocationPicture("picture");
        this.locationService.addLocation(PRESLAV);
        List<LocationServiceModel> locations = this.locationService.getOrderedLocations();

        Assert.assertEquals("Sofia", locations.get(1).getTown());
        Assert.assertEquals("Plovdiv", locations.get(2).getTown());
        Assert.assertEquals("Varna", locations.get(3).getTown());
        Assert.assertEquals("Great Preslav", locations.get(0).getTown());
    }

    @Test(expected = InvalidServiceModelException.class)
    public void addLocation_mustThrow(){
        this.seedDB();
        PRESLAV.setTown(null);
        PRESLAV.setPriority(1);
        this.locationService.addLocation(PRESLAV);
    }

    @Test
    public void addLocation_returnFalse(){
        this.validator = Mockito.mock(Validator.class);
        this.mapper = new DtoMapper(new ModelMapper());
        this.locationService =
                new LocationServiceImpl(this.locationRepository, this.mapper, validator);
        this.seedDB();
        PRESLAV.setTown(null);
        PRESLAV.setPriority(1);
        Mockito.when(validator.validate(PRESLAV)).thenReturn(Collections.emptySet());
        boolean actual = this.locationService.addLocation(PRESLAV);
        Assert.assertFalse(actual);
    }

    @Test
    public void getOneById_returnCorrectLocation(){
        this.locationRepository.deleteAll();
        Location location = new Location();

        location.setTown("town");
        location.setPriority(1);

        Location expected = this.locationRepository.saveAndFlush(location);

        LocationServiceModel actual = this.locationService.getOneById(expected.getId());
        Assert.assertEquals(expected.getTown(), actual.getTown());
        Assert.assertEquals(expected.getPriority(), actual.getPriority());
        Assert.assertEquals(expected.getId(), actual.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getOneById_mustThrow(){
        this.locationRepository.deleteAll();
        Location location = new Location();

        location.setTown("town");
        location.setPriority(1);
        this.locationRepository.save(location);

         this.locationService.getOneById("fake_id");

    }
}
