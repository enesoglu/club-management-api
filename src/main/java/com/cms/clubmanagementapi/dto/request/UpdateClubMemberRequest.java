package com.cms.clubmanagementapi.dto.request;

import com.cms.clubmanagementapi.model.MemberStatus;
import com.cms.clubmanagementapi.model.YearOfStudy;
import lombok.Data;

@Data
public class UpdateClubMemberRequest {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String schoolNo;
    private YearOfStudy yearOfStudy;
    private String nationalId;
    private String faculty;
    private String department;
    private MemberStatus membershipStatus;
    private String password;
}
