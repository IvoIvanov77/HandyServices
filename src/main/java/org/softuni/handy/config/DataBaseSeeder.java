package org.softuni.handy.config;

import org.softuni.handy.domain.entities.UserRole;
import org.softuni.handy.domain.enums.Role;
import org.softuni.handy.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataBaseSeeder {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public DataBaseSeeder(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @PostConstruct
    public void seed() {
        Role[] roles = Role.values();
        if (this.userRoleRepository.count() < roles.length) {
            List<UserRole> userRoles = Arrays.stream(roles)
                    .map(UserRole::new)
                    .collect(Collectors.toList());
            this.userRoleRepository.saveAll(userRoles);
        }
    }
}
