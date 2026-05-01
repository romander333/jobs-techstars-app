package com.techstars.jobstechstars.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JobPageableDto(
        @JsonProperty("hitsPerPage") int hitsPerPage,
        @JsonProperty("page") int page) {
}
