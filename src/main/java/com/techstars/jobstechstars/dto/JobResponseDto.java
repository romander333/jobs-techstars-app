package com.techstars.jobstechstars.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JobResponseDto(@JsonProperty("title") String title,
                             @JsonProperty("locations") List<String> locations,
                             @JsonProperty("url") String url,
                             @JsonProperty("seniority") String seniority,
                             @JsonProperty("organization") OrganizationDto organization) {

    public record OrganizationDto(@JsonProperty("name") String companyName,
                                  @JsonProperty("industry_tags") List<String> industryTags) {
    }
}
