package com.techstars.jobstechstars.services;

import com.techstars.jobstechstars.dto.JobRequestDto;
import com.techstars.jobstechstars.dto.JobResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {
    Page<JobResponseDto> getPageJobs(JobRequestDto jobRequestDto, Pageable pageable);
}
