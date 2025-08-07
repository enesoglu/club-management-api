package com.cms.clubmanagementapi.mapper;

import com.cms.clubmanagementapi.dto.ClubMemberDTO;
import com.cms.clubmanagementapi.dto.CreateClubMemberRequest;
import com.cms.clubmanagementapi.model.ClubMember;
import org.springframework.stereotype.Component;

@Component
public class ClubMemberMapper {

    public ClubMember toEntity(CreateClubMemberRequest request) {
        if (request == null) {
            return null;
        }
        ClubMember member = new ClubMember();
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
            member.setRole(request.getRole());
        }

        return member;
    }

    public ClubMemberDTO toDTO(ClubMember member) {
        if (member == null) {
            return null;
        }

        ClubMemberDTO dto = new ClubMemberDTO();
        dto.setId(member.getId());
        dto.setSchoolNo(member.getSchoolNo());
        dto.setName(member.getName());
        dto.setRole(member.getRole());
        dto.setEmail(member.getEmail());
        dto.setPhone(member.getPhone());
        dto.setYearOfStudy(member.getYearOfStudy());
        dto.setFaculty(member.getFaculty());
        dto.setDepartment(member.getDepartment());
        dto.setRegistrationDate(member.getRegistrationDate());
        dto.setMembershipStatus(member.getMembershipStatus());

        return dto;
    }
}
