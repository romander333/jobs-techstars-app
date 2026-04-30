package com.techstars.jobstechstars.models;

import com.techstars.jobstechstars.enums.SeniorityStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    private List<String> locations;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "job_link", columnDefinition = "TEXT")
    private String jobLink;
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "industry_tags", columnDefinition = "text[]")
    private List<String> industryTags;
    @Enumerated(EnumType.STRING)
    private SeniorityStatus seniorityStatus;
}
