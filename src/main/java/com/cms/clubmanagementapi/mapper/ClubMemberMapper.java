package com.cms.clubmanagementapi.mapper;

import com.cms.clubmanagementapi.dto.ClubMemberDTO;
import com.cms.clubmanagementapi.dto.CreateClubMemberRequest;
import com.cms.clubmanagementapi.model.ClubMember;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClubMemberMapper {

    //TODO "temporarily ignoring position variable"
    @Mapping(target = "positions", ignore = true)
    ClubMember toEntity(CreateClubMemberRequest request);

    List<ClubMember> toEntityList(List<CreateClubMemberRequest> members);

    ClubMemberDTO toDTO(ClubMember member);

    List<ClubMemberDTO> toDTOList(List<ClubMember> members);

}