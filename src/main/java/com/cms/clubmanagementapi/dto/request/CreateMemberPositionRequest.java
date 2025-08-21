package com.cms.clubmanagementapi.dto.request;

import com.cms.clubmanagementapi.model.role.CrewCommittee;
import com.cms.clubmanagementapi.model.role.ExecutiveTitle;
import com.cms.clubmanagementapi.model.role.Team;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMemberPositionRequest {

    @NotNull(message = "Team cannot be blank")
    private Team team;
    private CrewCommittee crewCommittee;
    private ExecutiveTitle executiveTitle;

}
