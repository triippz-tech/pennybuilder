package com.triippztech.pennybuilder.repository;

import com.triippztech.pennybuilder.domain.Portfolio;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Portfolio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    @Query("select portfolio from Portfolio portfolio where portfolio.owner.login = ?#{principal.username}")
    List<Portfolio> findByOwnerIsCurrentUser();
}
