package com.cms.clubmanagementapi.model.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExecutiveTitle {

    PRESIDENT("President"),
    VICE_PRESIDENT("Vice President"),
    BOARD_MEMBER("Board Member"),

    DIRECTOR_OF_PR("Director of PR & Social Media"),
    DIRECTOR_OF_SPONSORSHIP("Director of Sponsorships"),
    DIRECTOR_OF_COMMUNICATIONS("Director of Communications"),
    DIRECTOR_OF_YOUTUBE("Director of YouTube"),
    DIRECTOR_OF_PROJECTS("Director of Projects"),
    DIRECTOR_OF_PUBLICATIONS("Director of Publications"),
    DIRECTOR_OF_DESIGN("Director of Design");

    private final String displayName;

}
