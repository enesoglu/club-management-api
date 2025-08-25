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
        boolean isValid = true;

        switch (team) {

            // if team is EXECUTIVE, executiveTitle cannot be blank
            // and crewCommittee must be blank
            case EXECUTIVE:
                if (request.getExecutiveTitle() == null) {
                    isValid = false;
                    context.buildConstraintViolationWithTemplate("Executive Title must be specified for the Executive Board.")
                            .addPropertyNode("executiveTitle")
                            .addConstraintViolation();
                }
                if (request.getCrewCommittee() != null) {
                    isValid = false;
                    context.buildConstraintViolationWithTemplate("Crew Committee must be null for the Executive Board.")
                            .addPropertyNode("crewCommittee")
                            .addConstraintViolation();
                }
                break;

            // if team is CREW, crewCommittee cannot be blank
            // and executiveTitle must be blank
            case CREW:
                if (request.getCrewCommittee() == null) {
                    isValid = false;
                    context.buildConstraintViolationWithTemplate("Crew Committee must be specified for the CREW.")
                            .addPropertyNode("crewCommittee")
                            .addConstraintViolation();
                }
                if (request.getExecutiveTitle() != null) {
                    isValid = false;
                    context.buildConstraintViolationWithTemplate("Executive Title must be null for the CREW.")
                            .addPropertyNode("executiveTitle")
                            .addConstraintViolation();
                }
                break;

            // other Teams like VETERAN, MEMBER, SUPERVISORY.
            case SUPERVISORY:
            case MEMBER:
            case VETERAN:
                if (request.getExecutiveTitle() != null) {
                    isValid = false;
                    context.buildConstraintViolationWithTemplate("Executive Title must be null for %s.".formatted(request.getTeam()))
                            .addPropertyNode("executiveTitle")
                            .addConstraintViolation();
                }
                if (request.getCrewCommittee() != null) {
                    isValid = false;
                    context.buildConstraintViolationWithTemplate("Crew Committee must be null for %s.".formatted(request.getTeam()))
                            .addPropertyNode("crewCommittee")
                            .addConstraintViolation();
                }
                break;
        }
        return isValid;
    }

}