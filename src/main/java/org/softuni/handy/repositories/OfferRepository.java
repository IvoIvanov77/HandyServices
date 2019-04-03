package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.ServiceOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<ServiceOffer, String> {

    List<ServiceOffer> findAllByServiceOrderIdAndAcceptedOrderByPrice(String serviceOrder_id,
                                                                      boolean accepted);

}
