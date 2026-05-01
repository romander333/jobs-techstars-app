package com.techstars.jobstechstars.controllers;

import com.techstars.jobstechstars.dto.JobRequestDto;
import com.techstars.jobstechstars.dto.JobResponseDto;
import com.techstars.jobstechstars.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @GetMapping()
    public ResponseEntity<Page<JobResponseDto>> getAllJobs(JobRequestDto jobRequestDto, Pageable pageable) {
        Page<JobResponseDto> pageJobs = jobService.getPageJobs(jobRequestDto, pageable);
        return ResponseEntity.ok(pageJobs);
    }
}
