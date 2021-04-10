package com.triippztech.pennybuilder.repository;

import com.triippztech.pennybuilder.domain.PortfolioPosition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the PortfolioPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortfolioPositionRepository extends JpaRepository<PortfolioPosition, Long> {
    List<PortfolioPosition> findAllByPortfolio_Id(Long portfolioId);
    Optional<PortfolioPosition> findByPortfolio_IdAndAsset_Id(Long portfolioId, Long assetId);
}
