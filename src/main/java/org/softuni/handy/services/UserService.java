package org.softuni.handy.services;

import org.softuni.handy.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean registerUser(UserServiceModel serviceModel);

    List<UserServiceModel> allUsers();
}
