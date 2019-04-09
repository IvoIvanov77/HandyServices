package org.softuni.handy.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.handy.domain.entities.User;
import org.softuni.handy.domain.entities.UserRole;
import org.softuni.handy.domain.models.service.UserServiceModel;
import org.softuni.handy.repositories.UserRepository;
import org.softuni.handy.repositories.UserRoleRepository;
import org.softuni.handy.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrderServiceTest {

    private static final User IVO = new User();
    private static final User PESHO = new User();
    private static final User IVAN = new User();
    private static final UserServiceModel MISHO = new UserServiceModel();


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    private DtoMapper mapper;

    @MockBean
    private Validator validator;


    private BCryptPasswordEncoder encoder;

    private UserServiceImpl userService;


    @Before
    public void init(){
        this.mapper = new DtoMapper(new ModelMapper());
        this.encoder = new BCryptPasswordEncoder();
        this.userService = new UserServiceImpl(this.userRepository, mapper, validator, encoder, this.roleRepository);
    }

    private void setFields(){
        IVO.setUsername("ivaylo");
        PESHO.setUsername("petar");
        IVAN.setUsername("ivan");
        IVO.setEmail("i@i.i");
        PESHO.setEmail("p@p.p");
        IVAN.setEmail("iv@i.i");
        IVO.setPassword("123");
        PESHO.setPassword("123");
        IVAN.setPassword("123");
    }

    private void seedDB(){
        this.userRepository.deleteAll();
        this.setFields();
        this.userRepository.save(IVO);
        this.userRepository.save(PESHO);
        this.userRepository.save(IVAN);
    }

    private void setRoles(){
        UserRole r1 = new UserRole();
        UserRole r2 = new UserRole();
        UserRole r3 = new UserRole();
        r1.setAuthority("ROLE_1");
        r2.setAuthority("ROLE_2");
        r3.setAuthority("ROLE_3");

        IVAN.setAuthorities(new HashSet<UserRole>() {{
            add(r1);
        }});

        PESHO.setAuthorities(new HashSet<UserRole>() {{
            add(r1);
            add(r2);
        }});

        IVO.setAuthorities(new HashSet<UserRole>() {{
            add(r1);
            add(r2);
            add(r3);
        }});
    }


    @Test
    public void loadUserByUsername_returnCorrectUser(){
        this.seedDB();
        UserDetails user = this.userService.loadUserByUsername("ivaylo");
        Assert.assertNotNull(user);
        Assert.assertEquals(IVO.getUsername(), user.getUsername());
        Assert.assertEquals(IVO.getPassword(), user.getPassword());
    }

    @Test
    public void registerUser_returnTrueWithCorrectParameters(){
        this.seedDB();
        MISHO.setUsername("michail");
        MISHO.setEmail("m@m.m");
        MISHO.setPassword("123");
        Assert.assertTrue(this.userService.registerUser(MISHO));
    }

    @Test
    public void registerUser_returnFalseWithDuplicateUsername(){
        this.seedDB();
        MISHO.setUsername("ivaylo");
        MISHO.setEmail("m@m.m");
        MISHO.setPassword("123");
        Assert.assertFalse(this.userService.registerUser(MISHO));
    }

    @Test
    public void registerUser_returnFalseWithDuplicateEmail(){
        this.seedDB();
        MISHO.setUsername("michail");
        MISHO.setEmail("i@i.i");
        MISHO.setPassword("123");
        Assert.assertFalse(this.userService.registerUser(MISHO));
    }

    @Test
    public void allUsers_returnCorrectCount(){
        this.setFields();
        this.userService.registerUser(this.mapper.map(IVO, UserServiceModel.class));
        this.userService.registerUser(this.mapper.map(PESHO, UserServiceModel.class));
        this.userService.registerUser(this.mapper.map(IVAN, UserServiceModel.class));
        Assert.assertEquals(3, this.userService.allUsers().size());
    }

    @Test
    public void allUsers_returnUsersInCorrectOrder(){
        this.setFields();

        this.setRoles();

        this.seedDB();

        List<UserServiceModel> allUsers = this.userService.allUsers();
        Assert.assertEquals("ivaylo", allUsers.get(0).getUsername());
        Assert.assertEquals("petar", allUsers.get(1).getUsername());
        Assert.assertEquals("ivan", allUsers.get(2).getUsername());
    }

    @Test
    public void editUser_editUserCorrectly(){
        this.setFields();

        this.setRoles();

        this.seedDB();

        UserDetails userDetails = this.userService.loadUserByUsername("petar");
        Assert.assertEquals(2, userDetails.getAuthorities().size());
        UserRole r1 = new UserRole();

        UserServiceModel serviceModel = new UserServiceModel();
        serviceModel.setUsername("petar");
        serviceModel.setAuthorities(new HashSet<UserRole>(){{
            add(r1);
        }});

        boolean result = this.userService.editUser(serviceModel);
        Assert.assertTrue(result);

        UserDetails edited = this.userService.loadUserByUsername("petar");

        Assert.assertEquals(1, userDetails.getAuthorities().size());
    }

}
