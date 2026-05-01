package com.techstars.jobstechstars.services.impl;

import com.techstars.jobstechstars.dto.JobPageableDto;
import com.techstars.jobstechstars.dto.JobResponseDto;
import com.techstars.jobstechstars.dto.JobWrapperDto;
import com.techstars.jobstechstars.enums.SeniorityStatus;
import com.techstars.jobstechstars.exceptions.JobNotFoundException;
import com.techstars.jobstechstars.mappers.JobMapper;
import com.techstars.jobstechstars.models.Job;
import com.techstars.jobstechstars.repositories.JobRepository;
import com.techstars.jobstechstars.services.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final WebClient webClient;
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    private static final int HITS_PER_PAGE = 20;

    @Async
    @Transactional
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    @Override
    public void synchronizeJobs() {
        Set<JobResponseDto> jobResponseDtos = downloadAllJobs();

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

    @Override
    public Set<JobResponseDto> downloadAllJobs() {
        int pageQuantity = getPageQuantity();
        if (pageQuantity == 0) {
            log.warn("No Jobs found");
            throw new JobNotFoundException("Can't find any Jobs by request");
        }

        Set<JobResponseDto> allJobs = Flux.range(0, pageQuantity + 1)
                .flatMap(page -> webClient.post()
                        .bodyValue(new JobPageableDto(HITS_PER_PAGE, page))
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .retrieve()
                        .bodyToMono(JobWrapperDto.class)
                        .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))), 5
                )
                .flatMapIterable(wrapper -> wrapper.jobResult().jobResponseDto())
                .collect(Collectors.toSet())
                .block();
        return allJobs;
    }

    @Override
    public Page<JobResponseDto> getPageJobs(Pageable pageable) {
        return jobRepository.findAll(pageable)
                .map(jobMapper::toDto);
    }

    private int getPageQuantity() {
        JobPageableDto jobPageableDto = new JobPageableDto(HITS_PER_PAGE, 0);
        JobWrapperDto jobWrapperDto = webClient.post()
                .bodyValue(jobPageableDto)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToFlux(JobWrapperDto.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                .blockFirst();

        return jobWrapperDto.jobResult().count() != null ? jobWrapperDto.jobResult().count()  / HITS_PER_PAGE : 0;
    }
}
