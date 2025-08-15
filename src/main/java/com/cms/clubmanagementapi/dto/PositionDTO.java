package com.cms.clubmanagementapi.dto;

import com.cms.clubmanagementapi.model.role.CrewCommittee;
import com.cms.clubmanagementapi.model.role.ExecutiveTitle;
import com.cms.clubmanagementapi.model.role.Team;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PositionDTO {

    private Team team;
    private LocalDate startDate;
    private LocalDate endDate;
    private CrewCommittee crewCommittee;
    private ExecutiveTitle executiveTitle;
    private boolean isActive;

}
