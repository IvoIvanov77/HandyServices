package org.softuni.handy.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.*;
import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.service.ServiceOrderServiceModel;
import org.softuni.handy.repositories.*;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OfferServiceTest {

    private OrderServiceImpl orderService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private ProfessionalServiceRepository serviceRepository;
    @Autowired
    private UserRoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private Validator validator;

    private DtoMapper mapper;

    private ServiceOrder serviceOrder;

    @Before
    public void init() {
        this.mapper = new DtoMapper(new ModelMapper());
        this.orderService =
                new OrderServiceImpl(orderRepository, mapper, validator,
                        locationRepository, serviceTypeRepository, serviceRepository);
    }

    private void seedDB() {
        UserRole r = new UserRole();
        r.setAuthority("Role1");
        UserRole role = this.roleRepository.saveAndFlush(r);

        User u1 = new User();
        u1.setUsername("ivo");
        u1.setEmail("email");
        u1.setPassword("123");
        u1.setAuthorities(new HashSet<>(this.roleRepository.findAll()));
        User client = this.userRepository.save(u1);

        Location l = new Location();
        l.setPriority(1);
        l.setTown("town");
        Location location = this.locationRepository.save(l);

        ServiceType st = new ServiceType();
        st.setServiceName("newService");
        st.setPriority(1);
        ServiceType serviceType = this.serviceTypeRepository.save(st);

        this.serviceOrder = new ServiceOrder();
        serviceOrder.setOrderStatus(OrderStatus.EXPIRED);
        serviceOrder.setAddress("address");
        serviceOrder.setProblemDescription("problem");
        serviceOrder.setScheduledDate(LocalDate.of(2019, 5, 5));
        serviceOrder.setFirstName("fname");
        serviceOrder.setLastName("lname");
        serviceOrder.setPhoneNumber("phone");
        serviceOrder.setLocation(location);
        serviceOrder.setServiceType(serviceType);
        serviceOrder.setUser(client);

        User u2 = new User();
        u2.setUsername("pro");
        u2.setEmail("email-pro");
        u2.setPassword("123");
        u2.setAuthorities(new HashSet<>(this.roleRepository.findAll()));
        User pro = this.userRepository.save(u2);
        ProfessionalService professionalService = new ProfessionalService();
        professionalService.setUser(pro);
        professionalService.setLocation(location);
        professionalService.setServiceType(serviceType);
        professionalService.setServiceStatus(ServiceStatus.APPROVED);
        professionalService.setServiceDescription("service");
        professionalService.setSlogan("service-slogan");
        professionalService.setFirstName("proFName");
        professionalService.setLastName("proLName");
        professionalService.setPhoneNumber("pro-phoneNumber");
        this.serviceRepository.save(professionalService);

    }

    @Test
    public void createOrder_successfullyCreateOrderInDB() {
        this.seedDB();
        ServiceOrderServiceModel serviceModel =
                this.mapper.map(this.serviceOrder, ServiceOrderServiceModel.class);
        this.orderService.createOrder(serviceModel);
        ServiceOrder actual = this.orderRepository.findAll().get(0);
        Assert.assertEquals(1, this.orderRepository.count());
        Assert.assertEquals("address", actual.getAddress());
        Assert.assertEquals("problem", actual.getProblemDescription());
    }

    @Test
    public void createOrder_setOrderStatusToPending() {
        this.seedDB();
        ServiceOrderServiceModel serviceModel =
                this.mapper.map(this.serviceOrder, ServiceOrderServiceModel.class);
        this.orderService.createOrder(serviceModel);
        ServiceOrder actual = this.orderRepository.findAll().get(0);
        Assert.assertEquals(OrderStatus.PENDING, actual.getOrderStatus());

    }

    @Test
    public void getOrdersByUserRegisteredServices_returnCorrectOrders(){
        this.seedDB();
        serviceOrder.setOffers(new ArrayList<>());
        serviceOrder.setOrderStatus(OrderStatus.PENDING);
        this.orderRepository.save(this.serviceOrder);


        List<ServiceOrderServiceModel> result = this.orderService
                .getOrdersByUserRegisteredServices("pro", false,
                        OrderStatus.PENDING);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals("ivo", result.get(0).getUser().getUsername());

    }


    @Test
    public void getById_returnCorrectResult() {
        seedDB();
        ServiceOrder expected = this.orderRepository.save(this.serviceOrder);

        ServiceOrderServiceModel actual = this.orderService.getById(expected.getId());

        Assert.assertEquals(expected.getAddress(), actual.getAddress());
        Assert.assertEquals(expected.getUser(), actual.getUser());
    }

    @Test
    public void getOrdersByUserAndStatus_returnCorrectResult(){
        seedDB();
        ServiceOrder expected = this.orderRepository.save(this.serviceOrder);
        List<ServiceOrderServiceModel> result =
                this.orderService.getOrdersByUserAndStatus("ivo", OrderStatus.EXPIRED);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(expected.getAddress(), result.get(0).getAddress());
    }

    @Test
    public void getOrdersByUserAndStatus_returnEmptyList(){
        seedDB();
        this.orderRepository.save(this.serviceOrder);
        List<ServiceOrderServiceModel> result =
                this.orderService.getOrdersByUserAndStatus("ivo", OrderStatus.PENDING);
        Assert.assertEquals(0, result.size());

    }

    @Test
    public void getOrdersByStatusAndServiceUserName_returnCorrectResult(){
        seedDB();
        this.serviceOrder.setProfessionalService(this.serviceRepository.findAll().get(0));
        this.orderRepository.save(this.serviceOrder);
        List<ServiceOrderServiceModel> result =
                this.orderService.getOrdersByStatusAndServiceUserName("pro", OrderStatus.EXPIRED);
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void getOrdersByStatusAndServiceUserName_returnEmptyResultList(){
        seedDB();
        this.orderRepository.save(this.serviceOrder);
        List<ServiceOrderServiceModel> result =
                this.orderService.getOrdersByStatusAndServiceUserName("ivo", OrderStatus.EXPIRED);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void updateOrder_successfullyUpdateOrderInDB(){
        seedDB();
        ServiceOrder toUpdate = this.orderRepository.save(this.serviceOrder);
        this.orderService.updateOrderStatus(toUpdate.getId(), OrderStatus.CLAIMED);
        ServiceOrderServiceModel actual = this.orderService.getById(toUpdate.getId());
        Assert.assertEquals(OrderStatus.CLAIMED, actual.getOrderStatus());
    }




}
