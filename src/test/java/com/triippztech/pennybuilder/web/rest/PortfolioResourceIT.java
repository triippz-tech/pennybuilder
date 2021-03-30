package com.triippztech.pennybuilder.web.rest;

import static com.triippztech.pennybuilder.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.triippztech.pennybuilder.IntegrationTest;
import com.triippztech.pennybuilder.domain.Portfolio;
import com.triippztech.pennybuilder.domain.enumeration.FiatCurrency;
import com.triippztech.pennybuilder.repository.PortfolioRepository;
import com.triippztech.pennybuilder.service.dto.PortfolioDTO;
import com.triippztech.pennybuilder.service.mapper.PortfolioMapper;
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
 * Integration tests for the {@link PortfolioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PortfolioResourceIT {

    private static final String DEFAULT_PORTFOLIO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PORTFOLIO_NAME = "BBBBBBBBBB";

    private static final FiatCurrency DEFAULT_BASE_CURRENCY = FiatCurrency.AED;
    private static final FiatCurrency UPDATED_BASE_CURRENCY = FiatCurrency.AFN;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/portfolios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioMapper portfolioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPortfolioMockMvc;

    private Portfolio portfolio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Portfolio createEntity(EntityManager em) {
        Portfolio portfolio = new Portfolio()
            .portfolioName(DEFAULT_PORTFOLIO_NAME)
            .baseCurrency(DEFAULT_BASE_CURRENCY)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return portfolio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Portfolio createUpdatedEntity(EntityManager em) {
        Portfolio portfolio = new Portfolio()
            .portfolioName(UPDATED_PORTFOLIO_NAME)
            .baseCurrency(UPDATED_BASE_CURRENCY)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return portfolio;
    }

    @BeforeEach
    public void initTest() {
        portfolio = createEntity(em);
    }

    @Test
    @Transactional
    void createPortfolio() throws Exception {
        int databaseSizeBeforeCreate = portfolioRepository.findAll().size();
        // Create the Portfolio
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);
        restPortfolioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portfolioDTO)))
            .andExpect(status().isCreated());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeCreate + 1);
        Portfolio testPortfolio = portfolioList.get(portfolioList.size() - 1);
        assertThat(testPortfolio.getPortfolioName()).isEqualTo(DEFAULT_PORTFOLIO_NAME);
        assertThat(testPortfolio.getBaseCurrency()).isEqualTo(DEFAULT_BASE_CURRENCY);
        assertThat(testPortfolio.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testPortfolio.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPortfolio.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createPortfolioWithExistingId() throws Exception {
        // Create the Portfolio with an existing ID
        portfolio.setId(1L);
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);

        int databaseSizeBeforeCreate = portfolioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortfolioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portfolioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPortfolioNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = portfolioRepository.findAll().size();
        // set the field null
        portfolio.setPortfolioName(null);

        // Create the Portfolio, which fails.
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);

        restPortfolioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portfolioDTO)))
            .andExpect(status().isBadRequest());

        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBaseCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = portfolioRepository.findAll().size();
        // set the field null
        portfolio.setBaseCurrency(null);

        // Create the Portfolio, which fails.
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);

        restPortfolioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portfolioDTO)))
            .andExpect(status().isBadRequest());

        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = portfolioRepository.findAll().size();
        // set the field null
        portfolio.setIsActive(null);

        // Create the Portfolio, which fails.
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);

        restPortfolioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portfolioDTO)))
            .andExpect(status().isBadRequest());

        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPortfolios() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);

        // Get all the portfolioList
        restPortfolioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portfolio.getId().intValue())))
            .andExpect(jsonPath("$.[*].portfolioName").value(hasItem(DEFAULT_PORTFOLIO_NAME)))
            .andExpect(jsonPath("$.[*].baseCurrency").value(hasItem(DEFAULT_BASE_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }

    @Test
    @Transactional
    void getPortfolio() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);

        // Get the portfolio
        restPortfolioMockMvc
            .perform(get(ENTITY_API_URL_ID, portfolio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(portfolio.getId().intValue()))
            .andExpect(jsonPath("$.portfolioName").value(DEFAULT_PORTFOLIO_NAME))
            .andExpect(jsonPath("$.baseCurrency").value(DEFAULT_BASE_CURRENCY.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingPortfolio() throws Exception {
        // Get the portfolio
        restPortfolioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPortfolio() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);

        int databaseSizeBeforeUpdate = portfolioRepository.findAll().size();

        // Update the portfolio
        Portfolio updatedPortfolio = portfolioRepository.findById(portfolio.getId()).get();
        // Disconnect from session so that the updates on updatedPortfolio are not directly saved in db
        em.detach(updatedPortfolio);
        updatedPortfolio
            .portfolioName(UPDATED_PORTFOLIO_NAME)
            .baseCurrency(UPDATED_BASE_CURRENCY)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(updatedPortfolio);

        restPortfolioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, portfolioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeUpdate);
        Portfolio testPortfolio = portfolioList.get(portfolioList.size() - 1);
        assertThat(testPortfolio.getPortfolioName()).isEqualTo(UPDATED_PORTFOLIO_NAME);
        assertThat(testPortfolio.getBaseCurrency()).isEqualTo(UPDATED_BASE_CURRENCY);
        assertThat(testPortfolio.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPortfolio.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPortfolio.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPortfolio() throws Exception {
        int databaseSizeBeforeUpdate = portfolioRepository.findAll().size();
        portfolio.setId(count.incrementAndGet());

        // Create the Portfolio
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortfolioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, portfolioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPortfolio() throws Exception {
        int databaseSizeBeforeUpdate = portfolioRepository.findAll().size();
        portfolio.setId(count.incrementAndGet());

        // Create the Portfolio
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPortfolio() throws Exception {
        int databaseSizeBeforeUpdate = portfolioRepository.findAll().size();
        portfolio.setId(count.incrementAndGet());

        // Create the Portfolio
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portfolioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePortfolioWithPatch() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);

        int databaseSizeBeforeUpdate = portfolioRepository.findAll().size();

        // Update the portfolio using partial update
        Portfolio partialUpdatedPortfolio = new Portfolio();
        partialUpdatedPortfolio.setId(portfolio.getId());

        partialUpdatedPortfolio.portfolioName(UPDATED_PORTFOLIO_NAME).isActive(UPDATED_IS_ACTIVE).createdDate(UPDATED_CREATED_DATE);

        restPortfolioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPortfolio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPortfolio))
            )
            .andExpect(status().isOk());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeUpdate);
        Portfolio testPortfolio = portfolioList.get(portfolioList.size() - 1);
        assertThat(testPortfolio.getPortfolioName()).isEqualTo(UPDATED_PORTFOLIO_NAME);
        assertThat(testPortfolio.getBaseCurrency()).isEqualTo(DEFAULT_BASE_CURRENCY);
        assertThat(testPortfolio.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPortfolio.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPortfolio.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePortfolioWithPatch() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);

        int databaseSizeBeforeUpdate = portfolioRepository.findAll().size();

        // Update the portfolio using partial update
        Portfolio partialUpdatedPortfolio = new Portfolio();
        partialUpdatedPortfolio.setId(portfolio.getId());

        partialUpdatedPortfolio
            .portfolioName(UPDATED_PORTFOLIO_NAME)
            .baseCurrency(UPDATED_BASE_CURRENCY)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restPortfolioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPortfolio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPortfolio))
            )
            .andExpect(status().isOk());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeUpdate);
        Portfolio testPortfolio = portfolioList.get(portfolioList.size() - 1);
        assertThat(testPortfolio.getPortfolioName()).isEqualTo(UPDATED_PORTFOLIO_NAME);
        assertThat(testPortfolio.getBaseCurrency()).isEqualTo(UPDATED_BASE_CURRENCY);
        assertThat(testPortfolio.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPortfolio.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPortfolio.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPortfolio() throws Exception {
        int databaseSizeBeforeUpdate = portfolioRepository.findAll().size();
        portfolio.setId(count.incrementAndGet());

        // Create the Portfolio
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortfolioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, portfolioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portfolioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPortfolio() throws Exception {
        int databaseSizeBeforeUpdate = portfolioRepository.findAll().size();
        portfolio.setId(count.incrementAndGet());

        // Create the Portfolio
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portfolioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPortfolio() throws Exception {
        int databaseSizeBeforeUpdate = portfolioRepository.findAll().size();
        portfolio.setId(count.incrementAndGet());

        // Create the Portfolio
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(portfolioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePortfolio() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);

        int databaseSizeBeforeDelete = portfolioRepository.findAll().size();

        // Delete the portfolio
        restPortfolioMockMvc
            .perform(delete(ENTITY_API_URL_ID, portfolio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
