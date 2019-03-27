package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    UserRole getUserRoleByAuthority(String authority);
}
