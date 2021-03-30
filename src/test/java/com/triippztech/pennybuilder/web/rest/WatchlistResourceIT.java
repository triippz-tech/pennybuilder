package com.triippztech.pennybuilder.web.rest;

import static com.triippztech.pennybuilder.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.triippztech.pennybuilder.IntegrationTest;
import com.triippztech.pennybuilder.domain.Watchlist;
import com.triippztech.pennybuilder.repository.WatchlistRepository;
import com.triippztech.pennybuilder.service.dto.WatchlistDTO;
import com.triippztech.pennybuilder.service.mapper.WatchlistMapper;
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
 * Integration tests for the {@link WatchlistResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WatchlistResourceIT {

    private static final String DEFAULT_WATCHLIST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WATCHLIST_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/watchlists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private WatchlistMapper watchlistMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWatchlistMockMvc;

    private Watchlist watchlist;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Watchlist createEntity(EntityManager em) {
        Watchlist watchlist = new Watchlist()
            .watchlistName(DEFAULT_WATCHLIST_NAME)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return watchlist;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Watchlist createUpdatedEntity(EntityManager em) {
        Watchlist watchlist = new Watchlist()
            .watchlistName(UPDATED_WATCHLIST_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return watchlist;
    }

    @BeforeEach
    public void initTest() {
        watchlist = createEntity(em);
    }

    @Test
    @Transactional
    void createWatchlist() throws Exception {
        int databaseSizeBeforeCreate = watchlistRepository.findAll().size();
        // Create the Watchlist
        WatchlistDTO watchlistDTO = watchlistMapper.toDto(watchlist);
        restWatchlistMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(watchlistDTO)))
            .andExpect(status().isCreated());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeCreate + 1);
        Watchlist testWatchlist = watchlistList.get(watchlistList.size() - 1);
        assertThat(testWatchlist.getWatchlistName()).isEqualTo(DEFAULT_WATCHLIST_NAME);
        assertThat(testWatchlist.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testWatchlist.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testWatchlist.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createWatchlistWithExistingId() throws Exception {
        // Create the Watchlist with an existing ID
        watchlist.setId(1L);
        WatchlistDTO watchlistDTO = watchlistMapper.toDto(watchlist);

        int databaseSizeBeforeCreate = watchlistRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWatchlistMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(watchlistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkWatchlistNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = watchlistRepository.findAll().size();
        // set the field null
        watchlist.setWatchlistName(null);

        // Create the Watchlist, which fails.
        WatchlistDTO watchlistDTO = watchlistMapper.toDto(watchlist);

        restWatchlistMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(watchlistDTO)))
            .andExpect(status().isBadRequest());

        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = watchlistRepository.findAll().size();
        // set the field null
        watchlist.setIsActive(null);

        // Create the Watchlist, which fails.
        WatchlistDTO watchlistDTO = watchlistMapper.toDto(watchlist);

        restWatchlistMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(watchlistDTO)))
            .andExpect(status().isBadRequest());

        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWatchlists() throws Exception {
        // Initialize the database
        watchlistRepository.saveAndFlush(watchlist);

        // Get all the watchlistList
        restWatchlistMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(watchlist.getId().intValue())))
            .andExpect(jsonPath("$.[*].watchlistName").value(hasItem(DEFAULT_WATCHLIST_NAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }

    @Test
    @Transactional
    void getWatchlist() throws Exception {
        // Initialize the database
        watchlistRepository.saveAndFlush(watchlist);

        // Get the watchlist
        restWatchlistMockMvc
            .perform(get(ENTITY_API_URL_ID, watchlist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(watchlist.getId().intValue()))
            .andExpect(jsonPath("$.watchlistName").value(DEFAULT_WATCHLIST_NAME))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingWatchlist() throws Exception {
        // Get the watchlist
        restWatchlistMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWatchlist() throws Exception {
        // Initialize the database
        watchlistRepository.saveAndFlush(watchlist);

        int databaseSizeBeforeUpdate = watchlistRepository.findAll().size();

        // Update the watchlist
        Watchlist updatedWatchlist = watchlistRepository.findById(watchlist.getId()).get();
        // Disconnect from session so that the updates on updatedWatchlist are not directly saved in db
        em.detach(updatedWatchlist);
        updatedWatchlist
            .watchlistName(UPDATED_WATCHLIST_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        WatchlistDTO watchlistDTO = watchlistMapper.toDto(updatedWatchlist);

        restWatchlistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchlistDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(watchlistDTO))
            )
            .andExpect(status().isOk());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeUpdate);
        Watchlist testWatchlist = watchlistList.get(watchlistList.size() - 1);
        assertThat(testWatchlist.getWatchlistName()).isEqualTo(UPDATED_WATCHLIST_NAME);
        assertThat(testWatchlist.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWatchlist.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWatchlist.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingWatchlist() throws Exception {
        int databaseSizeBeforeUpdate = watchlistRepository.findAll().size();
        watchlist.setId(count.incrementAndGet());

        // Create the Watchlist
        WatchlistDTO watchlistDTO = watchlistMapper.toDto(watchlist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchlistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchlistDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(watchlistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWatchlist() throws Exception {
        int databaseSizeBeforeUpdate = watchlistRepository.findAll().size();
        watchlist.setId(count.incrementAndGet());

        // Create the Watchlist
        WatchlistDTO watchlistDTO = watchlistMapper.toDto(watchlist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchlistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(watchlistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWatchlist() throws Exception {
        int databaseSizeBeforeUpdate = watchlistRepository.findAll().size();
        watchlist.setId(count.incrementAndGet());

        // Create the Watchlist
        WatchlistDTO watchlistDTO = watchlistMapper.toDto(watchlist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchlistMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(watchlistDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWatchlistWithPatch() throws Exception {
        // Initialize the database
        watchlistRepository.saveAndFlush(watchlist);

        int databaseSizeBeforeUpdate = watchlistRepository.findAll().size();

        // Update the watchlist using partial update
        Watchlist partialUpdatedWatchlist = new Watchlist();
        partialUpdatedWatchlist.setId(watchlist.getId());

        partialUpdatedWatchlist.isActive(UPDATED_IS_ACTIVE);

        restWatchlistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatchlist.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWatchlist))
            )
            .andExpect(status().isOk());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeUpdate);
        Watchlist testWatchlist = watchlistList.get(watchlistList.size() - 1);
        assertThat(testWatchlist.getWatchlistName()).isEqualTo(DEFAULT_WATCHLIST_NAME);
        assertThat(testWatchlist.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWatchlist.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testWatchlist.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateWatchlistWithPatch() throws Exception {
        // Initialize the database
        watchlistRepository.saveAndFlush(watchlist);

        int databaseSizeBeforeUpdate = watchlistRepository.findAll().size();

        // Update the watchlist using partial update
        Watchlist partialUpdatedWatchlist = new Watchlist();
        partialUpdatedWatchlist.setId(watchlist.getId());

        partialUpdatedWatchlist
            .watchlistName(UPDATED_WATCHLIST_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restWatchlistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatchlist.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWatchlist))
            )
            .andExpect(status().isOk());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeUpdate);
        Watchlist testWatchlist = watchlistList.get(watchlistList.size() - 1);
        assertThat(testWatchlist.getWatchlistName()).isEqualTo(UPDATED_WATCHLIST_NAME);
        assertThat(testWatchlist.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWatchlist.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWatchlist.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingWatchlist() throws Exception {
        int databaseSizeBeforeUpdate = watchlistRepository.findAll().size();
        watchlist.setId(count.incrementAndGet());

        // Create the Watchlist
        WatchlistDTO watchlistDTO = watchlistMapper.toDto(watchlist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchlistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, watchlistDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(watchlistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWatchlist() throws Exception {
        int databaseSizeBeforeUpdate = watchlistRepository.findAll().size();
        watchlist.setId(count.incrementAndGet());

        // Create the Watchlist
        WatchlistDTO watchlistDTO = watchlistMapper.toDto(watchlist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchlistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(watchlistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWatchlist() throws Exception {
        int databaseSizeBeforeUpdate = watchlistRepository.findAll().size();
        watchlist.setId(count.incrementAndGet());

        // Create the Watchlist
        WatchlistDTO watchlistDTO = watchlistMapper.toDto(watchlist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchlistMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(watchlistDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWatchlist() throws Exception {
        // Initialize the database
        watchlistRepository.saveAndFlush(watchlist);

        int databaseSizeBeforeDelete = watchlistRepository.findAll().size();

        // Delete the watchlist
        restWatchlistMockMvc
            .perform(delete(ENTITY_API_URL_ID, watchlist.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
