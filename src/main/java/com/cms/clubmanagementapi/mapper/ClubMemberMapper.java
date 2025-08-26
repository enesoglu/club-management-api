package com.cms.clubmanagementapi.mapper;

import com.cms.clubmanagementapi.dto.response.ClubMemberDTO;
import com.cms.clubmanagementapi.dto.request.CreateClubMemberRequest;
import com.cms.clubmanagementapi.model.ClubMember;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = PositionMapper.class)
public interface ClubMemberMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "positions", ignore = true)
    ClubMember toEntity(CreateClubMemberRequest request);

    List<ClubMember> toEntityList(List<CreateClubMemberRequest> members);

    ClubMemberDTO toDTO(ClubMember member);

    List<ClubMemberDTO> toDTOList(List<ClubMember> members);

}