package com.cms.clubmanagementapi.model.role;

public enum MemberRole {

    EXECUTIVE,
/*
    executive board members have a sub-value
    for indicating what title they have.

    see: model/ExecutiveTitle.java
*/

    CREW,
/*
    crew members have a sub-value for indicating which
    committee the member is in.

    see: model/CrewCommittee.java
*/

    SUPERVISORY,
    MEMBER,
    VETERAN

}