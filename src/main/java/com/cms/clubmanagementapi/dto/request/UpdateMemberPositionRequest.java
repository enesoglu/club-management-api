package com.cms.clubmanagementapi.dto.request;

import com.cms.clubmanagementapi.dto.response.TermDTO;
import com.cms.clubmanagementapi.model.role.CrewCommittee;
import com.cms.clubmanagementapi.model.role.ExecutiveTitle;
import com.cms.clubmanagementapi.model.role.Team;
import com.cms.clubmanagementapi.validator.PositionValidationRequest;
import com.cms.clubmanagementapi.validator.ValidPosition;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidPosition
public class UpdateMemberPositionRequest implements PositionValidationRequest {

    @NotNull(message = "ID cannot be blank")
    private long id;
    @NotNull(message = "Team cannot be blank")
    private Team team;
    private CrewCommittee crewCommittee;
    private ExecutiveTitle executiveTitle;
    private TermDTO term;

}
