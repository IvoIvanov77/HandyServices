package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.ProfessionalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalServiceRepository extends JpaRepository<ProfessionalService, String> {
}
