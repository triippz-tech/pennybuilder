package com.triippztech.pennybuilder.repository;

import com.triippztech.pennybuilder.domain.UserSetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, Long> {}
