package org.softuni.handy.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.*;
import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.binding.AcceptOfferBindingModel;
import org.softuni.handy.domain.models.service.OfferServiceModel;
import org.softuni.handy.repositories.*;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OfferServiceTest {

    private OfferService offerService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OfferRepository offerRepository;
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

    private ServiceOffer serviceOffer;

    private ProfessionalService professionalService;

    @Before
    public void init() {
        this.mapper = new DtoMapper(new ModelMapper());
        this.offerService =
                new OfferServiceImpl(offerRepository, serviceRepository,
                        orderRepository, mapper, validator);
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

        ServiceOrder serviceOrder = new ServiceOrder();
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
        serviceOrder.setOffers(Collections.emptyList());
        this.orderRepository.save(serviceOrder);

        User u2 = new User();
        u2.setUsername("pro");
        u2.setEmail("email-pro");
        u2.setPassword("123");
        u2.setAuthorities(new HashSet<>(this.roleRepository.findAll()));
        User pro = this.userRepository.save(u2);

        this.professionalService = new ProfessionalService();
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

        this.serviceOffer = new ServiceOffer();
//        this.serviceOffer.setProfessionalService(professionalService);
        this.serviceOffer.setServiceOrder(serviceOrder);
        this.serviceOffer.setHours(1);
        this.serviceOffer.setPrice(BigDecimal.TEN);
    }

    @Test
    public void createOffer_successfullyAddOfferInDB(){
        seedDB();
        OfferServiceModel serviceModel = this.mapper.map(this.serviceOffer,
                OfferServiceModel.class);
        this.offerService.createOffer(serviceModel, "pro");

        ServiceOrder order = this.offerRepository.findAll().get(0).getServiceOrder();

        Assert.assertEquals(1, this.offerRepository.count());
    }

    @Test
    public void getAllByOrder_returnCorrectResultList(){
        seedDB();
        ServiceOrder serviceOrder = this.orderRepository.findAll().get(0);

        serviceOffer.setProfessionalService(this.professionalService);
        ServiceOffer serviceOffer = this.offerRepository.save(this.serviceOffer);

        List<OfferServiceModel> resultList = this.offerService.getAllByOrder(serviceOrder.getId());

        Assert.assertEquals(1, resultList.size());
        Assert.assertEquals(serviceOffer.getId(), resultList.get(0).getId());

    }

    @Test
    public void acceptOffer_workCorrectly(){
        seedDB();

        OfferServiceModel serviceModel = this.mapper.map(this.serviceOffer,
                OfferServiceModel.class);
        this.offerService.createOffer(serviceModel, "pro");
        ServiceOffer offer = this.offerRepository.findAll().get(0);

        AcceptOfferBindingModel bindingModel = this.mapper.map(offer,
                AcceptOfferBindingModel.class);

        this.offerService.acceptOffer(bindingModel);

        Assert.assertTrue(this.offerRepository.getOne(offer.getId()).isAccepted());
        Assert.assertEquals(OrderStatus.ACCEPTED,
                this.offerRepository.getOne(offer.getId()).getServiceOrder().getOrderStatus());

    }





}
