package com.cms.clubmanagementapi.dto;

import com.cms.clubmanagementapi.model.MemberStatus;
import com.cms.clubmanagementapi.model.role.MemberRole;
import lombok.Data;

//  create request dto
@Data
public class CreateClubMemberRequest {

    private String name;
    private String email;
    private String phone;
    private String schoolNo;
    private String nationalId;
    private String faculty;
    private String department;
    private MemberRole role;
    private String yearOfStudy;
    private String password;
    private MemberStatus membershipStatus;

}
