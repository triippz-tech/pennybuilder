package com.triippztech.pennybuilder.service;

import com.triippztech.pennybuilder.domain.Asset;
import com.triippztech.pennybuilder.repository.AssetRepository;
import com.triippztech.pennybuilder.service.criteria.AssetCriteria;
import com.triippztech.pennybuilder.service.dto.AssetDTO;
import com.triippztech.pennybuilder.service.iex.IEXInfoService;
import com.triippztech.pennybuilder.service.mapper.AssetMapper;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zankowski.iextrading4j.api.refdata.v1.SymbolDescription;

/**
 * Service Implementation for managing {@link Asset}.
 */
@Service
@Transactional
public class AssetService {

    private final Logger log = LoggerFactory.getLogger(AssetService.class);

    private final AssetRepository assetRepository;

    private final AssetMapper assetMapper;

    private final IEXInfoService iexInfoService;

    public AssetService(AssetRepository assetRepository, AssetMapper assetMapper, IEXInfoService iexInfoService) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
        this.iexInfoService = iexInfoService;
    }

    /**
     * Save a asset.
     *
     * @param assetDTO the entity to save.
     * @return the persisted entity.
     */
    public AssetDTO save(AssetDTO assetDTO) {
        log.debug("Request to save Asset : {}", assetDTO);
        Asset asset = assetMapper.toEntity(assetDTO);
        asset = assetRepository.save(asset);
        return assetMapper.toDto(asset);
    }

    /**
     * Partially update a asset.
     *
     * @param assetDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssetDTO> partialUpdate(AssetDTO assetDTO) {
        log.debug("Request to partially update Asset : {}", assetDTO);

        return assetRepository
            .findById(assetDTO.getId())
            .map(
                existingAsset -> {
                    assetMapper.partialUpdate(existingAsset, assetDTO);
                    return existingAsset;
                }
            )
            .map(assetRepository::save)
            .map(assetMapper::toDto);
    }

    /**
     * Get all the assets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assets");
        return assetRepository.findAll(pageable).map(assetMapper::toDto);
    }

    /**
     * Get one asset by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssetDTO> findOne(Long id) {
        log.debug("Request to get Asset : {}", id);
        return assetRepository.findById(id).map(assetMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<AssetDTO> findBySymbol(String symbol) {
        log.debug("Request to get Asset : {}", symbol);
        return assetRepository.findBySymbolEquals(symbol).map(assetMapper::toDto);
    }

    /**
     * Delete the asset by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Asset : {}", id);
        assetRepository.deleteById(id);
    }

    public List<SymbolDescription> queryAssets(AssetCriteria criteria) {
        return iexInfoService.searchAssets(criteria.getSymbol().getContains());//.stream()
//            .map(symbolDescription -> {
//                var found = assetRepository.findByNameEquals(symbolDescription.getSecurityName());
//                if (found.isPresent())
//                    return assetMapper.toDto(found.get());
//                return save(new AssetDTO(
//                    symbolDescription.getSymbol(),
//                    symbolDescription.getSecurityName(),
//                    ZonedDateTime.now()));
//            })
//            .collect(Collectors.toList());
    }
}
