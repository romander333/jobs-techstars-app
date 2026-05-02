package com.techstars.jobstechstars.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRequestDto {
    @Schema(description = "Job title", example = "Deployment & Maintenance Software Engineer")
    private String title;
    @Schema(description = "Company name", example = "SoftServe")
    private String companyName;
    @Schema(description = "Location office", example = "Ukraine, Lviv")
    private String location;
    @Schema(description = "Work status job", example = "SENIOR")
    private String seniority;
}
