package com.triippztech.pennybuilder.service;

import com.triippztech.pennybuilder.domain.Portfolio;
import com.triippztech.pennybuilder.repository.PortfolioRepository;
import com.triippztech.pennybuilder.service.dto.PortfolioDTO;
import com.triippztech.pennybuilder.service.mapper.PortfolioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Portfolio}.
 */
@Service
@Transactional
public class PortfolioService {

    private final Logger log = LoggerFactory.getLogger(PortfolioService.class);

    private final PortfolioRepository portfolioRepository;

    private final PortfolioMapper portfolioMapper;

    public PortfolioService(PortfolioRepository portfolioRepository, PortfolioMapper portfolioMapper) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioMapper = portfolioMapper;
    }

    /**
     * Save a portfolio.
     *
     * @param portfolioDTO the entity to save.
     * @return the persisted entity.
     */
    public PortfolioDTO save(PortfolioDTO portfolioDTO) {
        log.debug("Request to save Portfolio : {}", portfolioDTO);
        Portfolio portfolio = portfolioMapper.toEntity(portfolioDTO);
        portfolio = portfolioRepository.save(portfolio);
        return portfolioMapper.toDto(portfolio);
    }

    /**
     * Partially update a portfolio.
     *
     * @param portfolioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PortfolioDTO> partialUpdate(PortfolioDTO portfolioDTO) {
        log.debug("Request to partially update Portfolio : {}", portfolioDTO);

        return portfolioRepository
            .findById(portfolioDTO.getId())
            .map(
                existingPortfolio -> {
                    portfolioMapper.partialUpdate(existingPortfolio, portfolioDTO);
                    return existingPortfolio;
                }
            )
            .map(portfolioRepository::save)
            .map(portfolioMapper::toDto);
    }

    /**
     * Get all the portfolios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PortfolioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Portfolios");
        return portfolioRepository.findAll(pageable).map(portfolioMapper::toDto);
    }

    /**
     * Get one portfolio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PortfolioDTO> findOne(Long id) {
        log.debug("Request to get Portfolio : {}", id);
        return portfolioRepository.findById(id).map(portfolioMapper::toDto);
    }

    /**
     * Delete the portfolio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Portfolio : {}", id);
        portfolioRepository.deleteById(id);
    }
}
