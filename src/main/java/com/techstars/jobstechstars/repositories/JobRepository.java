package com.techstars.jobstechstars.repositories;

import com.techstars.jobstechstars.models.Job;
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
}
