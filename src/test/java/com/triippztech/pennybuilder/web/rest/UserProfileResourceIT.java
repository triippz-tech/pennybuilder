package com.triippztech.pennybuilder.web.rest;

import static com.triippztech.pennybuilder.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.triippztech.pennybuilder.IntegrationTest;
import com.triippztech.pennybuilder.domain.UserProfile;
import com.triippztech.pennybuilder.repository.UserProfileRepository;
import java.time.Instant;
import java.time.LocalDate;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link UserProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserProfileResourceIT {

    private static final String DEFAULT_HEADLINE = "AAAAAAAAAA";
    private static final String UPDATED_HEADLINE = "BBBBBBBBBB";

    private static final String DEFAULT_BIO = "AAAAAAAAAA";
    private static final String UPDATED_BIO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_NICKNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PICTURE = "BBBBBBBBBB";

    private static final String DEFAULT_TRADING_VIEW_URL = "AAAAAAAAAA";
    private static final String UPDATED_TRADING_VIEW_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER_URL = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK_URL = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_URL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BITH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BITH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/user-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserProfileMockMvc;

    private UserProfile userProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .headline(DEFAULT_HEADLINE)
            .bio(DEFAULT_BIO)
            .location(DEFAULT_LOCATION)
            .nickname(DEFAULT_NICKNAME)
            .profilePicture(DEFAULT_PROFILE_PICTURE)
            .tradingViewUrl(DEFAULT_TRADING_VIEW_URL)
            .twitterUrl(DEFAULT_TWITTER_URL)
            .facebookUrl(DEFAULT_FACEBOOK_URL)
            .bithDate(DEFAULT_BITH_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return userProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createUpdatedEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .headline(UPDATED_HEADLINE)
            .bio(UPDATED_BIO)
            .location(UPDATED_LOCATION)
            .nickname(UPDATED_NICKNAME)
            .profilePicture(UPDATED_PROFILE_PICTURE)
            .tradingViewUrl(UPDATED_TRADING_VIEW_URL)
            .twitterUrl(UPDATED_TWITTER_URL)
            .facebookUrl(UPDATED_FACEBOOK_URL)
            .bithDate(UPDATED_BITH_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return userProfile;
    }

    @BeforeEach
    public void initTest() {
        userProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createUserProfile() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();
        // Create the UserProfile
        restUserProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userProfile)))
            .andExpect(status().isCreated());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate + 1);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getHeadline()).isEqualTo(DEFAULT_HEADLINE);
        assertThat(testUserProfile.getBio()).isEqualTo(DEFAULT_BIO);
        assertThat(testUserProfile.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testUserProfile.getNickname()).isEqualTo(DEFAULT_NICKNAME);
        assertThat(testUserProfile.getProfilePicture()).isEqualTo(DEFAULT_PROFILE_PICTURE);
        assertThat(testUserProfile.getTradingViewUrl()).isEqualTo(DEFAULT_TRADING_VIEW_URL);
        assertThat(testUserProfile.getTwitterUrl()).isEqualTo(DEFAULT_TWITTER_URL);
        assertThat(testUserProfile.getFacebookUrl()).isEqualTo(DEFAULT_FACEBOOK_URL);
        assertThat(testUserProfile.getBithDate()).isEqualTo(DEFAULT_BITH_DATE);
        assertThat(testUserProfile.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserProfile.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createUserProfileWithExistingId() throws Exception {
        // Create the UserProfile with an existing ID
        userProfile.setId(1L);

        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userProfile)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserProfiles() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList
        restUserProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].headline").value(hasItem(DEFAULT_HEADLINE)))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].nickname").value(hasItem(DEFAULT_NICKNAME)))
            .andExpect(jsonPath("$.[*].profilePicture").value(hasItem(DEFAULT_PROFILE_PICTURE)))
            .andExpect(jsonPath("$.[*].tradingViewUrl").value(hasItem(DEFAULT_TRADING_VIEW_URL)))
            .andExpect(jsonPath("$.[*].twitterUrl").value(hasItem(DEFAULT_TWITTER_URL)))
            .andExpect(jsonPath("$.[*].facebookUrl").value(hasItem(DEFAULT_FACEBOOK_URL)))
            .andExpect(jsonPath("$.[*].bithDate").value(hasItem(DEFAULT_BITH_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }

    @Test
    @Transactional
    void getUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get the userProfile
        restUserProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, userProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userProfile.getId().intValue()))
            .andExpect(jsonPath("$.headline").value(DEFAULT_HEADLINE))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.nickname").value(DEFAULT_NICKNAME))
            .andExpect(jsonPath("$.profilePicture").value(DEFAULT_PROFILE_PICTURE))
            .andExpect(jsonPath("$.tradingViewUrl").value(DEFAULT_TRADING_VIEW_URL))
            .andExpect(jsonPath("$.twitterUrl").value(DEFAULT_TWITTER_URL))
            .andExpect(jsonPath("$.facebookUrl").value(DEFAULT_FACEBOOK_URL))
            .andExpect(jsonPath("$.bithDate").value(DEFAULT_BITH_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingUserProfile() throws Exception {
        // Get the userProfile
        restUserProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile
        UserProfile updatedUserProfile = userProfileRepository.findById(userProfile.getId()).get();
        // Disconnect from session so that the updates on updatedUserProfile are not directly saved in db
        em.detach(updatedUserProfile);
        updatedUserProfile
            .headline(UPDATED_HEADLINE)
            .bio(UPDATED_BIO)
            .location(UPDATED_LOCATION)
            .nickname(UPDATED_NICKNAME)
            .profilePicture(UPDATED_PROFILE_PICTURE)
            .tradingViewUrl(UPDATED_TRADING_VIEW_URL)
            .twitterUrl(UPDATED_TWITTER_URL)
            .facebookUrl(UPDATED_FACEBOOK_URL)
            .bithDate(UPDATED_BITH_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserProfile))
            )
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getHeadline()).isEqualTo(UPDATED_HEADLINE);
        assertThat(testUserProfile.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testUserProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testUserProfile.getNickname()).isEqualTo(UPDATED_NICKNAME);
        assertThat(testUserProfile.getProfilePicture()).isEqualTo(UPDATED_PROFILE_PICTURE);
        assertThat(testUserProfile.getTradingViewUrl()).isEqualTo(UPDATED_TRADING_VIEW_URL);
        assertThat(testUserProfile.getTwitterUrl()).isEqualTo(UPDATED_TWITTER_URL);
        assertThat(testUserProfile.getFacebookUrl()).isEqualTo(UPDATED_FACEBOOK_URL);
        assertThat(testUserProfile.getBithDate()).isEqualTo(UPDATED_BITH_DATE);
        assertThat(testUserProfile.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserProfile.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();
        userProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();
        userProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();
        userProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userProfile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserProfileWithPatch() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile using partial update
        UserProfile partialUpdatedUserProfile = new UserProfile();
        partialUpdatedUserProfile.setId(userProfile.getId());

        partialUpdatedUserProfile
            .bio(UPDATED_BIO)
            .bithDate(UPDATED_BITH_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserProfile))
            )
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getHeadline()).isEqualTo(DEFAULT_HEADLINE);
        assertThat(testUserProfile.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testUserProfile.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testUserProfile.getNickname()).isEqualTo(DEFAULT_NICKNAME);
        assertThat(testUserProfile.getProfilePicture()).isEqualTo(DEFAULT_PROFILE_PICTURE);
        assertThat(testUserProfile.getTradingViewUrl()).isEqualTo(DEFAULT_TRADING_VIEW_URL);
        assertThat(testUserProfile.getTwitterUrl()).isEqualTo(DEFAULT_TWITTER_URL);
        assertThat(testUserProfile.getFacebookUrl()).isEqualTo(DEFAULT_FACEBOOK_URL);
        assertThat(testUserProfile.getBithDate()).isEqualTo(UPDATED_BITH_DATE);
        assertThat(testUserProfile.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserProfile.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserProfileWithPatch() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile using partial update
        UserProfile partialUpdatedUserProfile = new UserProfile();
        partialUpdatedUserProfile.setId(userProfile.getId());

        partialUpdatedUserProfile
            .headline(UPDATED_HEADLINE)
            .bio(UPDATED_BIO)
            .location(UPDATED_LOCATION)
            .nickname(UPDATED_NICKNAME)
            .profilePicture(UPDATED_PROFILE_PICTURE)
            .tradingViewUrl(UPDATED_TRADING_VIEW_URL)
            .twitterUrl(UPDATED_TWITTER_URL)
            .facebookUrl(UPDATED_FACEBOOK_URL)
            .bithDate(UPDATED_BITH_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restUserProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserProfile))
            )
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getHeadline()).isEqualTo(UPDATED_HEADLINE);
        assertThat(testUserProfile.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testUserProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testUserProfile.getNickname()).isEqualTo(UPDATED_NICKNAME);
        assertThat(testUserProfile.getProfilePicture()).isEqualTo(UPDATED_PROFILE_PICTURE);
        assertThat(testUserProfile.getTradingViewUrl()).isEqualTo(UPDATED_TRADING_VIEW_URL);
        assertThat(testUserProfile.getTwitterUrl()).isEqualTo(UPDATED_TWITTER_URL);
        assertThat(testUserProfile.getFacebookUrl()).isEqualTo(UPDATED_FACEBOOK_URL);
        assertThat(testUserProfile.getBithDate()).isEqualTo(UPDATED_BITH_DATE);
        assertThat(testUserProfile.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserProfile.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();
        userProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();
        userProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();
        userProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserProfileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeDelete = userProfileRepository.findAll().size();

        // Delete the userProfile
        restUserProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, userProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
