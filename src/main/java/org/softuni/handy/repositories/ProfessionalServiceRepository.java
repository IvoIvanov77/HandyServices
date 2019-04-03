package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.Location;
import org.softuni.handy.domain.entities.ProfessionalService;
import org.softuni.handy.domain.entities.ServiceType;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalServiceRepository extends JpaRepository<ProfessionalService, String> {

    List<ProfessionalService> getAllByServiceStatus(ServiceStatus serviceStatus);

    @Query("" +
            "select ps from ProfessionalService ps where ps.location.id in :locations " +
            "and ps.serviceType.id in :types")
    List<ProfessionalService> getAllByLocationAndServiceType(
            @Param("locations") List<String> locations,
            @Param("types") List<String> types);

    Optional<ProfessionalService> getFirstByUserUsernameAndLocationAndServiceType(String username,
                                                             Location location,
                                                             ServiceType serviceType);
}
