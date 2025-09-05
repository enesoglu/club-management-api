package com.cms.clubmanagementapi.validator;

import com.cms.clubmanagementapi.model.role.CrewCommittee;
import com.cms.clubmanagementapi.model.role.ExecutiveTitle;
import com.cms.clubmanagementapi.model.role.Team;

public interface PositionValidationRequest {

    Team getTeam();
    CrewCommittee getCrewCommittee();
    ExecutiveTitle getExecutiveTitle();

}