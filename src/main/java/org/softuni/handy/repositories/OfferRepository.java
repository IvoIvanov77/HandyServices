package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.ServiceOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<ServiceOffer, String> {

    List<ServiceOffer> findAllByServiceOrderIdAndAcceptedOrderByPrice(String serviceOrder_id,
                                                                      boolean accepted);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("" +
            "update ServiceOffer o set o.accepted = true " +
            "where o.id = :id")
    void acceptOffer(@Param("id") String id);

}
