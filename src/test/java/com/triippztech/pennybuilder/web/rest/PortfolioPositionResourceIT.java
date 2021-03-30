package com.triippztech.pennybuilder.web.rest;

import static com.triippztech.pennybuilder.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.triippztech.pennybuilder.IntegrationTest;
import com.triippztech.pennybuilder.domain.PortfolioPosition;
import com.triippztech.pennybuilder.repository.PortfolioPositionRepository;
import com.triippztech.pennybuilder.service.dto.PortfolioPositionDTO;
import com.triippztech.pennybuilder.service.mapper.PortfolioPositionMapper;
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
 * Integration tests for the {@link PortfolioPositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PortfolioPositionResourceIT {

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final Boolean DEFAULT_IS_OPEN = false;
    private static final Boolean UPDATED_IS_OPEN = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/portfolio-positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PortfolioPositionRepository portfolioPositionRepository;

    @Autowired
    private PortfolioPositionMapper portfolioPositionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPortfolioPositionMockMvc;

    private PortfolioPosition portfolioPosition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PortfolioPosition createEntity(EntityManager em) {
        PortfolioPosition portfolioPosition = new PortfolioPosition()
            .quantity(DEFAULT_QUANTITY)
            .isOpen(DEFAULT_IS_OPEN)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return portfolioPosition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PortfolioPosition createUpdatedEntity(EntityManager em) {
        PortfolioPosition portfolioPosition = new PortfolioPosition()
            .quantity(UPDATED_QUANTITY)
            .isOpen(UPDATED_IS_OPEN)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return portfolioPosition;
    }

    @BeforeEach
    public void initTest() {
        portfolioPosition = createEntity(em);
    }

    @Test
    @Transactional
    void createPortfolioPosition() throws Exception {
        int databaseSizeBeforeCreate = portfolioPositionRepository.findAll().size();
        // Create the PortfolioPosition
        PortfolioPositionDTO portfolioPositionDTO = portfolioPositionMapper.toDto(portfolioPosition);
        restPortfolioPositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioPositionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PortfolioPosition in the database
        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeCreate + 1);
        PortfolioPosition testPortfolioPosition = portfolioPositionList.get(portfolioPositionList.size() - 1);
        assertThat(testPortfolioPosition.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPortfolioPosition.getIsOpen()).isEqualTo(DEFAULT_IS_OPEN);
        assertThat(testPortfolioPosition.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPortfolioPosition.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createPortfolioPositionWithExistingId() throws Exception {
        // Create the PortfolioPosition with an existing ID
        portfolioPosition.setId(1L);
        PortfolioPositionDTO portfolioPositionDTO = portfolioPositionMapper.toDto(portfolioPosition);

        int databaseSizeBeforeCreate = portfolioPositionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortfolioPositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioPosition in the database
        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = portfolioPositionRepository.findAll().size();
        // set the field null
        portfolioPosition.setQuantity(null);

        // Create the PortfolioPosition, which fails.
        PortfolioPositionDTO portfolioPositionDTO = portfolioPositionMapper.toDto(portfolioPosition);

        restPortfolioPositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioPositionDTO))
            )
            .andExpect(status().isBadRequest());

        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsOpenIsRequired() throws Exception {
        int databaseSizeBeforeTest = portfolioPositionRepository.findAll().size();
        // set the field null
        portfolioPosition.setIsOpen(null);

        // Create the PortfolioPosition, which fails.
        PortfolioPositionDTO portfolioPositionDTO = portfolioPositionMapper.toDto(portfolioPosition);

        restPortfolioPositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioPositionDTO))
            )
            .andExpect(status().isBadRequest());

        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPortfolioPositions() throws Exception {
        // Initialize the database
        portfolioPositionRepository.saveAndFlush(portfolioPosition);

        // Get all the portfolioPositionList
        restPortfolioPositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portfolioPosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].isOpen").value(hasItem(DEFAULT_IS_OPEN.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }

    @Test
    @Transactional
    void getPortfolioPosition() throws Exception {
        // Initialize the database
        portfolioPositionRepository.saveAndFlush(portfolioPosition);

        // Get the portfolioPosition
        restPortfolioPositionMockMvc
            .perform(get(ENTITY_API_URL_ID, portfolioPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(portfolioPosition.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.isOpen").value(DEFAULT_IS_OPEN.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingPortfolioPosition() throws Exception {
        // Get the portfolioPosition
        restPortfolioPositionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPortfolioPosition() throws Exception {
        // Initialize the database
        portfolioPositionRepository.saveAndFlush(portfolioPosition);

        int databaseSizeBeforeUpdate = portfolioPositionRepository.findAll().size();

        // Update the portfolioPosition
        PortfolioPosition updatedPortfolioPosition = portfolioPositionRepository.findById(portfolioPosition.getId()).get();
        // Disconnect from session so that the updates on updatedPortfolioPosition are not directly saved in db
        em.detach(updatedPortfolioPosition);
        updatedPortfolioPosition
            .quantity(UPDATED_QUANTITY)
            .isOpen(UPDATED_IS_OPEN)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        PortfolioPositionDTO portfolioPositionDTO = portfolioPositionMapper.toDto(updatedPortfolioPosition);

        restPortfolioPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, portfolioPositionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioPositionDTO))
            )
            .andExpect(status().isOk());

        // Validate the PortfolioPosition in the database
        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeUpdate);
        PortfolioPosition testPortfolioPosition = portfolioPositionList.get(portfolioPositionList.size() - 1);
        assertThat(testPortfolioPosition.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPortfolioPosition.getIsOpen()).isEqualTo(UPDATED_IS_OPEN);
        assertThat(testPortfolioPosition.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPortfolioPosition.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPortfolioPosition() throws Exception {
        int databaseSizeBeforeUpdate = portfolioPositionRepository.findAll().size();
        portfolioPosition.setId(count.incrementAndGet());

        // Create the PortfolioPosition
        PortfolioPositionDTO portfolioPositionDTO = portfolioPositionMapper.toDto(portfolioPosition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortfolioPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, portfolioPositionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioPosition in the database
        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPortfolioPosition() throws Exception {
        int databaseSizeBeforeUpdate = portfolioPositionRepository.findAll().size();
        portfolioPosition.setId(count.incrementAndGet());

        // Create the PortfolioPosition
        PortfolioPositionDTO portfolioPositionDTO = portfolioPositionMapper.toDto(portfolioPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioPosition in the database
        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPortfolioPosition() throws Exception {
        int databaseSizeBeforeUpdate = portfolioPositionRepository.findAll().size();
        portfolioPosition.setId(count.incrementAndGet());

        // Create the PortfolioPosition
        PortfolioPositionDTO portfolioPositionDTO = portfolioPositionMapper.toDto(portfolioPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioPositionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portfolioPositionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PortfolioPosition in the database
        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePortfolioPositionWithPatch() throws Exception {
        // Initialize the database
        portfolioPositionRepository.saveAndFlush(portfolioPosition);

        int databaseSizeBeforeUpdate = portfolioPositionRepository.findAll().size();

        // Update the portfolioPosition using partial update
        PortfolioPosition partialUpdatedPortfolioPosition = new PortfolioPosition();
        partialUpdatedPortfolioPosition.setId(portfolioPosition.getId());

        partialUpdatedPortfolioPosition.quantity(UPDATED_QUANTITY).createdDate(UPDATED_CREATED_DATE);

        restPortfolioPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPortfolioPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPortfolioPosition))
            )
            .andExpect(status().isOk());

        // Validate the PortfolioPosition in the database
        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeUpdate);
        PortfolioPosition testPortfolioPosition = portfolioPositionList.get(portfolioPositionList.size() - 1);
        assertThat(testPortfolioPosition.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPortfolioPosition.getIsOpen()).isEqualTo(DEFAULT_IS_OPEN);
        assertThat(testPortfolioPosition.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPortfolioPosition.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePortfolioPositionWithPatch() throws Exception {
        // Initialize the database
        portfolioPositionRepository.saveAndFlush(portfolioPosition);

        int databaseSizeBeforeUpdate = portfolioPositionRepository.findAll().size();

        // Update the portfolioPosition using partial update
        PortfolioPosition partialUpdatedPortfolioPosition = new PortfolioPosition();
        partialUpdatedPortfolioPosition.setId(portfolioPosition.getId());

        partialUpdatedPortfolioPosition
            .quantity(UPDATED_QUANTITY)
            .isOpen(UPDATED_IS_OPEN)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restPortfolioPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPortfolioPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPortfolioPosition))
            )
            .andExpect(status().isOk());

        // Validate the PortfolioPosition in the database
        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeUpdate);
        PortfolioPosition testPortfolioPosition = portfolioPositionList.get(portfolioPositionList.size() - 1);
        assertThat(testPortfolioPosition.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPortfolioPosition.getIsOpen()).isEqualTo(UPDATED_IS_OPEN);
        assertThat(testPortfolioPosition.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPortfolioPosition.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPortfolioPosition() throws Exception {
        int databaseSizeBeforeUpdate = portfolioPositionRepository.findAll().size();
        portfolioPosition.setId(count.incrementAndGet());

        // Create the PortfolioPosition
        PortfolioPositionDTO portfolioPositionDTO = portfolioPositionMapper.toDto(portfolioPosition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortfolioPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, portfolioPositionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portfolioPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioPosition in the database
        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPortfolioPosition() throws Exception {
        int databaseSizeBeforeUpdate = portfolioPositionRepository.findAll().size();
        portfolioPosition.setId(count.incrementAndGet());

        // Create the PortfolioPosition
        PortfolioPositionDTO portfolioPositionDTO = portfolioPositionMapper.toDto(portfolioPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portfolioPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioPosition in the database
        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPortfolioPosition() throws Exception {
        int databaseSizeBeforeUpdate = portfolioPositionRepository.findAll().size();
        portfolioPosition.setId(count.incrementAndGet());

        // Create the PortfolioPosition
        PortfolioPositionDTO portfolioPositionDTO = portfolioPositionMapper.toDto(portfolioPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioPositionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portfolioPositionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PortfolioPosition in the database
        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePortfolioPosition() throws Exception {
        // Initialize the database
        portfolioPositionRepository.saveAndFlush(portfolioPosition);

        int databaseSizeBeforeDelete = portfolioPositionRepository.findAll().size();

        // Delete the portfolioPosition
        restPortfolioPositionMockMvc
            .perform(delete(ENTITY_API_URL_ID, portfolioPosition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PortfolioPosition> portfolioPositionList = portfolioPositionRepository.findAll();
        assertThat(portfolioPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
