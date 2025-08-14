package com.cms.clubmanagementapi.model.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Team {

    EXECUTIVE("Executive Board"),
/*
    executive board members have a sub-value
    for indicating what title they have.

    see: model/ExecutiveTitle.java
*/

    CREW("Crew"),
/*
    crew members have a sub-value for indicating which
    committee the member is in.

    see: model/CrewCommittee.java
*/

    SUPERVISORY("Supervisory Board"),
    MEMBER("Member"),
    VETERAN("Veteran");

    private final String displayName;

}