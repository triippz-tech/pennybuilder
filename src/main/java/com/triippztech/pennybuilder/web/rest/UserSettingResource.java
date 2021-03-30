package com.triippztech.pennybuilder.web.rest;

import com.triippztech.pennybuilder.domain.UserSetting;
import com.triippztech.pennybuilder.repository.UserSettingRepository;
import com.triippztech.pennybuilder.service.UserSettingService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.triippztech.pennybuilder.domain.UserSetting}.
 */
@RestController
@RequestMapping("/api")
public class UserSettingResource {

    private final Logger log = LoggerFactory.getLogger(UserSettingResource.class);

    private static final String ENTITY_NAME = "userSetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserSettingService userSettingService;

    private final UserSettingRepository userSettingRepository;

    public UserSettingResource(UserSettingService userSettingService, UserSettingRepository userSettingRepository) {
        this.userSettingService = userSettingService;
        this.userSettingRepository = userSettingRepository;
    }

    /**
     * {@code POST  /user-settings} : Create a new userSetting.
     *
     * @param userSetting the userSetting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userSetting, or with status {@code 400 (Bad Request)} if the userSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-settings")
    public ResponseEntity<UserSetting> createUserSetting(@Valid @RequestBody UserSetting userSetting) throws URISyntaxException {
        log.debug("REST request to save UserSetting : {}", userSetting);
        if (userSetting.getId() != null) {
            throw new BadRequestAlertException("A new userSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserSetting result = userSettingService.save(userSetting);
        return ResponseEntity
            .created(new URI("/api/user-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-settings/:id} : Updates an existing userSetting.
     *
     * @param id the id of the userSetting to save.
     * @param userSetting the userSetting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSetting,
     * or with status {@code 400 (Bad Request)} if the userSetting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userSetting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-settings/{id}")
    public ResponseEntity<UserSetting> updateUserSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserSetting userSetting
    ) throws URISyntaxException {
        log.debug("REST request to update UserSetting : {}, {}", id, userSetting);
        if (userSetting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userSetting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserSetting result = userSettingService.save(userSetting);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userSetting.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-settings/:id} : Partial updates given fields of an existing userSetting, field will ignore if it is null
     *
     * @param id the id of the userSetting to save.
     * @param userSetting the userSetting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSetting,
     * or with status {@code 400 (Bad Request)} if the userSetting is not valid,
     * or with status {@code 404 (Not Found)} if the userSetting is not found,
     * or with status {@code 500 (Internal Server Error)} if the userSetting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-settings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<UserSetting> partialUpdateUserSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserSetting userSetting
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserSetting partially : {}, {}", id, userSetting);
        if (userSetting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userSetting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserSetting> result = userSettingService.partialUpdate(userSetting);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userSetting.getId().toString())
        );
    }

    /**
     * {@code GET  /user-settings} : get all the userSettings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userSettings in body.
     */
    @GetMapping("/user-settings")
    public List<UserSetting> getAllUserSettings() {
        log.debug("REST request to get all UserSettings");
        return userSettingService.findAll();
    }

    /**
     * {@code GET  /user-settings/:id} : get the "id" userSetting.
     *
     * @param id the id of the userSetting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userSetting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-settings/{id}")
    public ResponseEntity<UserSetting> getUserSetting(@PathVariable Long id) {
        log.debug("REST request to get UserSetting : {}", id);
        Optional<UserSetting> userSetting = userSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userSetting);
    }

    /**
     * {@code DELETE  /user-settings/:id} : delete the "id" userSetting.
     *
     * @param id the id of the userSetting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-settings/{id}")
    public ResponseEntity<Void> deleteUserSetting(@PathVariable Long id) {
        log.debug("REST request to delete UserSetting : {}", id);
        userSettingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
