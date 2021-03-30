package com.triippztech.pennybuilder.service;

import com.triippztech.pennybuilder.domain.Watchlist;
import com.triippztech.pennybuilder.repository.WatchlistRepository;
import com.triippztech.pennybuilder.service.dto.WatchlistDTO;
import com.triippztech.pennybuilder.service.mapper.WatchlistMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Watchlist}.
 */
@Service
@Transactional
public class WatchlistService {

    private final Logger log = LoggerFactory.getLogger(WatchlistService.class);

    private final WatchlistRepository watchlistRepository;

    private final WatchlistMapper watchlistMapper;

    public WatchlistService(WatchlistRepository watchlistRepository, WatchlistMapper watchlistMapper) {
        this.watchlistRepository = watchlistRepository;
        this.watchlistMapper = watchlistMapper;
    }

    /**
     * Save a watchlist.
     *
     * @param watchlistDTO the entity to save.
     * @return the persisted entity.
     */
    public WatchlistDTO save(WatchlistDTO watchlistDTO) {
        log.debug("Request to save Watchlist : {}", watchlistDTO);
        Watchlist watchlist = watchlistMapper.toEntity(watchlistDTO);
        watchlist = watchlistRepository.save(watchlist);
        return watchlistMapper.toDto(watchlist);
    }

    /**
     * Partially update a watchlist.
     *
     * @param watchlistDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WatchlistDTO> partialUpdate(WatchlistDTO watchlistDTO) {
        log.debug("Request to partially update Watchlist : {}", watchlistDTO);

        return watchlistRepository
            .findById(watchlistDTO.getId())
            .map(
                existingWatchlist -> {
                    watchlistMapper.partialUpdate(existingWatchlist, watchlistDTO);
                    return existingWatchlist;
                }
            )
            .map(watchlistRepository::save)
            .map(watchlistMapper::toDto);
    }

    /**
     * Get all the watchlists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WatchlistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Watchlists");
        return watchlistRepository.findAll(pageable).map(watchlistMapper::toDto);
    }

    /**
     * Get one watchlist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WatchlistDTO> findOne(Long id) {
        log.debug("Request to get Watchlist : {}", id);
        return watchlistRepository.findById(id).map(watchlistMapper::toDto);
    }

    /**
     * Delete the watchlist by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Watchlist : {}", id);
        watchlistRepository.deleteById(id);
    }
}
