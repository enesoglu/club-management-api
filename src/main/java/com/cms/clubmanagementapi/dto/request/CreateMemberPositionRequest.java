package com.cms.clubmanagementapi.dto.request;

import com.cms.clubmanagementapi.model.role.CrewCommittee;
import com.cms.clubmanagementapi.model.role.ExecutiveTitle;
import com.cms.clubmanagementapi.model.role.Team;
import lombok.Data;

@Data
public class CreateMemberPositionRequest {

    private Team team;
    private CrewCommittee crewCommittee;
    private ExecutiveTitle executiveTitle;

}
