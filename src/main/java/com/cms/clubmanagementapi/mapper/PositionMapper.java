package com.cms.clubmanagementapi.mapper;

import com.cms.clubmanagementapi.dto.response.PositionDTO;
import com.cms.clubmanagementapi.model.role.Position;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    PositionDTO toDTO(Position position);

    List<PositionDTO> toDTOList(List<Position> positions);

}
