package com.techstars.jobstechstars.mappers;

import com.techstars.jobstechstars.dto.JobResponseDto;
import com.techstars.jobstechstars.models.Job;
import com.techstars.jobstechstars.enums.SeniorityStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface JobMapper {
    @Mapping(target = "companyName", source = "organization.companyName")
    @Mapping(target = "industryTags", source = "organization.industryTags")
    @Mapping(target = "seniorityStatus", expression = "java(toSeniority(jobResponseDto.seniority()))")
    @Mapping(target = "id", ignore = true)
    Job toModel(JobResponseDto jobResponseDto);

    Set<Job> toModels(Set<JobResponseDto> jobResponseDtos);

    @Mapping(target = "organization.industryTags", source = "job.industryTags")
    @Mapping(target = "organization.companyName", source = "job.companyName")
    @Mapping(target = "seniority", expression = "java(getSeniorityName(job.getSeniorityStatus()))")
    JobResponseDto toDto(Job job);

    default SeniorityStatus toSeniority(String seniorityString) {
        if (seniorityString == null || seniorityString.isBlank())
            return null;

        return SeniorityStatus.fromString(seniorityString);
    }

    default String getSeniorityName(SeniorityStatus seniorityStatus) {
        if (seniorityStatus == null)
            return null;

        return seniorityStatus.name();
    }

}
