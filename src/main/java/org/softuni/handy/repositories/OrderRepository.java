package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.ServiceOrder;
import org.softuni.handy.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<ServiceOrder, String> {
}
