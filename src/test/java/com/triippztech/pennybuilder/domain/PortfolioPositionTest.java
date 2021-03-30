package com.triippztech.pennybuilder.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.triippztech.pennybuilder.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PortfolioPositionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortfolioPosition.class);
        PortfolioPosition portfolioPosition1 = new PortfolioPosition();
        portfolioPosition1.setId(1L);
        PortfolioPosition portfolioPosition2 = new PortfolioPosition();
        portfolioPosition2.setId(portfolioPosition1.getId());
        assertThat(portfolioPosition1).isEqualTo(portfolioPosition2);
        portfolioPosition2.setId(2L);
        assertThat(portfolioPosition1).isNotEqualTo(portfolioPosition2);
        portfolioPosition1.setId(null);
        assertThat(portfolioPosition1).isNotEqualTo(portfolioPosition2);
    }
}
