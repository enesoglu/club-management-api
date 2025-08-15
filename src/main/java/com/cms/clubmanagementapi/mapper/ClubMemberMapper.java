package com.cms.clubmanagementapi.mapper;

import com.cms.clubmanagementapi.dto.ClubMemberDTO;
import com.cms.clubmanagementapi.dto.CreateClubMemberRequest;
import com.cms.clubmanagementapi.model.ClubMember;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PositionMapper.class)
public interface ClubMemberMapper {

    ClubMember toEntity(CreateClubMemberRequest request);

    List<ClubMember> toEntityList(List<CreateClubMemberRequest> members);

    ClubMemberDTO toDTO(ClubMember member);

    List<ClubMemberDTO> toDTOList(List<ClubMember> members);

}