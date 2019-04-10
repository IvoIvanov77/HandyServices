package org.softuni.handy.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.*;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.softuni.handy.domain.models.service.ProfessionalServiceModel;
import org.softuni.handy.repositories.*;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProfessionalServiceServiceTest {

    private ProfessionalServiceServiceImpl professionalServiceService;

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

    private UserService userService;
    @MockBean
    private Validator validator;

    private DtoMapper mapper;

    private ProfessionalService professionalService;

    @MockBean
    private  BCryptPasswordEncoder passwordEncoder;

    @Before
    public void init() {
        this.mapper = new DtoMapper(new ModelMapper());
        this.userService = new UserServiceImpl(userRepository, mapper, validator,
                passwordEncoder, roleRepository);
        this.professionalServiceService =
                new ProfessionalServiceServiceImpl(serviceRepository,
                        mapper, validator, userService);
    }

    private void seedDB() {
        UserRole r = new UserRole();
        r.setAuthority("Role1");
        UserRole role = this.roleRepository.saveAndFlush(r);

        User u2 = new User();
        u2.setUsername("pro");
        u2.setEmail("email-pro");
        u2.setPassword("123");
        u2.setAuthorities(new HashSet<>(this.roleRepository.findAll()));
        User pro = this.userRepository.save(u2);

        Location l = new Location();
        l.setPriority(1);
        l.setTown("town");
        Location location = this.locationRepository.save(l);

        ServiceType st = new ServiceType();
        st.setServiceName("newService");
        st.setPriority(1);
        ServiceType serviceType = this.serviceTypeRepository.save(st);

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
    }

    private ProfessionalService createNewProfessionalService() {
        //same user, location and type
        ProfessionalService newProfessionalService = new ProfessionalService();
        newProfessionalService.setUser(this.professionalService.getUser());
        newProfessionalService.setLocation(this.professionalService.getLocation());
        newProfessionalService.setServiceType(this.professionalService.getServiceType());

        newProfessionalService.setServiceStatus(ServiceStatus.REJECTED);
        newProfessionalService.setServiceDescription("new service");
        newProfessionalService.setSlogan("new service-slogan");
        newProfessionalService.setFirstName("new proFName");
        newProfessionalService.setLastName("new proLName");
        newProfessionalService.setPhoneNumber("new pro-phoneNumber");
        return newProfessionalService;
    }

    @Test
    public void registerService_successfullyCreateServiceInDB() {
        seedDB();
        this.professionalServiceService.registerService(
                this.mapper.map(this.professionalService, ProfessionalServiceModel.class)
        );
        Assert.assertEquals(1, this.serviceRepository.count());
        ServiceStatus actual = this.serviceRepository.findAll().get(0).getServiceStatus();
        Assert.assertEquals(ServiceStatus.PENDING, actual);

    }

    @Test
    public void registerService_oneUserCanNotRegisterTwoServicesWithSameLocationAndType() {
        seedDB();
        this.professionalServiceService.registerService(
                this.mapper.map(this.professionalService, ProfessionalServiceModel.class)
        );

        Assert.assertFalse(this.professionalServiceService.registerService(
                this.mapper.map(this.createNewProfessionalService(), ProfessionalServiceModel.class)
        ));
    }

    @Test
    public void getAllByStatus_returnCorrectResult() {
        seedDB();
        this.serviceRepository.save(this.professionalService);
        this.serviceRepository.save(this.createNewProfessionalService());
        List<ProfessionalServiceModel> resultList =
                this.professionalServiceService.getAllByStatus(ServiceStatus.REJECTED.name());
        Assert.assertEquals(1, resultList.size());
        Assert.assertEquals(resultList.get(0).getSlogan(),
                this.createNewProfessionalService().getSlogan());
    }

    @Test
    public void getAllByUsername_returnCorrectResult() {
        seedDB();
        this.serviceRepository.save(this.professionalService);
        this.serviceRepository.save(this.createNewProfessionalService());
        List<ProfessionalServiceModel> resultList =
                this.professionalServiceService.getAllByUsername("pro");
        Assert.assertEquals(2, resultList.size());

    }

    @Test
    public void getAllByUsername_returnEmptyListIfThereIsNoSuchOrder() {
        seedDB();
        this.serviceRepository.save(this.professionalService);
        this.serviceRepository.save(this.createNewProfessionalService());
        List<ProfessionalServiceModel> resultList =
                this.professionalServiceService.getAllByUsername("ivo");
        Assert.assertEquals(0, resultList.size());

    }

    @Test
    public void getAllByUsername_returnCorrectResultWithDifferentUsers() {
        seedDB();
        UserRole r = new UserRole();
        r.setAuthority("Role1");
        UserRole role = this.roleRepository.saveAndFlush(r);

        User u1 = new User();
        u1.setUsername("ivo");
        u1.setEmail("email");
        u1.setPassword("123");
        u1.setAuthorities(new HashSet<>(this.roleRepository.findAll()));
        User client = this.userRepository.save(u1);

        this.serviceRepository.save(this.createNewProfessionalService());
        this.professionalService.setUser(client);
        this.serviceRepository.save(this.professionalService);

        List<ProfessionalServiceModel> resultList =
                this.professionalServiceService.getAllByUsername("ivo");
        Assert.assertEquals(1, resultList.size());
    }

    @Test
    public void getOneByID_returnCorrectObject() {
        seedDB();
        ProfessionalService professionalService =
                this.serviceRepository.save(this.professionalService);

        ProfessionalServiceModel professionalServiceModel =
                this.professionalServiceService.getOneByID(professionalService.getId());

        Assert.assertEquals("pro", professionalServiceModel.getUser().getUsername());

    }

    @Test
    public void searchByLocationAndType_returnCorrectResultList() {
        seedDB();
        List<String> locations = new ArrayList<>();
        List<String> serviceTypes = new ArrayList<>();
        locations.add(this.locationRepository.findAll().get(0).getId());
        locations.add("fake id");
        serviceTypes.add(this.serviceTypeRepository.findAll().get(0).getId());
        serviceTypes.add("fake id");
        this.serviceRepository.save(this.professionalService);
        this.serviceRepository.save(this.createNewProfessionalService());

        List<ProfessionalServiceModel> resultList =
                this.professionalServiceService.searchByLocationAndType(locations,serviceTypes);

        Assert.assertEquals(2, resultList.size());
    }

    @Test
    public void editService_successfullyUpdateServiceInDB() {
        seedDB();
        ProfessionalService toUpdate = this.serviceRepository.save(this.professionalService);
        ProfessionalServiceModel updater =
                this.mapper.map(this.professionalService, ProfessionalServiceModel.class);
        updater.setServiceStatus(ServiceStatus.PENDING);

        this.professionalServiceService.editService(updater);

        Assert.assertEquals(ServiceStatus.PENDING,
                this.serviceRepository.findAll().get(0).getServiceStatus());

    }

    @Test
    public void editService_returnFalseIfUserIsNull() {
        seedDB();
        ProfessionalService toUpdate = this.serviceRepository.save(this.professionalService);
        ProfessionalServiceModel updater =
                this.mapper.map(this.professionalService, ProfessionalServiceModel.class);
        updater.setUser(null);

        Assert.assertFalse(this.professionalServiceService.editService(updater));
    }

}