package com.triippztech.pennybuilder.web.rest;

import com.triippztech.pennybuilder.repository.WatchlistPositionRepository;
import com.triippztech.pennybuilder.service.WatchlistPositionService;
import com.triippztech.pennybuilder.service.dto.WatchlistPositionDTO;
import com.triippztech.pennybuilder.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.triippztech.pennybuilder.domain.WatchlistPosition}.
 */
@RestController
@RequestMapping("/api")
public class WatchlistPositionResource {

    private final Logger log = LoggerFactory.getLogger(WatchlistPositionResource.class);

    private static final String ENTITY_NAME = "watchlistPosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WatchlistPositionService watchlistPositionService;

    private final WatchlistPositionRepository watchlistPositionRepository;

    public WatchlistPositionResource(
        WatchlistPositionService watchlistPositionService,
        WatchlistPositionRepository watchlistPositionRepository
    ) {
        this.watchlistPositionService = watchlistPositionService;
        this.watchlistPositionRepository = watchlistPositionRepository;
    }

    /**
     * {@code POST  /watchlist-positions} : Create a new watchlistPosition.
     *
     * @param watchlistPositionDTO the watchlistPositionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new watchlistPositionDTO, or with status {@code 400 (Bad Request)} if the watchlistPosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/watchlist-positions")
    public ResponseEntity<WatchlistPositionDTO> createWatchlistPosition(@RequestBody WatchlistPositionDTO watchlistPositionDTO)
        throws URISyntaxException {
        log.debug("REST request to save WatchlistPosition : {}", watchlistPositionDTO);
        if (watchlistPositionDTO.getId() != null) {
            throw new BadRequestAlertException("A new watchlistPosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WatchlistPositionDTO result = watchlistPositionService.save(watchlistPositionDTO);
        return ResponseEntity
            .created(new URI("/api/watchlist-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /watchlist-positions/:id} : Updates an existing watchlistPosition.
     *
     * @param id the id of the watchlistPositionDTO to save.
     * @param watchlistPositionDTO the watchlistPositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchlistPositionDTO,
     * or with status {@code 400 (Bad Request)} if the watchlistPositionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the watchlistPositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/watchlist-positions/{id}")
    public ResponseEntity<WatchlistPositionDTO> updateWatchlistPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WatchlistPositionDTO watchlistPositionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WatchlistPosition : {}, {}", id, watchlistPositionDTO);
        if (watchlistPositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, watchlistPositionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!watchlistPositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WatchlistPositionDTO result = watchlistPositionService.save(watchlistPositionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, watchlistPositionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /watchlist-positions/:id} : Partial updates given fields of an existing watchlistPosition, field will ignore if it is null
     *
     * @param id the id of the watchlistPositionDTO to save.
     * @param watchlistPositionDTO the watchlistPositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchlistPositionDTO,
     * or with status {@code 400 (Bad Request)} if the watchlistPositionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the watchlistPositionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the watchlistPositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/watchlist-positions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<WatchlistPositionDTO> partialUpdateWatchlistPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WatchlistPositionDTO watchlistPositionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WatchlistPosition partially : {}, {}", id, watchlistPositionDTO);
        if (watchlistPositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, watchlistPositionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!watchlistPositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WatchlistPositionDTO> result = watchlistPositionService.partialUpdate(watchlistPositionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, watchlistPositionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /watchlist-positions} : get all the watchlistPositions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of watchlistPositions in body.
     */
    @GetMapping("/watchlist-positions")
    public ResponseEntity<List<WatchlistPositionDTO>> getAllWatchlistPositions(Pageable pageable) {
        log.debug("REST request to get a page of WatchlistPositions");
        Page<WatchlistPositionDTO> page = watchlistPositionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /watchlist-positions/:id} : get the "id" watchlistPosition.
     *
     * @param id the id of the watchlistPositionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the watchlistPositionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/watchlist-positions/{id}")
    public ResponseEntity<WatchlistPositionDTO> getWatchlistPosition(@PathVariable Long id) {
        log.debug("REST request to get WatchlistPosition : {}", id);
        Optional<WatchlistPositionDTO> watchlistPositionDTO = watchlistPositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(watchlistPositionDTO);
    }

    /**
     * {@code DELETE  /watchlist-positions/:id} : delete the "id" watchlistPosition.
     *
     * @param id the id of the watchlistPositionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/watchlist-positions/{id}")
    public ResponseEntity<Void> deleteWatchlistPosition(@PathVariable Long id) {
        log.debug("REST request to delete WatchlistPosition : {}", id);
        watchlistPositionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
