package com.cms.clubmanagementapi.validator;

import com.cms.clubmanagementapi.dto.request.CreateMemberPositionRequest;
import com.cms.clubmanagementapi.model.role.Team;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PositionValidator implements ConstraintValidator<ValidPosition, CreateMemberPositionRequest> {

    @Override
    public boolean isValid(CreateMemberPositionRequest request, ConstraintValidatorContext context) {

        // case of null is handled by PositionService
        if (request == null || request.getTeam() == null) {
            return true;
        }

        Team team = request.getTeam();

        // if team is EXECUTIVE, executiveTitle cannot be blank
        if (team == Team.EXECUTIVE) {
            if (request.getExecutiveTitle() == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Executive Title must be specified for the Executive Board.")
                        .addPropertyNode("executiveTitle")
                        .addConstraintViolation();
                return false;
            }
        }

        // if team is CREW, crewCommittee cannot be blank
        else if (team == Team.CREW) {
            if (request.getCrewCommittee() == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Crew Committee must be specified for the CREW")
                        .addPropertyNode("crewCommittee")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;    // there is no third option (i hope)
    }
}