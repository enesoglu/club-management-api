package com.cms.clubmanagementapi.mapper;

import com.cms.clubmanagementapi.dto.ClubMemberDTO;
import com.cms.clubmanagementapi.dto.CreateClubMemberRequest;
import com.cms.clubmanagementapi.model.ClubMember;

import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClubMemberMapper {

    ClubMember toEntity(CreateClubMemberRequest request);

    List<ClubMember> toEntityList(List<CreateClubMemberRequest> members);

    ClubMemberDTO toDTO(ClubMember member);

    List<ClubMemberDTO> toDTOList(List<ClubMember> members);

}

/*     ClubMember member = new ClubMember();
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setSchoolNo(request.getSchoolNo());
        member.setNationalId(request.getNationalId());
        member.setFaculty(request.getFaculty());
        member.setDepartment(request.getDepartment());
        member.setYearOfStudy(request.getYearOfStudy());
        member.setPassword(request.getPassword());

        if (request.getRole() != null) {
            //TODO
            // member.setRole(request.getRole());
        }
*/

/*
    {
        if (member == null) {
            return null;
        }

        ClubMemberDTO dto = new ClubMemberDTO();
        dto.setId(member.getId());
        dto.setSchoolNo(member.getSchoolNo());
        dto.setName(member.getName());
        //TODO
        // dto.setRole(member.getRole());
        dto.setEmail(member.getEmail());
        dto.setPhone(member.getPhone());
        dto.setYearOfStudy(member.getYearOfStudy());
        dto.setFaculty(member.getFaculty());
        dto.setDepartment(member.getDepartment());
        dto.setRegistrationDate(member.getRegistrationDate());
        dto.setMembershipStatus(member.getMembershipStatus());

        return dto;
    }
*/
