package com.triippztech.pennybuilder.repository;

import com.triippztech.pennybuilder.domain.WatchlistPosition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WatchlistPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WatchlistPositionRepository extends JpaRepository<WatchlistPosition, Long> {}
