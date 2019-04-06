package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, String> {

    List<Claim> findAllByClosed(boolean closed);

    List<Claim> findAllByClosedAndProfessionalService_User_Username(boolean closed, String username);

    List<Claim> findAllByClosedAndServiceOrder_User_Username(boolean closed, String username);


}
