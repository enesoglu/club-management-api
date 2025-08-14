package com.cms.clubmanagementapi.dto;

import com.cms.clubmanagementapi.model.role.CrewCommittee;
import com.cms.clubmanagementapi.model.role.ExecutiveTitle;
import com.cms.clubmanagementapi.model.role.Team;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateClubMemberPosition {

    private Team team;
    private CrewCommittee crewCommittee;
    private ExecutiveTitle executiveTitle;

}
