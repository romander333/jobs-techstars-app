package com.techstars.jobstechstars.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JobWrapperDto(
        @JsonProperty("results") JobResult jobResult) {


    public record JobResult(
            @JsonProperty("jobs") Set<JobResponseDto> jobResponseDto,
            @JsonProperty("count") Integer count) {
    }
}
