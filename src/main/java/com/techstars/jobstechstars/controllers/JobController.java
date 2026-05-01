package com.techstars.jobstechstars.controllers;

import com.techstars.jobstechstars.dto.JobResponseDto;
import com.techstars.jobstechstars.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @GetMapping()
    public Page<JobResponseDto> getAllJobs(Pageable pageable) {
        return jobService.getPageJobs(pageable);
    }
}
