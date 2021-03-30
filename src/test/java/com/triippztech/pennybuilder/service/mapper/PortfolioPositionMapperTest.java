package com.triippztech.pennybuilder.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PortfolioPositionMapperTest {

    private PortfolioPositionMapper portfolioPositionMapper;

    @BeforeEach
    public void setUp() {
        portfolioPositionMapper = new PortfolioPositionMapperImpl();
    }
}
