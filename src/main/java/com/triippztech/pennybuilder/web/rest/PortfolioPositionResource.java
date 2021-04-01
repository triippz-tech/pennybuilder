package com.triippztech.pennybuilder.web.rest;

import com.triippztech.pennybuilder.repository.PortfolioPositionRepository;
import com.triippztech.pennybuilder.service.PortfolioPositionService;
import com.triippztech.pennybuilder.service.dto.PortfolioPositionDTO;
import com.triippztech.pennybuilder.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.triippztech.pennybuilder.domain.PortfolioPosition}.
 */
@RestController
@RequestMapping("/api")
public class PortfolioPositionResource {

    private final Logger log = LoggerFactory.getLogger(PortfolioPositionResource.class);

    private static final String ENTITY_NAME = "portfolioPosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PortfolioPositionService portfolioPositionService;

    private final PortfolioPositionRepository portfolioPositionRepository;

    public PortfolioPositionResource(
        PortfolioPositionService portfolioPositionService,
        PortfolioPositionRepository portfolioPositionRepository
    ) {
        this.portfolioPositionService = portfolioPositionService;
        this.portfolioPositionRepository = portfolioPositionRepository;
    }

    /**
     * {@code POST  /portfolio-positions} : Create a new portfolioPosition.
     *
     * @param portfolioPositionDTO the portfolioPositionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new portfolioPositionDTO, or with status {@code 400 (Bad Request)} if the portfolioPosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/portfolio-positions")
    public ResponseEntity<PortfolioPositionDTO> createPortfolioPosition(@Valid @RequestBody PortfolioPositionDTO portfolioPositionDTO)
        throws URISyntaxException {
        log.debug("REST request to save PortfolioPosition : {}", portfolioPositionDTO);
        if (portfolioPositionDTO.getId() != null) {
            throw new BadRequestAlertException("A new portfolioPosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PortfolioPositionDTO result = portfolioPositionService.save(portfolioPositionDTO);
        return ResponseEntity
            .created(new URI("/api/portfolio-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /portfolio-positions/:id} : Updates an existing portfolioPosition.
     *
     * @param id the id of the portfolioPositionDTO to save.
     * @param portfolioPositionDTO the portfolioPositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portfolioPositionDTO,
     * or with status {@code 400 (Bad Request)} if the portfolioPositionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the portfolioPositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/portfolio-positions/{id}")
    public ResponseEntity<PortfolioPositionDTO> updatePortfolioPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PortfolioPositionDTO portfolioPositionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PortfolioPosition : {}, {}", id, portfolioPositionDTO);
        if (portfolioPositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, portfolioPositionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!portfolioPositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PortfolioPositionDTO result = portfolioPositionService.save(portfolioPositionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, portfolioPositionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /portfolio-positions/:id} : Partial updates given fields of an existing portfolioPosition, field will ignore if it is null
     *
     * @param id the id of the portfolioPositionDTO to save.
     * @param portfolioPositionDTO the portfolioPositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portfolioPositionDTO,
     * or with status {@code 400 (Bad Request)} if the portfolioPositionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the portfolioPositionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the portfolioPositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/portfolio-positions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PortfolioPositionDTO> partialUpdatePortfolioPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PortfolioPositionDTO portfolioPositionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PortfolioPosition partially : {}, {}", id, portfolioPositionDTO);
        if (portfolioPositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, portfolioPositionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!portfolioPositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PortfolioPositionDTO> result = portfolioPositionService.partialUpdate(portfolioPositionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, portfolioPositionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /portfolio-positions} : get all the portfolioPositions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of portfolioPositions in body.
     */
    @GetMapping("/portfolio-positions")
    public ResponseEntity<List<PortfolioPositionDTO>> getAllPortfolioPositions(Pageable pageable) {
        log.debug("REST request to get a page of PortfolioPositions");
        Page<PortfolioPositionDTO> page = portfolioPositionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /portfolio-positions/:id} : get the "id" portfolioPosition.
     *
     * @param id the id of the portfolioPositionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portfolioPositionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portfolio-positions/{id}")
    public ResponseEntity<PortfolioPositionDTO> getPortfolioPosition(@PathVariable Long id) {
        log.debug("REST request to get PortfolioPosition : {}", id);
        Optional<PortfolioPositionDTO> portfolioPositionDTO = portfolioPositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(portfolioPositionDTO);
    }

    /**
     * {@code DELETE  /portfolio-positions/:id} : delete the "id" portfolioPosition.
     *
     * @param id the id of the portfolioPositionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/portfolio-positions/{id}")
    public ResponseEntity<Void> deletePortfolioPosition(@PathVariable Long id) {
        log.debug("REST request to delete PortfolioPosition : {}", id);
        portfolioPositionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
