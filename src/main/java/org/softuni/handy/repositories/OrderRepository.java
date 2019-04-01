package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.Location;
import org.softuni.handy.domain.entities.ServiceOrder;
import org.softuni.handy.domain.entities.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<ServiceOrder, String> {


    List<ServiceOrder> findAllByLocationInAndServiceTypeIn(Collection<Location> location,
                                                           Collection<ServiceType> serviceType);


}
