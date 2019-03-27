package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("" +
            "update Location l set l.priority = (l.priority + 1) " +
            "where l.priority >= :p")
    void updatePriorities(@Param("p") int priority);
}
