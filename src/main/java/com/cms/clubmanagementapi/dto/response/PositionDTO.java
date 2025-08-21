package com.cms.clubmanagementapi.dto.response;

import com.cms.clubmanagementapi.model.role.CrewCommittee;
import com.cms.clubmanagementapi.model.role.ExecutiveTitle;
import com.cms.clubmanagementapi.model.role.Team;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PositionDTO {

    private Team team;
    private LocalDate startDate;
    private LocalDate endDate;
    private CrewCommittee crewCommittee;
    private ExecutiveTitle executiveTitle;
    private boolean isActive;
    private TermDTO term;

}
