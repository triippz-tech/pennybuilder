package com.triippztech.pennybuilder.repository;

import com.triippztech.pennybuilder.domain.Asset;
import com.triippztech.pennybuilder.service.dto.AssetDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Asset entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset> {
    Optional<Asset> findByNameEquals(String name);
    Optional<Asset> findBySymbolEquals(String asset);
}
