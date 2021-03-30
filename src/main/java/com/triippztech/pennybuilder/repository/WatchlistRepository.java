package com.triippztech.pennybuilder.repository;

import com.triippztech.pennybuilder.domain.Watchlist;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Watchlist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    @Query("select watchlist from Watchlist watchlist where watchlist.owner.login = ?#{principal.username}")
    List<Watchlist> findByOwnerIsCurrentUser();
}
