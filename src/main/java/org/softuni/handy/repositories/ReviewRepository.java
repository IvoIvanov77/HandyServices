package org.softuni.handy.repositories;

import org.softuni.handy.domain.entities.Review;
import org.softuni.handy.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
}
