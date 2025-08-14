package com.cms.clubmanagementapi.dto;

import com.cms.clubmanagementapi.model.role.MemberRole;
import com.cms.clubmanagementapi.model.MemberStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ClubMemberDTO {

    private Long id;
    private String schoolNo;
    private String name;
    private String email;
    private String phone;
    private String yearOfStudy;
    private String faculty;
    private String department;
    private LocalDate registrationDate;
    private MemberRole role;
    private MemberStatus membershipStatus;
}
