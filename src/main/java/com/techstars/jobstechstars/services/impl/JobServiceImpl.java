package com.techstars.jobstechstars.services.impl;

import com.techstars.jobstechstars.dto.JobRequestDto;
import com.techstars.jobstechstars.dto.JobResponseDto;
import com.techstars.jobstechstars.mappers.JobMapper;
import com.techstars.jobstechstars.repositories.JobRepository;
import com.techstars.jobstechstars.services.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    /**
     * Retrieves a paginated list of jobs based on the provided filtering criteria.
     *
     * @param jobRequestDto the DTO containing filter criteria such as title, company name, seniority, and location
     * @param pageable      the pagination information
     * @return a page of JobResponseDto objects matching the filters
     */
    @Override
    public Page<JobResponseDto> getPageJobs(JobRequestDto jobRequestDto, Pageable pageable) {
        return jobRepository.findByFilters(
                        jobRequestDto.getTitle(),
                        jobRequestDto.getCompanyName(),
                        jobRequestDto.getSeniority(),
                        jobRequestDto.getLocation(),
                        pageable
                )
                .map(jobMapper::toDto);
    }
}
