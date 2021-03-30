package com.triippztech.pennybuilder.web.rest;

import static com.triippztech.pennybuilder.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.triippztech.pennybuilder.IntegrationTest;
import com.triippztech.pennybuilder.domain.Asset;
import com.triippztech.pennybuilder.domain.PortfolioPosition;
import com.triippztech.pennybuilder.domain.WatchlistPosition;
import com.triippztech.pennybuilder.repository.AssetRepository;
import com.triippztech.pennybuilder.service.criteria.AssetCriteria;
import com.triippztech.pennybuilder.service.dto.AssetDTO;
import com.triippztech.pennybuilder.service.mapper.AssetMapper;
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
 * Integration tests for the {@link AssetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/assets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetMapper assetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetMockMvc;

    private Asset asset;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asset createEntity(EntityManager em) {
        Asset asset = new Asset()
            .name(DEFAULT_NAME)
            .symbol(DEFAULT_SYMBOL)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return asset;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asset createUpdatedEntity(EntityManager em) {
        Asset asset = new Asset()
            .name(UPDATED_NAME)
            .symbol(UPDATED_SYMBOL)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return asset;
    }

    @BeforeEach
    public void initTest() {
        asset = createEntity(em);
    }

    @Test
    @Transactional
    void createAsset() throws Exception {
        int databaseSizeBeforeCreate = assetRepository.findAll().size();
        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);
        restAssetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetDTO)))
            .andExpect(status().isCreated());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeCreate + 1);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAsset.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testAsset.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAsset.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createAssetWithExistingId() throws Exception {
        // Create the Asset with an existing ID
        asset.setId(1L);
        AssetDTO assetDTO = assetMapper.toDto(asset);

        int databaseSizeBeforeCreate = assetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRepository.findAll().size();
        // set the field null
        asset.setName(null);

        // Create the Asset, which fails.
        AssetDTO assetDTO = assetMapper.toDto(asset);

        restAssetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetDTO)))
            .andExpect(status().isBadRequest());

        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRepository.findAll().size();
        // set the field null
        asset.setSymbol(null);

        // Create the Asset, which fails.
        AssetDTO assetDTO = assetMapper.toDto(asset);

        restAssetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetDTO)))
            .andExpect(status().isBadRequest());

        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAssets() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList
        restAssetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asset.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }

    @Test
    @Transactional
    void getAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get the asset
        restAssetMockMvc
            .perform(get(ENTITY_API_URL_ID, asset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(asset.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }

    @Test
    @Transactional
    void getAssetsByIdFiltering() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        Long id = asset.getId();

        defaultAssetShouldBeFound("id.equals=" + id);
        defaultAssetShouldNotBeFound("id.notEquals=" + id);

        defaultAssetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAssetShouldNotBeFound("id.greaterThan=" + id);

        defaultAssetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAssetShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAssetsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where name equals to DEFAULT_NAME
        defaultAssetShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the assetList where name equals to UPDATED_NAME
        defaultAssetShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAssetsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where name not equals to DEFAULT_NAME
        defaultAssetShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the assetList where name not equals to UPDATED_NAME
        defaultAssetShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAssetsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAssetShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the assetList where name equals to UPDATED_NAME
        defaultAssetShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAssetsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where name is not null
        defaultAssetShouldBeFound("name.specified=true");

        // Get all the assetList where name is null
        defaultAssetShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByNameContainsSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where name contains DEFAULT_NAME
        defaultAssetShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the assetList where name contains UPDATED_NAME
        defaultAssetShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAssetsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where name does not contain DEFAULT_NAME
        defaultAssetShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the assetList where name does not contain UPDATED_NAME
        defaultAssetShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAssetsBySymbolIsEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where symbol equals to DEFAULT_SYMBOL
        defaultAssetShouldBeFound("symbol.equals=" + DEFAULT_SYMBOL);

        // Get all the assetList where symbol equals to UPDATED_SYMBOL
        defaultAssetShouldNotBeFound("symbol.equals=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    void getAllAssetsBySymbolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where symbol not equals to DEFAULT_SYMBOL
        defaultAssetShouldNotBeFound("symbol.notEquals=" + DEFAULT_SYMBOL);

        // Get all the assetList where symbol not equals to UPDATED_SYMBOL
        defaultAssetShouldBeFound("symbol.notEquals=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    void getAllAssetsBySymbolIsInShouldWork() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where symbol in DEFAULT_SYMBOL or UPDATED_SYMBOL
        defaultAssetShouldBeFound("symbol.in=" + DEFAULT_SYMBOL + "," + UPDATED_SYMBOL);

        // Get all the assetList where symbol equals to UPDATED_SYMBOL
        defaultAssetShouldNotBeFound("symbol.in=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    void getAllAssetsBySymbolIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where symbol is not null
        defaultAssetShouldBeFound("symbol.specified=true");

        // Get all the assetList where symbol is null
        defaultAssetShouldNotBeFound("symbol.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsBySymbolContainsSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where symbol contains DEFAULT_SYMBOL
        defaultAssetShouldBeFound("symbol.contains=" + DEFAULT_SYMBOL);

        // Get all the assetList where symbol contains UPDATED_SYMBOL
        defaultAssetShouldNotBeFound("symbol.contains=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    void getAllAssetsBySymbolNotContainsSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where symbol does not contain DEFAULT_SYMBOL
        defaultAssetShouldNotBeFound("symbol.doesNotContain=" + DEFAULT_SYMBOL);

        // Get all the assetList where symbol does not contain UPDATED_SYMBOL
        defaultAssetShouldBeFound("symbol.doesNotContain=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    void getAllAssetsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where createdDate equals to DEFAULT_CREATED_DATE
        defaultAssetShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the assetList where createdDate equals to UPDATED_CREATED_DATE
        defaultAssetShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultAssetShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the assetList where createdDate not equals to UPDATED_CREATED_DATE
        defaultAssetShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultAssetShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the assetList where createdDate equals to UPDATED_CREATED_DATE
        defaultAssetShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where createdDate is not null
        defaultAssetShouldBeFound("createdDate.specified=true");

        // Get all the assetList where createdDate is null
        defaultAssetShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultAssetShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the assetList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultAssetShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultAssetShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the assetList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultAssetShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where createdDate is less than DEFAULT_CREATED_DATE
        defaultAssetShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the assetList where createdDate is less than UPDATED_CREATED_DATE
        defaultAssetShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultAssetShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the assetList where createdDate is greater than SMALLER_CREATED_DATE
        defaultAssetShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where updatedDate equals to DEFAULT_UPDATED_DATE
        defaultAssetShouldBeFound("updatedDate.equals=" + DEFAULT_UPDATED_DATE);

        // Get all the assetList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultAssetShouldNotBeFound("updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where updatedDate not equals to DEFAULT_UPDATED_DATE
        defaultAssetShouldNotBeFound("updatedDate.notEquals=" + DEFAULT_UPDATED_DATE);

        // Get all the assetList where updatedDate not equals to UPDATED_UPDATED_DATE
        defaultAssetShouldBeFound("updatedDate.notEquals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where updatedDate in DEFAULT_UPDATED_DATE or UPDATED_UPDATED_DATE
        defaultAssetShouldBeFound("updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE);

        // Get all the assetList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultAssetShouldNotBeFound("updatedDate.in=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where updatedDate is not null
        defaultAssetShouldBeFound("updatedDate.specified=true");

        // Get all the assetList where updatedDate is null
        defaultAssetShouldNotBeFound("updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where updatedDate is greater than or equal to DEFAULT_UPDATED_DATE
        defaultAssetShouldBeFound("updatedDate.greaterThanOrEqual=" + DEFAULT_UPDATED_DATE);

        // Get all the assetList where updatedDate is greater than or equal to UPDATED_UPDATED_DATE
        defaultAssetShouldNotBeFound("updatedDate.greaterThanOrEqual=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where updatedDate is less than or equal to DEFAULT_UPDATED_DATE
        defaultAssetShouldBeFound("updatedDate.lessThanOrEqual=" + DEFAULT_UPDATED_DATE);

        // Get all the assetList where updatedDate is less than or equal to SMALLER_UPDATED_DATE
        defaultAssetShouldNotBeFound("updatedDate.lessThanOrEqual=" + SMALLER_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where updatedDate is less than DEFAULT_UPDATED_DATE
        defaultAssetShouldNotBeFound("updatedDate.lessThan=" + DEFAULT_UPDATED_DATE);

        // Get all the assetList where updatedDate is less than UPDATED_UPDATED_DATE
        defaultAssetShouldBeFound("updatedDate.lessThan=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where updatedDate is greater than DEFAULT_UPDATED_DATE
        defaultAssetShouldNotBeFound("updatedDate.greaterThan=" + DEFAULT_UPDATED_DATE);

        // Get all the assetList where updatedDate is greater than SMALLER_UPDATED_DATE
        defaultAssetShouldBeFound("updatedDate.greaterThan=" + SMALLER_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAssetsByPortfolioPositionsIsEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);
        PortfolioPosition portfolioPositions = PortfolioPositionResourceIT.createEntity(em);
        em.persist(portfolioPositions);
        em.flush();
        asset.addPortfolioPositions(portfolioPositions);
        assetRepository.saveAndFlush(asset);
        Long portfolioPositionsId = portfolioPositions.getId();

        // Get all the assetList where portfolioPositions equals to portfolioPositionsId
        defaultAssetShouldBeFound("portfolioPositionsId.equals=" + portfolioPositionsId);

        // Get all the assetList where portfolioPositions equals to (portfolioPositionsId + 1)
        defaultAssetShouldNotBeFound("portfolioPositionsId.equals=" + (portfolioPositionsId + 1));
    }

    @Test
    @Transactional
    void getAllAssetsByWatchlistPositionsIsEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);
        WatchlistPosition watchlistPositions = WatchlistPositionResourceIT.createEntity(em);
        em.persist(watchlistPositions);
        em.flush();
        asset.addWatchlistPositions(watchlistPositions);
        assetRepository.saveAndFlush(asset);
        Long watchlistPositionsId = watchlistPositions.getId();

        // Get all the assetList where watchlistPositions equals to watchlistPositionsId
        defaultAssetShouldBeFound("watchlistPositionsId.equals=" + watchlistPositionsId);

        // Get all the assetList where watchlistPositions equals to (watchlistPositionsId + 1)
        defaultAssetShouldNotBeFound("watchlistPositionsId.equals=" + (watchlistPositionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAssetShouldBeFound(String filter) throws Exception {
        restAssetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asset.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));

        // Check, that the count call also returns 1
        restAssetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAssetShouldNotBeFound(String filter) throws Exception {
        restAssetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAssetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAsset() throws Exception {
        // Get the asset
        restAssetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Update the asset
        Asset updatedAsset = assetRepository.findById(asset.getId()).get();
        // Disconnect from session so that the updates on updatedAsset are not directly saved in db
        em.detach(updatedAsset);
        updatedAsset.name(UPDATED_NAME).symbol(UPDATED_SYMBOL).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);
        AssetDTO assetDTO = assetMapper.toDto(updatedAsset);

        restAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetDTO))
            )
            .andExpect(status().isOk());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAsset.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testAsset.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAsset.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAsset() throws Exception {
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();
        asset.setId(count.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAsset() throws Exception {
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();
        asset.setId(count.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAsset() throws Exception {
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();
        asset.setId(count.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetWithPatch() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Update the asset using partial update
        Asset partialUpdatedAsset = new Asset();
        partialUpdatedAsset.setId(asset.getId());

        partialUpdatedAsset.symbol(UPDATED_SYMBOL).createdDate(UPDATED_CREATED_DATE);

        restAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsset.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAsset))
            )
            .andExpect(status().isOk());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAsset.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testAsset.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAsset.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAssetWithPatch() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Update the asset using partial update
        Asset partialUpdatedAsset = new Asset();
        partialUpdatedAsset.setId(asset.getId());

        partialUpdatedAsset.name(UPDATED_NAME).symbol(UPDATED_SYMBOL).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsset.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAsset))
            )
            .andExpect(status().isOk());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAsset.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testAsset.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAsset.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAsset() throws Exception {
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();
        asset.setId(count.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAsset() throws Exception {
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();
        asset.setId(count.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAsset() throws Exception {
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();
        asset.setId(count.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(assetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        int databaseSizeBeforeDelete = assetRepository.findAll().size();

        // Delete the asset
        restAssetMockMvc
            .perform(delete(ENTITY_API_URL_ID, asset.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
