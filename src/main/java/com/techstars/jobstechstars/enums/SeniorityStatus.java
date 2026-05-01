package com.techstars.jobstechstars.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum SeniorityStatus {
    SENIOR("senior"),
    VICE_PRESIDENT("vice_president"),
    MID_SENIOR("mid_senior"),
    ENTRY_LEVEL("entry_level"),
    DIRECTOR("director"),
    ASSOCIATE("associate"),
    INTERNSHIP("internship"),
    CXO("cxo");

    private final String jsonValue;

    SeniorityStatus(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    public static SeniorityStatus fromString(String jsonValue) {
        if (jsonValue == null) return null;

        for (SeniorityStatus status : SeniorityStatus.values()) {
            if (status.jsonValue.equalsIgnoreCase(jsonValue)) {
                return status;
            }
        }
        log.warn("Invalid seniority status: " + jsonValue);
        throw new IllegalArgumentException("Invalid seniority status: " + jsonValue);
    }
}
