package com.triippztech.pennybuilder.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WatchlistMapperTest {

    private WatchlistMapper watchlistMapper;

    @BeforeEach
    public void setUp() {
        watchlistMapper = new WatchlistMapperImpl();
    }
}
