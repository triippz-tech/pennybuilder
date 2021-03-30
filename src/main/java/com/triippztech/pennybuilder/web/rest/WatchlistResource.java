package com.triippztech.pennybuilder.web.rest;

import com.triippztech.pennybuilder.repository.WatchlistRepository;
import com.triippztech.pennybuilder.service.WatchlistService;
import com.triippztech.pennybuilder.service.dto.WatchlistDTO;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.triippztech.pennybuilder.domain.Watchlist}.
 */
@RestController
@RequestMapping("/api")
public class WatchlistResource {

    private final Logger log = LoggerFactory.getLogger(WatchlistResource.class);

    private static final String ENTITY_NAME = "watchlist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WatchlistService watchlistService;

    private final WatchlistRepository watchlistRepository;

    public WatchlistResource(WatchlistService watchlistService, WatchlistRepository watchlistRepository) {
        this.watchlistService = watchlistService;
        this.watchlistRepository = watchlistRepository;
    }

    /**
     * {@code POST  /watchlists} : Create a new watchlist.
     *
     * @param watchlistDTO the watchlistDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new watchlistDTO, or with status {@code 400 (Bad Request)} if the watchlist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/watchlists")
    public ResponseEntity<WatchlistDTO> createWatchlist(@Valid @RequestBody WatchlistDTO watchlistDTO) throws URISyntaxException {
        log.debug("REST request to save Watchlist : {}", watchlistDTO);
        if (watchlistDTO.getId() != null) {
            throw new BadRequestAlertException("A new watchlist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WatchlistDTO result = watchlistService.save(watchlistDTO);
        return ResponseEntity
            .created(new URI("/api/watchlists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /watchlists/:id} : Updates an existing watchlist.
     *
     * @param id the id of the watchlistDTO to save.
     * @param watchlistDTO the watchlistDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchlistDTO,
     * or with status {@code 400 (Bad Request)} if the watchlistDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the watchlistDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/watchlists/{id}")
    public ResponseEntity<WatchlistDTO> updateWatchlist(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WatchlistDTO watchlistDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Watchlist : {}, {}", id, watchlistDTO);
        if (watchlistDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, watchlistDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!watchlistRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WatchlistDTO result = watchlistService.save(watchlistDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, watchlistDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /watchlists/:id} : Partial updates given fields of an existing watchlist, field will ignore if it is null
     *
     * @param id the id of the watchlistDTO to save.
     * @param watchlistDTO the watchlistDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchlistDTO,
     * or with status {@code 400 (Bad Request)} if the watchlistDTO is not valid,
     * or with status {@code 404 (Not Found)} if the watchlistDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the watchlistDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/watchlists/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<WatchlistDTO> partialUpdateWatchlist(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WatchlistDTO watchlistDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Watchlist partially : {}, {}", id, watchlistDTO);
        if (watchlistDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, watchlistDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!watchlistRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WatchlistDTO> result = watchlistService.partialUpdate(watchlistDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, watchlistDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /watchlists} : get all the watchlists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of watchlists in body.
     */
    @GetMapping("/watchlists")
    public ResponseEntity<List<WatchlistDTO>> getAllWatchlists(Pageable pageable) {
        log.debug("REST request to get a page of Watchlists");
        Page<WatchlistDTO> page = watchlistService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /watchlists/:id} : get the "id" watchlist.
     *
     * @param id the id of the watchlistDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the watchlistDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/watchlists/{id}")
    public ResponseEntity<WatchlistDTO> getWatchlist(@PathVariable Long id) {
        log.debug("REST request to get Watchlist : {}", id);
        Optional<WatchlistDTO> watchlistDTO = watchlistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(watchlistDTO);
    }

    /**
     * {@code DELETE  /watchlists/:id} : delete the "id" watchlist.
     *
     * @param id the id of the watchlistDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/watchlists/{id}")
    public ResponseEntity<Void> deleteWatchlist(@PathVariable Long id) {
        log.debug("REST request to delete Watchlist : {}", id);
        watchlistService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
