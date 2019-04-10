package org.softuni.handy.services;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.handy.domain.entities.UserRole;
import org.softuni.handy.domain.enums.Role;
import org.softuni.handy.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRoleServiceTest {
    private UserRoleService userRoleService;

    @Autowired
    private UserRoleRepository roleRepository;

    private List<String> autorities;

    @Before
    public void init(){
       this.userRoleService = new UserRoleServiceImpl(this.roleRepository);
       this.autorities = Arrays.stream(Role.values())
               .map(Enum::name)
               .collect(Collectors.toList());
       seedDb();
    }

    private void seedDb(){
        for (String authority : autorities) {
            UserRole role = new UserRole();
            role.setAuthority(authority);
            this.roleRepository.save(role);
        }
    }

    @Test
    public void userRoles_returnAllRoles(){
        List<UserRole> allRoles = this.userRoleService.userRoles();
        int expectedSize = this.autorities.size();
        int actualSize = allRoles.size();
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void userRole_returnCorrectRole(){
        String roleName = this.autorities.get(0);
        UserRole role = this.userRoleService.userRole(roleName);
        Assert.assertEquals(roleName, role.getAuthority());
    }
}
