package org.softuni.handy.services;

import org.softuni.handy.domain.entities.UserRole;
import org.softuni.handy.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository roleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserRole> userRoles(){
        return this.roleRepository.findAll();
    }

    @Override
    public UserRole userRole(String authority){
        return this.roleRepository.getUserRoleByAuthority(authority);
    }
}
