package com.triippztech.pennybuilder.service.mapper;

import com.triippztech.pennybuilder.domain.*;
import com.triippztech.pennybuilder.service.dto.PortfolioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Portfolio} and its DTO {@link PortfolioDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface PortfolioMapper extends EntityMapper<PortfolioDTO, Portfolio> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "id")
    PortfolioDTO toDto(Portfolio s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PortfolioDTO toDtoId(Portfolio portfolio);
}
