package org.softuni.handy.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.Location;
import org.softuni.handy.domain.models.service.LocationServiceModel;
import org.softuni.handy.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private ModelMapper modelMapper;
    private LocationServiceImpl locationService;


    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
        this.locationService =
                new LocationServiceImpl(this.locationRepository, this.modelMapper);
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
    public void addLocation_orderUpdateLocationPriority(){
        this.seedDB();
        PRESLAV.setTown("Great Preslav");
        PRESLAV.setPriority(1);
        this.locationService.addLocation(PRESLAV);
        List<LocationServiceModel> locations = this.locationService.getOrderedLocations();

        Assert.assertEquals("Sofia", locations.get(1).getTown());
        Assert.assertEquals("Plovdiv", locations.get(2).getTown());
        Assert.assertEquals("Varna", locations.get(3).getTown());
        Assert.assertEquals("Great Preslav", locations.get(0).getTown());
    }
}
