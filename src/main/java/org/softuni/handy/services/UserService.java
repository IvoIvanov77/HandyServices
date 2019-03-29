package org.softuni.handy.services;

import org.softuni.handy.domain.entities.UserRole;
import org.softuni.handy.domain.enums.Role;
import org.softuni.handy.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    //add all roles with lower authority
    Set<UserRole> setUserRoles(Role role);

    boolean registerUser(UserServiceModel serviceModel);

    List<UserServiceModel> allUsers();

    boolean editUser(UserServiceModel serviceModel);
}
