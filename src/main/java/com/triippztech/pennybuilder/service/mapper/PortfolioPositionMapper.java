package com.triippztech.pennybuilder.service.mapper;

import com.triippztech.pennybuilder.domain.*;
import com.triippztech.pennybuilder.service.dto.PortfolioPositionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PortfolioPosition} and its DTO {@link PortfolioPositionDTO}.
 */
@Mapper(componentModel = "spring", uses = { PortfolioMapper.class, AssetMapper.class })
public interface PortfolioPositionMapper extends EntityMapper<PortfolioPositionDTO, PortfolioPosition> {
    @Mapping(target = "portfolio", source = "portfolio", qualifiedByName = "id")
    @Mapping(target = "asset", source = "asset", qualifiedByName = "id")
    PortfolioPositionDTO toDto(PortfolioPosition s);
}
