package com.triippztech.pennybuilder.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.triippztech.pennybuilder.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WatchlistTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Watchlist.class);
        Watchlist watchlist1 = new Watchlist();
        watchlist1.setId(1L);
        Watchlist watchlist2 = new Watchlist();
        watchlist2.setId(watchlist1.getId());
        assertThat(watchlist1).isEqualTo(watchlist2);
        watchlist2.setId(2L);
        assertThat(watchlist1).isNotEqualTo(watchlist2);
        watchlist1.setId(null);
        assertThat(watchlist1).isNotEqualTo(watchlist2);
    }
}
