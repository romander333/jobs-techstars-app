package com.techstars.jobstechstars.services;

import com.techstars.jobstechstars.dto.JobResponseDto;

import java.util.Set;

public interface TechstarsJobClient {
    Set<JobResponseDto> downloadAllJobs();
}
