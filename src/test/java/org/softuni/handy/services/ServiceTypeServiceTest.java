package org.softuni.handy.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.ServiceType;
import org.softuni.handy.domain.models.service.ServiceTypeServiceModel;
import org.softuni.handy.repositories.ServiceTypeRepository;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validator;
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
    private DtoMapper mapper;
    private ServiceTypeServiceImpl serviceTypeService;
    @MockBean
    private Validator validator;


    @Before
    public void init(){
        this.mapper = new DtoMapper(new ModelMapper());
        this.serviceTypeService =
                new ServiceTypeServiceImpl(this.serviceTypeRepository, this.mapper, validator);
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
    public void getOrderedTypes_returnOrderedTypesByPriority(){
        this.seedDB();
        List<ServiceTypeServiceModel> services =
                this.serviceTypeService.getOrderedServiceTypes();

        Assert.assertEquals("Plumbing", services.get(0).getServiceName());
        Assert.assertEquals("Electrical", services.get(1).getServiceName());
        Assert.assertEquals("Cleaning", services.get(2).getServiceName());
    }

    @Test
    public void addServiceType_orderUpdateTypePriority(){
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

    @Test
    public void getOneById_returnCorrectServiceType(){
        this.serviceTypeRepository.deleteAll();
        ServiceType serviceType = new ServiceType();

        serviceType.setServiceName("plumbing");
        serviceType.setPriority(1);

        ServiceType expected = this.serviceTypeRepository.saveAndFlush(serviceType);

        ServiceTypeServiceModel actual = this.serviceTypeService.getOneById(expected.getId());
        Assert.assertEquals(expected.getServiceName(), actual.getServiceName());
        Assert.assertEquals(expected.getPriority(), actual.getPriority());
        Assert.assertEquals(expected.getId(), actual.getId());
    }



}
