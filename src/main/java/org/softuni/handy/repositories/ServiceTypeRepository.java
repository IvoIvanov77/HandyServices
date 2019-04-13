package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.ServiceType;
import org.softuni.handy.domain.enums.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, String> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("" +
            "update ServiceType st set st.priority = (st.priority + 1) " +
            "where st.priority >= :p")
    void updatePriorities(@Param("p") int priority);

    @Query("" +
            "select distinct st from ServiceType st  join fetch st.services s " +
            "where s.serviceStatus = :status and s.location.id = :id")
    List<ServiceType> serviceTypesByLocation(@Param("id") String id,
                                             @Param("status") ServiceStatus status);

    @Query("" +
            "select distinct st from ServiceType st join fetch st.services as s " +
            "where s.user.username =:username ")
    List<ServiceType> findAllByServiceMan(@Param("username") String username);
}
