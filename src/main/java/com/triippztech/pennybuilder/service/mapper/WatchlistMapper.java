package com.triippztech.pennybuilder.service.mapper;

import com.triippztech.pennybuilder.domain.*;
import com.triippztech.pennybuilder.service.dto.WatchlistDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Watchlist} and its DTO {@link WatchlistDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface WatchlistMapper extends EntityMapper<WatchlistDTO, Watchlist> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "id")
    WatchlistDTO toDto(Watchlist s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WatchlistDTO toDtoId(Watchlist watchlist);
}
