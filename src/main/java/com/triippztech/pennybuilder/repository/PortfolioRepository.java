package com.triippztech.pennybuilder.repository;

import com.triippztech.pennybuilder.domain.Portfolio;
import java.util.List;
import java.util.Optional;

import com.triippztech.pennybuilder.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Portfolio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    @Query("select portfolio from Portfolio portfolio where portfolio.owner.login = ?#{principal.username}")
    List<Portfolio> findByOwnerIsCurrentUser();

    Page<Portfolio> findAllByOwner(Pageable pageable, User user);
    Page<Portfolio> findAllByOwner_Id(Pageable pageable, Long ownerId);
}
