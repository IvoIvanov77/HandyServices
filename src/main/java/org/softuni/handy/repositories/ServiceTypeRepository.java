package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, String> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("" +
            "update ServiceType st set st.priority = (st.priority + 1) " +
            "where st.priority >= :p")
    void updatePriorities(@Param("p") int priority);
}
