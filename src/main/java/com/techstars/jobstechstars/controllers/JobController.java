package com.techstars.jobstechstars.controllers;

import com.techstars.jobstechstars.dto.JobRequestDto;
import com.techstars.jobstechstars.dto.JobResponseDto;
import com.techstars.jobstechstars.services.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get all jobs",
            description = "Return paginated list of jobs with sorting and filtering support"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Jobs return successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination, sorting or filtering parameters")
    })
    @GetMapping()
    public ResponseEntity<Page<JobResponseDto>> getAllJobs(
            @Parameter(
                    description = "Contains the following parameters such as: " +
                            "title, companyName, location, seniority, by which it is possible to filter",
            example = "title = Sales, companyName = DataCamp, location = Argentina, seniority = ASSOCIATE")
            JobRequestDto jobRequestDto,
            @Parameter(description = "Page number, starts from 0",
    example = "0")Pageable pageable) {
        Page<JobResponseDto> pageJobs = jobService.getPageJobs(jobRequestDto, pageable);
        return ResponseEntity.ok(pageJobs);
    }
}
