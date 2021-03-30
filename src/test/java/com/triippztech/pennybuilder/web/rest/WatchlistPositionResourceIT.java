package com.triippztech.pennybuilder.web.rest;

import static com.triippztech.pennybuilder.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.triippztech.pennybuilder.IntegrationTest;
import com.triippztech.pennybuilder.domain.WatchlistPosition;
import com.triippztech.pennybuilder.repository.WatchlistPositionRepository;
import com.triippztech.pennybuilder.service.dto.WatchlistPositionDTO;
import com.triippztech.pennybuilder.service.mapper.WatchlistPositionMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WatchlistPositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WatchlistPositionResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/watchlist-positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WatchlistPositionRepository watchlistPositionRepository;

    @Autowired
    private WatchlistPositionMapper watchlistPositionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWatchlistPositionMockMvc;

    private WatchlistPosition watchlistPosition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchlistPosition createEntity(EntityManager em) {
        WatchlistPosition watchlistPosition = new WatchlistPosition().createdDate(DEFAULT_CREATED_DATE).updatedDate(DEFAULT_UPDATED_DATE);
        return watchlistPosition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchlistPosition createUpdatedEntity(EntityManager em) {
        WatchlistPosition watchlistPosition = new WatchlistPosition().createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);
        return watchlistPosition;
    }

    @BeforeEach
    public void initTest() {
        watchlistPosition = createEntity(em);
    }

    @Test
    @Transactional
    void createWatchlistPosition() throws Exception {
        int databaseSizeBeforeCreate = watchlistPositionRepository.findAll().size();
        // Create the WatchlistPosition
        WatchlistPositionDTO watchlistPositionDTO = watchlistPositionMapper.toDto(watchlistPosition);
        restWatchlistPositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(watchlistPositionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WatchlistPosition in the database
        List<WatchlistPosition> watchlistPositionList = watchlistPositionRepository.findAll();
        assertThat(watchlistPositionList).hasSize(databaseSizeBeforeCreate + 1);
        WatchlistPosition testWatchlistPosition = watchlistPositionList.get(watchlistPositionList.size() - 1);
        assertThat(testWatchlistPosition.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testWatchlistPosition.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createWatchlistPositionWithExistingId() throws Exception {
        // Create the WatchlistPosition with an existing ID
        watchlistPosition.setId(1L);
        WatchlistPositionDTO watchlistPositionDTO = watchlistPositionMapper.toDto(watchlistPosition);

        int databaseSizeBeforeCreate = watchlistPositionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWatchlistPositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(watchlistPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchlistPosition in the database
        List<WatchlistPosition> watchlistPositionList = watchlistPositionRepository.findAll();
        assertThat(watchlistPositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWatchlistPositions() throws Exception {
        // Initialize the database
        watchlistPositionRepository.saveAndFlush(watchlistPosition);

        // Get all the watchlistPositionList
        restWatchlistPositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(watchlistPosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }

    @Test
    @Transactional
    void getWatchlistPosition() throws Exception {
        // Initialize the database
        watchlistPositionRepository.saveAndFlush(watchlistPosition);

        // Get the watchlistPosition
        restWatchlistPositionMockMvc
            .perform(get(ENTITY_API_URL_ID, watchlistPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(watchlistPosition.getId().intValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingWatchlistPosition() throws Exception {
        // Get the watchlistPosition
        restWatchlistPositionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWatchlistPosition() throws Exception {
        // Initialize the database
        watchlistPositionRepository.saveAndFlush(watchlistPosition);

        int databaseSizeBeforeUpdate = watchlistPositionRepository.findAll().size();

        // Update the watchlistPosition
        WatchlistPosition updatedWatchlistPosition = watchlistPositionRepository.findById(watchlistPosition.getId()).get();
        // Disconnect from session so that the updates on updatedWatchlistPosition are not directly saved in db
        em.detach(updatedWatchlistPosition);
        updatedWatchlistPosition.createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);
        WatchlistPositionDTO watchlistPositionDTO = watchlistPositionMapper.toDto(updatedWatchlistPosition);

        restWatchlistPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchlistPositionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(watchlistPositionDTO))
            )
            .andExpect(status().isOk());

        // Validate the WatchlistPosition in the database
        List<WatchlistPosition> watchlistPositionList = watchlistPositionRepository.findAll();
        assertThat(watchlistPositionList).hasSize(databaseSizeBeforeUpdate);
        WatchlistPosition testWatchlistPosition = watchlistPositionList.get(watchlistPositionList.size() - 1);
        assertThat(testWatchlistPosition.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWatchlistPosition.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingWatchlistPosition() throws Exception {
        int databaseSizeBeforeUpdate = watchlistPositionRepository.findAll().size();
        watchlistPosition.setId(count.incrementAndGet());

        // Create the WatchlistPosition
        WatchlistPositionDTO watchlistPositionDTO = watchlistPositionMapper.toDto(watchlistPosition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchlistPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchlistPositionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(watchlistPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchlistPosition in the database
        List<WatchlistPosition> watchlistPositionList = watchlistPositionRepository.findAll();
        assertThat(watchlistPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWatchlistPosition() throws Exception {
        int databaseSizeBeforeUpdate = watchlistPositionRepository.findAll().size();
        watchlistPosition.setId(count.incrementAndGet());

        // Create the WatchlistPosition
        WatchlistPositionDTO watchlistPositionDTO = watchlistPositionMapper.toDto(watchlistPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchlistPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(watchlistPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchlistPosition in the database
        List<WatchlistPosition> watchlistPositionList = watchlistPositionRepository.findAll();
        assertThat(watchlistPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWatchlistPosition() throws Exception {
        int databaseSizeBeforeUpdate = watchlistPositionRepository.findAll().size();
        watchlistPosition.setId(count.incrementAndGet());

        // Create the WatchlistPosition
        WatchlistPositionDTO watchlistPositionDTO = watchlistPositionMapper.toDto(watchlistPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchlistPositionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(watchlistPositionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WatchlistPosition in the database
        List<WatchlistPosition> watchlistPositionList = watchlistPositionRepository.findAll();
        assertThat(watchlistPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWatchlistPositionWithPatch() throws Exception {
        // Initialize the database
        watchlistPositionRepository.saveAndFlush(watchlistPosition);

        int databaseSizeBeforeUpdate = watchlistPositionRepository.findAll().size();

        // Update the watchlistPosition using partial update
        WatchlistPosition partialUpdatedWatchlistPosition = new WatchlistPosition();
        partialUpdatedWatchlistPosition.setId(watchlistPosition.getId());

        partialUpdatedWatchlistPosition.createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restWatchlistPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatchlistPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWatchlistPosition))
            )
            .andExpect(status().isOk());

        // Validate the WatchlistPosition in the database
        List<WatchlistPosition> watchlistPositionList = watchlistPositionRepository.findAll();
        assertThat(watchlistPositionList).hasSize(databaseSizeBeforeUpdate);
        WatchlistPosition testWatchlistPosition = watchlistPositionList.get(watchlistPositionList.size() - 1);
        assertThat(testWatchlistPosition.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWatchlistPosition.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateWatchlistPositionWithPatch() throws Exception {
        // Initialize the database
        watchlistPositionRepository.saveAndFlush(watchlistPosition);

        int databaseSizeBeforeUpdate = watchlistPositionRepository.findAll().size();

        // Update the watchlistPosition using partial update
        WatchlistPosition partialUpdatedWatchlistPosition = new WatchlistPosition();
        partialUpdatedWatchlistPosition.setId(watchlistPosition.getId());

        partialUpdatedWatchlistPosition.createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restWatchlistPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatchlistPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWatchlistPosition))
            )
            .andExpect(status().isOk());

        // Validate the WatchlistPosition in the database
        List<WatchlistPosition> watchlistPositionList = watchlistPositionRepository.findAll();
        assertThat(watchlistPositionList).hasSize(databaseSizeBeforeUpdate);
        WatchlistPosition testWatchlistPosition = watchlistPositionList.get(watchlistPositionList.size() - 1);
        assertThat(testWatchlistPosition.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWatchlistPosition.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingWatchlistPosition() throws Exception {
        int databaseSizeBeforeUpdate = watchlistPositionRepository.findAll().size();
        watchlistPosition.setId(count.incrementAndGet());

        // Create the WatchlistPosition
        WatchlistPositionDTO watchlistPositionDTO = watchlistPositionMapper.toDto(watchlistPosition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchlistPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, watchlistPositionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(watchlistPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchlistPosition in the database
        List<WatchlistPosition> watchlistPositionList = watchlistPositionRepository.findAll();
        assertThat(watchlistPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWatchlistPosition() throws Exception {
        int databaseSizeBeforeUpdate = watchlistPositionRepository.findAll().size();
        watchlistPosition.setId(count.incrementAndGet());

        // Create the WatchlistPosition
        WatchlistPositionDTO watchlistPositionDTO = watchlistPositionMapper.toDto(watchlistPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchlistPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(watchlistPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchlistPosition in the database
        List<WatchlistPosition> watchlistPositionList = watchlistPositionRepository.findAll();
        assertThat(watchlistPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWatchlistPosition() throws Exception {
        int databaseSizeBeforeUpdate = watchlistPositionRepository.findAll().size();
        watchlistPosition.setId(count.incrementAndGet());

        // Create the WatchlistPosition
        WatchlistPositionDTO watchlistPositionDTO = watchlistPositionMapper.toDto(watchlistPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchlistPositionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(watchlistPositionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WatchlistPosition in the database
        List<WatchlistPosition> watchlistPositionList = watchlistPositionRepository.findAll();
        assertThat(watchlistPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWatchlistPosition() throws Exception {
        // Initialize the database
        watchlistPositionRepository.saveAndFlush(watchlistPosition);

        int databaseSizeBeforeDelete = watchlistPositionRepository.findAll().size();

        // Delete the watchlistPosition
        restWatchlistPositionMockMvc
            .perform(delete(ENTITY_API_URL_ID, watchlistPosition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WatchlistPosition> watchlistPositionList = watchlistPositionRepository.findAll();
        assertThat(watchlistPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
