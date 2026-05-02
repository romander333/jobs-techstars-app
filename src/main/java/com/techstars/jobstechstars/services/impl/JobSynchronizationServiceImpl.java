package com.techstars.jobstechstars.services.impl;

import com.techstars.jobstechstars.dto.JobResponseDto;
import com.techstars.jobstechstars.mappers.JobMapper;
import com.techstars.jobstechstars.models.Job;
import com.techstars.jobstechstars.repositories.JobRepository;
import com.techstars.jobstechstars.services.JobSynchronizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobSynchronizationServiceImpl implements JobSynchronizationService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final TechstarsJobClientImpl techstarsJobClient;

    /**
     * Periodically synchronizes jobs from the external Techstars API.
     * This method runs asynchronously every hour. It fetches the latest jobs,
     * saves new or updated entries, and removes jobs that are no longer present
     * in the external source to ensure the local database remains a consistent
     * mirror of the remote data.
     */
    @Async
    @Transactional
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    @Override
    public void synchronizeJobs() {
        Set<JobResponseDto> jobResponseDtos = techstarsJobClient.downloadAllJobs();

        Set<Job> actualJobs = jobMapper.toModels(jobResponseDtos);

        long countElementsBeforeSaving = jobRepository.count();
        jobRepository.saveAll(actualJobs);

        Set<Long> actualJobId = actualJobs.stream()
                .map(Job::getId)
                .collect(Collectors.toSet());

        if (countElementsBeforeSaving > 0) {
            jobRepository.deleteAllJobsByIds(actualJobId);
        }
    }
}
