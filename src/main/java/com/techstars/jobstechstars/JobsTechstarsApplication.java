package com.techstars.jobstechstars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class JobsTechstarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobsTechstarsApplication.class, args);
    }

}
