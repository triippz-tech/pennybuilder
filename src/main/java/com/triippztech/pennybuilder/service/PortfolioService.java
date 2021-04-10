package com.triippztech.pennybuilder.service;

import com.triippztech.pennybuilder.domain.Asset;
import com.triippztech.pennybuilder.domain.Portfolio;
import com.triippztech.pennybuilder.domain.PortfolioPosition;
import com.triippztech.pennybuilder.exceptions.AssetExistsInPortfolioException;
import com.triippztech.pennybuilder.repository.PortfolioPositionRepository;
import com.triippztech.pennybuilder.repository.PortfolioRepository;
import com.triippztech.pennybuilder.service.dto.*;
import com.triippztech.pennybuilder.service.iex.IEXInfoService;
import com.triippztech.pennybuilder.service.mapper.PortfolioMapper;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.triippztech.pennybuilder.service.mapper.PortfolioPositionMapper;
import liquibase.pro.packaged.Z;
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

    private final PortfolioPositionRepository portfolioPositionRepository;

    private final PortfolioPositionMapper portfolioPositionMapper;

    private final AssetService assetService;

    private final UserService userService;

    private final IEXInfoService iexInfoService;

    public PortfolioService(PortfolioRepository portfolioRepository,
                            PortfolioMapper portfolioMapper,
                            PortfolioPositionRepository portfolioPositionRepository,
                            PortfolioPositionMapper portfolioPositionMapper,
                            AssetService assetService, UserService userService,
                            IEXInfoService iexInfoService) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioMapper = portfolioMapper;
        this.portfolioPositionRepository = portfolioPositionRepository;
        this.portfolioPositionMapper = portfolioPositionMapper;
        this.assetService = assetService;
        this.userService = userService;
        this.iexInfoService = iexInfoService;
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

    public PortfolioDTO addSymbol(Long id, String symbol) throws AssetExistsInPortfolioException {
        log.debug("Request to add Symbol: {} to Portfolio: {}", symbol, id);
        var quote = iexInfoService.getQuote(symbol);
        var optionalAssetDTO = assetService.findBySymbol(symbol);
        AssetDTO assetDTO;
        if (optionalAssetDTO.isEmpty()) {
            var asset = new AssetDTO();
            asset.setSymbol(quote.getSymbol());
            asset.setName(quote.getCompanyName());
            asset.setCreatedDate(ZonedDateTime.now());
            assetDTO = assetService.save(asset);
        } else { assetDTO = optionalAssetDTO.get(); }

        if (portfolioPositionRepository.findByPortfolio_IdAndAsset_Id(id, assetDTO.getId()).isPresent())
            throw new AssetExistsInPortfolioException("Position with Symbol " + symbol + " Already Exists in Portfolio");

        Optional<PortfolioDTO> portfolioDTO = findOne(id);
        var portfolioPostion = new PortfolioPositionDTO(portfolioDTO.get(), assetDTO);
        portfolioPositionRepository.save(portfolioPositionMapper.toEntity(portfolioPostion));

        return findOne(id).get();


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
     * Get all the portfolios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Portfolio> findAllByUser(Pageable pageable, Principal principal) throws Exception {
        log.debug("Request to get all Portfolios for User: {}", principal);
        var adminUserDto = userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new Exception("User could not be found"));
        return portfolioRepository.findAllByOwner_Id(pageable, adminUserDto.getId());
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

    @Transactional(readOnly = true)
    public List<PortfolioPositionQuoteDTO> getPortfolioPositionsAsQuote(Long portfolioId) {
        log.debug("Request to get PortfolioPositions as Quote for Portfolio: {}", portfolioId);
        var positionList = portfolioPositionRepository.findAllByPortfolio_Id(portfolioId);
        if (positionList.isEmpty()) return Collections.emptyList();

        Map<String, PortfolioPosition> positionMap = positionList.stream()
            .collect(Collectors.toMap(position -> position.getAsset().getSymbol(), position -> position));

        return iexInfoService.getQuotes(
            positionList
                .stream()
                .map(portfolioPosition -> portfolioPosition.getAsset().getSymbol())
                .collect(Collectors.toList())).values().stream().map(batchStocks ->
                    new PortfolioPositionQuoteDTO(
                        positionMap.get(batchStocks.getQuote().getSymbol()),
                        batchStocks.getQuote())).collect(Collectors.toList());
    }
}
