package com.triippztech.pennybuilder.repository;

import com.triippztech.pennybuilder.domain.PortfolioPosition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PortfolioPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortfolioPositionRepository extends JpaRepository<PortfolioPosition, Long> {}
