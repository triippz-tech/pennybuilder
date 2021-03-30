package com.triippztech.pennybuilder.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.triippztech.pennybuilder.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WatchlistPositionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchlistPositionDTO.class);
        WatchlistPositionDTO watchlistPositionDTO1 = new WatchlistPositionDTO();
        watchlistPositionDTO1.setId(1L);
        WatchlistPositionDTO watchlistPositionDTO2 = new WatchlistPositionDTO();
        assertThat(watchlistPositionDTO1).isNotEqualTo(watchlistPositionDTO2);
        watchlistPositionDTO2.setId(watchlistPositionDTO1.getId());
        assertThat(watchlistPositionDTO1).isEqualTo(watchlistPositionDTO2);
        watchlistPositionDTO2.setId(2L);
        assertThat(watchlistPositionDTO1).isNotEqualTo(watchlistPositionDTO2);
        watchlistPositionDTO1.setId(null);
        assertThat(watchlistPositionDTO1).isNotEqualTo(watchlistPositionDTO2);
    }
}
