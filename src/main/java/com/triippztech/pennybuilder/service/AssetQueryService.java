package com.triippztech.pennybuilder.service;

import com.triippztech.pennybuilder.domain.*; // for static metamodels
import com.triippztech.pennybuilder.domain.Asset;
import com.triippztech.pennybuilder.repository.AssetRepository;
import com.triippztech.pennybuilder.service.criteria.AssetCriteria;
import com.triippztech.pennybuilder.service.dto.AssetDTO;
import com.triippztech.pennybuilder.service.mapper.AssetMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Asset} entities in the database.
 * The main input is a {@link AssetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AssetDTO} or a {@link Page} of {@link AssetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssetQueryService extends QueryService<Asset> {

    private final Logger log = LoggerFactory.getLogger(AssetQueryService.class);

    private final AssetRepository assetRepository;

    private final AssetMapper assetMapper;

    public AssetQueryService(AssetRepository assetRepository, AssetMapper assetMapper) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }

    /**
     * Return a {@link List} of {@link AssetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AssetDTO> findByCriteria(AssetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Asset> specification = createSpecification(criteria);
        return assetMapper.toDto(assetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AssetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetDTO> findByCriteria(AssetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Asset> specification = createSpecification(criteria);
        return assetRepository.findAll(specification, page).map(assetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Asset> specification = createSpecification(criteria);
        return assetRepository.count(specification);
    }

    /**
     * Function to convert {@link AssetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Asset> createSpecification(AssetCriteria criteria) {
        Specification<Asset> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Asset_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Asset_.name));
            }
            if (criteria.getSymbol() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSymbol(), Asset_.symbol));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Asset_.createdDate));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), Asset_.updatedDate));
            }
            if (criteria.getPortfolioPositionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPortfolioPositionsId(),
                            root -> root.join(Asset_.portfolioPositions, JoinType.LEFT).get(PortfolioPosition_.id)
                        )
                    );
            }
            if (criteria.getWatchlistPositionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWatchlistPositionsId(),
                            root -> root.join(Asset_.watchlistPositions, JoinType.LEFT).get(WatchlistPosition_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
