package com.techstars.jobstechstars.repositories;

import com.techstars.jobstechstars.dto.JobResponseDto;
import com.techstars.jobstechstars.models.Job;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Modifying
    @Query("DELETE FROM Job j WHERE j.id NOT IN :jobIds")
    void deleteAllJobsByIds(@Param("jobIds") Set<Long> jobIds);


    @Query(value = """
            SELECT *
            FROM jobs j
            WHERE
            (CAST(:title AS text) IS NULL OR j.title ILIKE CONCAT('%', CAST(:title AS text), '%'))
            AND (CAST(:companyName AS text) IS NULL OR lower(j.company_name) LIKE lower(concat('%', CAST(:companyName AS text), '%')))
            AND (CAST(:seniority AS text) IS NULL OR lower(j.seniority_status) LIKE lower(concat('%', CAST(:seniority AS text), '%')))
            AND (CAST(:location AS text) IS NULL OR lower(array_to_string(j.locations, ',')) LIKE lower(concat('%', CAST(:location AS text), '%')))
            """, nativeQuery = true)
    Page<Job> findByFilters(
            @Param("title") String title,
            @Param("companyName") String companyName,
            @Param("seniority") String seniority,
            @Param("location") String location,
            Pageable pageable);
}
