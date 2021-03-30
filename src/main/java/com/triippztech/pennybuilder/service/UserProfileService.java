package com.triippztech.pennybuilder.service;

import com.triippztech.pennybuilder.domain.UserProfile;
import com.triippztech.pennybuilder.repository.UserProfileRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserProfile}.
 */
@Service
@Transactional
public class UserProfileService {

    private final Logger log = LoggerFactory.getLogger(UserProfileService.class);

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    /**
     * Save a userProfile.
     *
     * @param userProfile the entity to save.
     * @return the persisted entity.
     */
    public UserProfile save(UserProfile userProfile) {
        log.debug("Request to save UserProfile : {}", userProfile);
        return userProfileRepository.save(userProfile);
    }

    /**
     * Partially update a userProfile.
     *
     * @param userProfile the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserProfile> partialUpdate(UserProfile userProfile) {
        log.debug("Request to partially update UserProfile : {}", userProfile);

        return userProfileRepository
            .findById(userProfile.getId())
            .map(
                existingUserProfile -> {
                    if (userProfile.getHeadline() != null) {
                        existingUserProfile.setHeadline(userProfile.getHeadline());
                    }
                    if (userProfile.getBio() != null) {
                        existingUserProfile.setBio(userProfile.getBio());
                    }
                    if (userProfile.getLocation() != null) {
                        existingUserProfile.setLocation(userProfile.getLocation());
                    }
                    if (userProfile.getNickname() != null) {
                        existingUserProfile.setNickname(userProfile.getNickname());
                    }
                    if (userProfile.getProfilePicture() != null) {
                        existingUserProfile.setProfilePicture(userProfile.getProfilePicture());
                    }
                    if (userProfile.getTradingViewUrl() != null) {
                        existingUserProfile.setTradingViewUrl(userProfile.getTradingViewUrl());
                    }
                    if (userProfile.getTwitterUrl() != null) {
                        existingUserProfile.setTwitterUrl(userProfile.getTwitterUrl());
                    }
                    if (userProfile.getFacebookUrl() != null) {
                        existingUserProfile.setFacebookUrl(userProfile.getFacebookUrl());
                    }
                    if (userProfile.getBithDate() != null) {
                        existingUserProfile.setBithDate(userProfile.getBithDate());
                    }
                    if (userProfile.getCreatedDate() != null) {
                        existingUserProfile.setCreatedDate(userProfile.getCreatedDate());
                    }
                    if (userProfile.getUpdatedDate() != null) {
                        existingUserProfile.setUpdatedDate(userProfile.getUpdatedDate());
                    }

                    return existingUserProfile;
                }
            )
            .map(userProfileRepository::save);
    }

    /**
     * Get all the userProfiles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserProfile> findAll() {
        log.debug("Request to get all UserProfiles");
        return userProfileRepository.findAll();
    }

    /**
     * Get one userProfile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserProfile> findOne(Long id) {
        log.debug("Request to get UserProfile : {}", id);
        return userProfileRepository.findById(id);
    }

    /**
     * Delete the userProfile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserProfile : {}", id);
        userProfileRepository.deleteById(id);
    }
}
