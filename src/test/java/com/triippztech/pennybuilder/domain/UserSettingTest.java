package com.triippztech.pennybuilder.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.triippztech.pennybuilder.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserSettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSetting.class);
        UserSetting userSetting1 = new UserSetting();
        userSetting1.setId(1L);
        UserSetting userSetting2 = new UserSetting();
        userSetting2.setId(userSetting1.getId());
        assertThat(userSetting1).isEqualTo(userSetting2);
        userSetting2.setId(2L);
        assertThat(userSetting1).isNotEqualTo(userSetting2);
        userSetting1.setId(null);
        assertThat(userSetting1).isNotEqualTo(userSetting2);
    }
}
