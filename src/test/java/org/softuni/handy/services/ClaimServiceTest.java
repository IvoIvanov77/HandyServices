package org.softuni.handy.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.*;
import org.softuni.handy.domain.enums.OrderStatus;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.service.ClaimServiceModel;
import org.softuni.handy.domain.models.service.CreateClaimServiceModel;
import org.softuni.handy.repositories.*;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClaimServiceTest {

    private ClaimService claimService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClaimRepository claimRepository;
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

    private DtoMapper mapper;

    private Claim claim;

    @Before
    public void init() {
        this.mapper = new DtoMapper(new ModelMapper());
        this.claimService =
                new ClaimServiceImpl(claimRepository, orderRepository,
                        serviceRepository, mapper);
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
        serviceOrder.setProfessionalService(professionalService);
        this.orderRepository.save(serviceOrder);

        this.claim = new Claim();
        this.claim.setServiceOrder(serviceOrder);
        this.claim.setProfessionalService(professionalService);
        this.claim.setReason("reason");
    }

    @Test
    public void openClaim_successfullyCreateClaimInDB(){
        seedDB();
        CreateClaimServiceModel serviceModel = new CreateClaimServiceModel();
        serviceModel.setProfessionalService(this.claim.getProfessionalService().getId());
        serviceModel.setServiceOrder(this.claim.getServiceOrder().getId());
        serviceModel.setReason(this.claim.getReason());

        Assert.assertTrue(this.claimService.openClaim(serviceModel));

    }

    @Test(expected = UnsupportedOperationException.class)
    public void openClaim_mustThrow(){
        seedDB();
        this.claimRepository.save(this.claim);
        CreateClaimServiceModel serviceModel = new CreateClaimServiceModel();
        serviceModel.setProfessionalService(this.claim.getProfessionalService().getId());
        serviceModel.setServiceOrder(this.claim.getServiceOrder().getId());
        serviceModel.setReason(this.claim.getReason());
        this.claimService.openClaim(serviceModel);
    }

    @Test
    public void getUserClaims_returnCorrectResultList(){
        seedDB();
        this.claimRepository.save(this.claim);
        List<ClaimServiceModel> resultList =
                this.claimService.getUserClaims("pro",false, true);

        Assert.assertEquals(1, resultList.size());
    }

    @Test
    public void getUserClaims_returnEmptyResultList(){
        seedDB();
        this.claimRepository.save(this.claim);
        List<ClaimServiceModel> resultList =
                this.claimService.getUserClaims("pro",false, false);

        Assert.assertEquals(0, resultList.size());
    }

    @Test
    public void closeClaim_returnTrue(){
        seedDB();
        Claim expected = this.claimRepository.save(this.claim);
        Assert.assertTrue(this.claimService.closeClaim(expected.getId()));

    }

    @Test
    public void closeClaim_returnFalse(){
        seedDB();
        this.claimRepository.save(this.claim);
        Assert.assertFalse(this.claimService.closeClaim("fake-id"));

    }

    @Test
    public void closeClaim_worksCorrectly(){
        seedDB();
        Claim expected = this.claimRepository.save(this.claim);
        this.claimService.closeClaim(expected.getId());
        Assert.assertTrue(this.claimRepository.getOne(expected.getId()).isClosed());


    }





}
