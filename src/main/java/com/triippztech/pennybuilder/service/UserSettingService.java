package com.triippztech.pennybuilder.service;

import com.triippztech.pennybuilder.domain.UserSetting;
import com.triippztech.pennybuilder.repository.UserSettingRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserSetting}.
 */
@Service
@Transactional
public class UserSettingService {

    private final Logger log = LoggerFactory.getLogger(UserSettingService.class);

    private final UserSettingRepository userSettingRepository;

    public UserSettingService(UserSettingRepository userSettingRepository) {
        this.userSettingRepository = userSettingRepository;
    }

    /**
     * Save a userSetting.
     *
     * @param userSetting the entity to save.
     * @return the persisted entity.
     */
    public UserSetting save(UserSetting userSetting) {
        log.debug("Request to save UserSetting : {}", userSetting);
        return userSettingRepository.save(userSetting);
    }

    /**
     * Partially update a userSetting.
     *
     * @param userSetting the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserSetting> partialUpdate(UserSetting userSetting) {
        log.debug("Request to partially update UserSetting : {}", userSetting);

        return userSettingRepository
            .findById(userSetting.getId())
            .map(
                existingUserSetting -> {
                    if (userSetting.getReceiveEmail() != null) {
                        existingUserSetting.setReceiveEmail(userSetting.getReceiveEmail());
                    }
                    if (userSetting.getPrivateProfile() != null) {
                        existingUserSetting.setPrivateProfile(userSetting.getPrivateProfile());
                    }
                    if (userSetting.getPhoneNumber() != null) {
                        existingUserSetting.setPhoneNumber(userSetting.getPhoneNumber());
                    }
                    if (userSetting.getCreatedDate() != null) {
                        existingUserSetting.setCreatedDate(userSetting.getCreatedDate());
                    }
                    if (userSetting.getUpdatedDate() != null) {
                        existingUserSetting.setUpdatedDate(userSetting.getUpdatedDate());
                    }

                    return existingUserSetting;
                }
            )
            .map(userSettingRepository::save);
    }

    /**
     * Get all the userSettings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserSetting> findAll() {
        log.debug("Request to get all UserSettings");
        return userSettingRepository.findAll();
    }

    /**
     * Get one userSetting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserSetting> findOne(Long id) {
        log.debug("Request to get UserSetting : {}", id);
        return userSettingRepository.findById(id);
    }

    /**
     * Delete the userSetting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserSetting : {}", id);
        userSettingRepository.deleteById(id);
    }
}
