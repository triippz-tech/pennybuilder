package com.triippztech.pennybuilder.service.mapper;

import com.triippztech.pennybuilder.domain.*;
import com.triippztech.pennybuilder.service.dto.WatchlistPositionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WatchlistPosition} and its DTO {@link WatchlistPositionDTO}.
 */
@Mapper(componentModel = "spring", uses = { WatchlistMapper.class, AssetMapper.class })
public interface WatchlistPositionMapper extends EntityMapper<WatchlistPositionDTO, WatchlistPosition> {
    @Mapping(target = "watchlist", source = "watchlist", qualifiedByName = "id")
    @Mapping(target = "asset", source = "asset", qualifiedByName = "id")
    WatchlistPositionDTO toDto(WatchlistPosition s);
}
