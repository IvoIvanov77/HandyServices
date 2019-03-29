package org.softuni.handy.services;

import org.softuni.handy.domain.entities.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> userRoles();

    UserRole userRole(String authority);
}
