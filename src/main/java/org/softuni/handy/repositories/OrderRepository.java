package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.Location;
import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.entities.ServiceOrder;
import org.softuni.handy.domain.entities.ServiceType;
import org.softuni.handy.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<ServiceOrder, String> {


    List<ServiceOrder> findAllByLocationInAndServiceTypeInAndOrderStatusInOrderByScheduledDate(
            Collection<Location> location,
            Collection<ServiceType> serviceType,
            Collection<OrderStatus> statuses);

    List<ServiceOrder> findAllByUserUsernameAndOrderStatus(String username, OrderStatus orderStatus);

    List<ServiceOrder> findAllByOrderStatusAndProfessionalServiceIn(OrderStatus orderStatus,
                                                                    Collection<ProfessionalService> professionalService);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("" +
            "update ServiceOrder o set o.orderStatus = org.softuni.handy.domain.enums.OrderStatus.ACCEPTED, " +
            "o.professionalService = :professionalService " +
            "where o.id = :id")
    void acceptOrder(@Param("id") String id,
                     @Param("professionalService") ProfessionalService professionalService);


}
