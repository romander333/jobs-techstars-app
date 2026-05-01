package com.techstars.jobstechstars.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRequestDto {
    private String title;
    private String companyName;
    private String location;
    private String seniority;
}
