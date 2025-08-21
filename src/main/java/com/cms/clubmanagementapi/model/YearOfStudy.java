package com.cms.clubmanagementapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum YearOfStudy {

    PREPARATION("Preparation", 0),
    FIRST_YEAR("First Year", 1),
    SECOND_YEAR("Second Year", 2),
    THIRD_YEAR("Third Year", 3),
    FOURTH_YEAR("Fourth Year", 4),
    GRADUATED("Graduated", -1),;

    private final String displayName;
    private final int intValue;

}
