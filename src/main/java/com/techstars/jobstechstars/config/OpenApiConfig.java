package com.techstars.jobstechstars.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    /**
     * Configures the OpenAPI documentation for the Techstars Service.
     *
     * @return OpenAPI instance with service metadata
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Job Techstars")
                                .description("API documentation for Job Techstars.")
                                .version("1.0")
                );
    }
}
