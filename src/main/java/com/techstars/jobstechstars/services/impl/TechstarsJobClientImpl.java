package com.techstars.jobstechstars.services.impl;

import com.techstars.jobstechstars.dto.JobPageableDto;
import com.techstars.jobstechstars.dto.JobResponseDto;
import com.techstars.jobstechstars.dto.JobWrapperDto;
import com.techstars.jobstechstars.exceptions.JobNotFoundException;
import com.techstars.jobstechstars.services.TechstarsJobClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TechstarsJobClientImpl implements TechstarsJobClient {
    private final WebClient webClient;

    private static final int HITS_PER_PAGE = 20;

    /**
     * Downloads all jobs from the Techstars API by iterating through all available pages.
     *
     * @return a Set of {@link JobResponseDto} containing all retrieved jobs.
     * @throws JobNotFoundException if no jobs are found or the page count is zero.
     */
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

    /**
     * Performs an initial request to determine the total number of pages available.
     *
     * @return the total number of pages based on the total job count and {@code HITS_PER_PAGE}.
     */
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
