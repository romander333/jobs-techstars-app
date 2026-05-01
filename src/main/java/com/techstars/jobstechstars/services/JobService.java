package com.techstars.jobstechstars.services;

import com.techstars.jobstechstars.dto.JobResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface JobService {
    void synchronizeJobs();
    Set<JobResponseDto> downloadAllJobs();
    Page<JobResponseDto> getPageJobs(Pageable pageable);
}
