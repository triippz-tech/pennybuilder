package com.triippztech.pennybuilder.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.triippztech.pennybuilder.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WatchlistPositionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchlistPosition.class);
        WatchlistPosition watchlistPosition1 = new WatchlistPosition();
        watchlistPosition1.setId(1L);
        WatchlistPosition watchlistPosition2 = new WatchlistPosition();
        watchlistPosition2.setId(watchlistPosition1.getId());
        assertThat(watchlistPosition1).isEqualTo(watchlistPosition2);
        watchlistPosition2.setId(2L);
        assertThat(watchlistPosition1).isNotEqualTo(watchlistPosition2);
        watchlistPosition1.setId(null);
        assertThat(watchlistPosition1).isNotEqualTo(watchlistPosition2);
    }
}
