package com.triippztech.pennybuilder.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WatchlistPositionMapperTest {

    private WatchlistPositionMapper watchlistPositionMapper;

    @BeforeEach
    public void setUp() {
        watchlistPositionMapper = new WatchlistPositionMapperImpl();
    }
}
