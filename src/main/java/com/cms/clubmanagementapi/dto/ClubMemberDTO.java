package com.cms.clubmanagementapi.dto;

import com.cms.clubmanagementapi.model.MemberStatus;
import com.cms.clubmanagementapi.model.role.Position;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

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
    private MemberStatus membershipStatus;
    private List<Position> positions;

}
