package org.softuni.handy.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.ServiceType;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
import org.softuni.handy.repositories.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ServiceTypeServiceTest {

    private static final ServiceType PLUMBER = new ServiceType();
    private static final ServiceType ELECTRICIAN = new ServiceType();
    private static final ServiceType CLEANER = new ServiceType();
    private static final ServiceTypeServiceModel PAINTER = new ServiceTypeServiceModel();

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    private ModelMapper modelMapper;
    private ServiceTypeServiceImpl serviceTypeService;


    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
        this.serviceTypeService =
                new ServiceTypeServiceImpl(this.serviceTypeRepository, this.modelMapper);
    }

    private void seedDB(){
        PLUMBER.setServiceName("Plumbing");
        PLUMBER.setPriority(1);
        ELECTRICIAN.setServiceName("Electrical");
        ELECTRICIAN.setPriority(2);
        CLEANER.setServiceName("Cleaning");
        CLEANER.setPriority(3);
        this.serviceTypeRepository.saveAndFlush(CLEANER);
        this.serviceTypeRepository.saveAndFlush(ELECTRICIAN);
        this.serviceTypeRepository.saveAndFlush(PLUMBER);
    }

    @Test
    public void getOrderedLocations_returnOrderedLocationByPriority(){
        this.seedDB();
        List<ServiceTypeServiceModel> services =
                this.serviceTypeService.getOrderedServiceTypes();

        Assert.assertEquals("Plumbing", services.get(0).getServiceName());
        Assert.assertEquals("Electrical", services.get(1).getServiceName());
        Assert.assertEquals("Cleaning", services.get(2).getServiceName());
    }

    @Test
    public void addLocation_orderUpdateLocationPriority(){
        this.seedDB();
        PAINTER.setServiceName("Painting");
        PAINTER.setPriority(1);
        this.serviceTypeService.addServiceType(PAINTER);
        List<ServiceTypeServiceModel> services =
                this.serviceTypeService.getOrderedServiceTypes();

        Assert.assertEquals("Plumbing", services.get(1).getServiceName());
        Assert.assertEquals("Electrical", services.get(2).getServiceName());
        Assert.assertEquals("Cleaning", services.get(3).getServiceName());
        Assert.assertEquals("Painting", services.get(0).getServiceName());

    }
}
