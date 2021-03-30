package com.triippztech.pennybuilder.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.triippztech.pennybuilder.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PortfolioPositionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortfolioPositionDTO.class);
        PortfolioPositionDTO portfolioPositionDTO1 = new PortfolioPositionDTO();
        portfolioPositionDTO1.setId(1L);
        PortfolioPositionDTO portfolioPositionDTO2 = new PortfolioPositionDTO();
        assertThat(portfolioPositionDTO1).isNotEqualTo(portfolioPositionDTO2);
        portfolioPositionDTO2.setId(portfolioPositionDTO1.getId());
        assertThat(portfolioPositionDTO1).isEqualTo(portfolioPositionDTO2);
        portfolioPositionDTO2.setId(2L);
        assertThat(portfolioPositionDTO1).isNotEqualTo(portfolioPositionDTO2);
        portfolioPositionDTO1.setId(null);
        assertThat(portfolioPositionDTO1).isNotEqualTo(portfolioPositionDTO2);
    }
}
