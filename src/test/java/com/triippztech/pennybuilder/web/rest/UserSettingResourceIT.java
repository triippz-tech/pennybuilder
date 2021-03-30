package com.triippztech.pennybuilder.web.rest;

import static com.triippztech.pennybuilder.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.triippztech.pennybuilder.IntegrationTest;
import com.triippztech.pennybuilder.domain.UserSetting;
import com.triippztech.pennybuilder.repository.UserSettingRepository;
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
 * Integration tests for the {@link UserSettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserSettingResourceIT {

    private static final Boolean DEFAULT_RECEIVE_EMAIL = false;
    private static final Boolean UPDATED_RECEIVE_EMAIL = true;

    private static final Boolean DEFAULT_PRIVATE_PROFILE = false;
    private static final Boolean UPDATED_PRIVATE_PROFILE = true;

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/user-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserSettingRepository userSettingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserSettingMockMvc;

    private UserSetting userSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSetting createEntity(EntityManager em) {
        UserSetting userSetting = new UserSetting()
            .receiveEmail(DEFAULT_RECEIVE_EMAIL)
            .privateProfile(DEFAULT_PRIVATE_PROFILE)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return userSetting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSetting createUpdatedEntity(EntityManager em) {
        UserSetting userSetting = new UserSetting()
            .receiveEmail(UPDATED_RECEIVE_EMAIL)
            .privateProfile(UPDATED_PRIVATE_PROFILE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return userSetting;
    }

    @BeforeEach
    public void initTest() {
        userSetting = createEntity(em);
    }

    @Test
    @Transactional
    void createUserSetting() throws Exception {
        int databaseSizeBeforeCreate = userSettingRepository.findAll().size();
        // Create the UserSetting
        restUserSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSetting)))
            .andExpect(status().isCreated());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeCreate + 1);
        UserSetting testUserSetting = userSettingList.get(userSettingList.size() - 1);
        assertThat(testUserSetting.getReceiveEmail()).isEqualTo(DEFAULT_RECEIVE_EMAIL);
        assertThat(testUserSetting.getPrivateProfile()).isEqualTo(DEFAULT_PRIVATE_PROFILE);
        assertThat(testUserSetting.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testUserSetting.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserSetting.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createUserSettingWithExistingId() throws Exception {
        // Create the UserSetting with an existing ID
        userSetting.setId(1L);

        int databaseSizeBeforeCreate = userSettingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSetting)))
            .andExpect(status().isBadRequest());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkReceiveEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSettingRepository.findAll().size();
        // set the field null
        userSetting.setReceiveEmail(null);

        // Create the UserSetting, which fails.

        restUserSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSetting)))
            .andExpect(status().isBadRequest());

        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrivateProfileIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSettingRepository.findAll().size();
        // set the field null
        userSetting.setPrivateProfile(null);

        // Create the UserSetting, which fails.

        restUserSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSetting)))
            .andExpect(status().isBadRequest());

        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserSettings() throws Exception {
        // Initialize the database
        userSettingRepository.saveAndFlush(userSetting);

        // Get all the userSettingList
        restUserSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].receiveEmail").value(hasItem(DEFAULT_RECEIVE_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].privateProfile").value(hasItem(DEFAULT_PRIVATE_PROFILE.booleanValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }

    @Test
    @Transactional
    void getUserSetting() throws Exception {
        // Initialize the database
        userSettingRepository.saveAndFlush(userSetting);

        // Get the userSetting
        restUserSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, userSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userSetting.getId().intValue()))
            .andExpect(jsonPath("$.receiveEmail").value(DEFAULT_RECEIVE_EMAIL.booleanValue()))
            .andExpect(jsonPath("$.privateProfile").value(DEFAULT_PRIVATE_PROFILE.booleanValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingUserSetting() throws Exception {
        // Get the userSetting
        restUserSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserSetting() throws Exception {
        // Initialize the database
        userSettingRepository.saveAndFlush(userSetting);

        int databaseSizeBeforeUpdate = userSettingRepository.findAll().size();

        // Update the userSetting
        UserSetting updatedUserSetting = userSettingRepository.findById(userSetting.getId()).get();
        // Disconnect from session so that the updates on updatedUserSetting are not directly saved in db
        em.detach(updatedUserSetting);
        updatedUserSetting
            .receiveEmail(UPDATED_RECEIVE_EMAIL)
            .privateProfile(UPDATED_PRIVATE_PROFILE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserSetting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserSetting))
            )
            .andExpect(status().isOk());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeUpdate);
        UserSetting testUserSetting = userSettingList.get(userSettingList.size() - 1);
        assertThat(testUserSetting.getReceiveEmail()).isEqualTo(UPDATED_RECEIVE_EMAIL);
        assertThat(testUserSetting.getPrivateProfile()).isEqualTo(UPDATED_PRIVATE_PROFILE);
        assertThat(testUserSetting.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUserSetting.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserSetting.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserSetting() throws Exception {
        int databaseSizeBeforeUpdate = userSettingRepository.findAll().size();
        userSetting.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userSetting.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserSetting() throws Exception {
        int databaseSizeBeforeUpdate = userSettingRepository.findAll().size();
        userSetting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserSetting() throws Exception {
        int databaseSizeBeforeUpdate = userSettingRepository.findAll().size();
        userSetting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSettingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSetting)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserSettingWithPatch() throws Exception {
        // Initialize the database
        userSettingRepository.saveAndFlush(userSetting);

        int databaseSizeBeforeUpdate = userSettingRepository.findAll().size();

        // Update the userSetting using partial update
        UserSetting partialUpdatedUserSetting = new UserSetting();
        partialUpdatedUserSetting.setId(userSetting.getId());

        partialUpdatedUserSetting.receiveEmail(UPDATED_RECEIVE_EMAIL).phoneNumber(UPDATED_PHONE_NUMBER).createdDate(UPDATED_CREATED_DATE);

        restUserSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserSetting))
            )
            .andExpect(status().isOk());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeUpdate);
        UserSetting testUserSetting = userSettingList.get(userSettingList.size() - 1);
        assertThat(testUserSetting.getReceiveEmail()).isEqualTo(UPDATED_RECEIVE_EMAIL);
        assertThat(testUserSetting.getPrivateProfile()).isEqualTo(DEFAULT_PRIVATE_PROFILE);
        assertThat(testUserSetting.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUserSetting.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserSetting.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserSettingWithPatch() throws Exception {
        // Initialize the database
        userSettingRepository.saveAndFlush(userSetting);

        int databaseSizeBeforeUpdate = userSettingRepository.findAll().size();

        // Update the userSetting using partial update
        UserSetting partialUpdatedUserSetting = new UserSetting();
        partialUpdatedUserSetting.setId(userSetting.getId());

        partialUpdatedUserSetting
            .receiveEmail(UPDATED_RECEIVE_EMAIL)
            .privateProfile(UPDATED_PRIVATE_PROFILE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserSetting))
            )
            .andExpect(status().isOk());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeUpdate);
        UserSetting testUserSetting = userSettingList.get(userSettingList.size() - 1);
        assertThat(testUserSetting.getReceiveEmail()).isEqualTo(UPDATED_RECEIVE_EMAIL);
        assertThat(testUserSetting.getPrivateProfile()).isEqualTo(UPDATED_PRIVATE_PROFILE);
        assertThat(testUserSetting.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUserSetting.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserSetting.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserSetting() throws Exception {
        int databaseSizeBeforeUpdate = userSettingRepository.findAll().size();
        userSetting.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserSetting() throws Exception {
        int databaseSizeBeforeUpdate = userSettingRepository.findAll().size();
        userSetting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSetting))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserSetting() throws Exception {
        int databaseSizeBeforeUpdate = userSettingRepository.findAll().size();
        userSetting.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSettingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userSetting))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserSetting() throws Exception {
        // Initialize the database
        userSettingRepository.saveAndFlush(userSetting);

        int databaseSizeBeforeDelete = userSettingRepository.findAll().size();

        // Delete the userSetting
        restUserSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, userSetting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
