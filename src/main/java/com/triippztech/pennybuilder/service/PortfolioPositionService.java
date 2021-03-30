package com.triippztech.pennybuilder.service;

import com.triippztech.pennybuilder.domain.PortfolioPosition;
import com.triippztech.pennybuilder.repository.PortfolioPositionRepository;
import com.triippztech.pennybuilder.service.dto.PortfolioPositionDTO;
import com.triippztech.pennybuilder.service.mapper.PortfolioPositionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PortfolioPosition}.
 */
@Service
@Transactional
public class PortfolioPositionService {

    private final Logger log = LoggerFactory.getLogger(PortfolioPositionService.class);

    private final PortfolioPositionRepository portfolioPositionRepository;

    private final PortfolioPositionMapper portfolioPositionMapper;

    public PortfolioPositionService(
        PortfolioPositionRepository portfolioPositionRepository,
        PortfolioPositionMapper portfolioPositionMapper
    ) {
        this.portfolioPositionRepository = portfolioPositionRepository;
        this.portfolioPositionMapper = portfolioPositionMapper;
    }

    /**
     * Save a portfolioPosition.
     *
     * @param portfolioPositionDTO the entity to save.
     * @return the persisted entity.
     */
    public PortfolioPositionDTO save(PortfolioPositionDTO portfolioPositionDTO) {
        log.debug("Request to save PortfolioPosition : {}", portfolioPositionDTO);
        PortfolioPosition portfolioPosition = portfolioPositionMapper.toEntity(portfolioPositionDTO);
        portfolioPosition = portfolioPositionRepository.save(portfolioPosition);
        return portfolioPositionMapper.toDto(portfolioPosition);
    }

    /**
     * Partially update a portfolioPosition.
     *
     * @param portfolioPositionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PortfolioPositionDTO> partialUpdate(PortfolioPositionDTO portfolioPositionDTO) {
        log.debug("Request to partially update PortfolioPosition : {}", portfolioPositionDTO);

        return portfolioPositionRepository
            .findById(portfolioPositionDTO.getId())
            .map(
                existingPortfolioPosition -> {
                    portfolioPositionMapper.partialUpdate(existingPortfolioPosition, portfolioPositionDTO);
                    return existingPortfolioPosition;
                }
            )
            .map(portfolioPositionRepository::save)
            .map(portfolioPositionMapper::toDto);
    }

    /**
     * Get all the portfolioPositions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PortfolioPositionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PortfolioPositions");
        return portfolioPositionRepository.findAll(pageable).map(portfolioPositionMapper::toDto);
    }

    /**
     * Get one portfolioPosition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PortfolioPositionDTO> findOne(Long id) {
        log.debug("Request to get PortfolioPosition : {}", id);
        return portfolioPositionRepository.findById(id).map(portfolioPositionMapper::toDto);
    }

    /**
     * Delete the portfolioPosition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PortfolioPosition : {}", id);
        portfolioPositionRepository.deleteById(id);
    }
}
