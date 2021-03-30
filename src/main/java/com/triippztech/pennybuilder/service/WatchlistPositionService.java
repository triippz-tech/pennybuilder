package com.triippztech.pennybuilder.service;

import com.triippztech.pennybuilder.domain.WatchlistPosition;
import com.triippztech.pennybuilder.repository.WatchlistPositionRepository;
import com.triippztech.pennybuilder.service.dto.WatchlistPositionDTO;
import com.triippztech.pennybuilder.service.mapper.WatchlistPositionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WatchlistPosition}.
 */
@Service
@Transactional
public class WatchlistPositionService {

    private final Logger log = LoggerFactory.getLogger(WatchlistPositionService.class);

    private final WatchlistPositionRepository watchlistPositionRepository;

    private final WatchlistPositionMapper watchlistPositionMapper;

    public WatchlistPositionService(
        WatchlistPositionRepository watchlistPositionRepository,
        WatchlistPositionMapper watchlistPositionMapper
    ) {
        this.watchlistPositionRepository = watchlistPositionRepository;
        this.watchlistPositionMapper = watchlistPositionMapper;
    }

    /**
     * Save a watchlistPosition.
     *
     * @param watchlistPositionDTO the entity to save.
     * @return the persisted entity.
     */
    public WatchlistPositionDTO save(WatchlistPositionDTO watchlistPositionDTO) {
        log.debug("Request to save WatchlistPosition : {}", watchlistPositionDTO);
        WatchlistPosition watchlistPosition = watchlistPositionMapper.toEntity(watchlistPositionDTO);
        watchlistPosition = watchlistPositionRepository.save(watchlistPosition);
        return watchlistPositionMapper.toDto(watchlistPosition);
    }

    /**
     * Partially update a watchlistPosition.
     *
     * @param watchlistPositionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WatchlistPositionDTO> partialUpdate(WatchlistPositionDTO watchlistPositionDTO) {
        log.debug("Request to partially update WatchlistPosition : {}", watchlistPositionDTO);

        return watchlistPositionRepository
            .findById(watchlistPositionDTO.getId())
            .map(
                existingWatchlistPosition -> {
                    watchlistPositionMapper.partialUpdate(existingWatchlistPosition, watchlistPositionDTO);
                    return existingWatchlistPosition;
                }
            )
            .map(watchlistPositionRepository::save)
            .map(watchlistPositionMapper::toDto);
    }

    /**
     * Get all the watchlistPositions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WatchlistPositionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WatchlistPositions");
        return watchlistPositionRepository.findAll(pageable).map(watchlistPositionMapper::toDto);
    }

    /**
     * Get one watchlistPosition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WatchlistPositionDTO> findOne(Long id) {
        log.debug("Request to get WatchlistPosition : {}", id);
        return watchlistPositionRepository.findById(id).map(watchlistPositionMapper::toDto);
    }

    /**
     * Delete the watchlistPosition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WatchlistPosition : {}", id);
        watchlistPositionRepository.deleteById(id);
    }
}
