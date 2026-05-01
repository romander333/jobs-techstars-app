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
    Job toModel(JobResponseDto jobResponseDto);

    Set<Job> toModels(Set<JobResponseDto> jobResponseDtos);

    default SeniorityStatus toSeniority(String seniorityString) {
        if (seniorityString == null || seniorityString.isBlank())
            return null;

        return SeniorityStatus.fromString(seniorityString);
    }
}
