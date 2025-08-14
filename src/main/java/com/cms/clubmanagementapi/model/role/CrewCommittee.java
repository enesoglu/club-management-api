package com.cms.clubmanagementapi.model.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CrewCommittee {

    ORGANIZATION("Organization  Committee"),
    MEDIUM("Medium-TechSheet Committee"),
    SPONSORSHIP("Sponsorship Committee"),
    DESIGN("Design Committee"),
    PR("PR&Social Media Committee");

    private final String displayName;

}
