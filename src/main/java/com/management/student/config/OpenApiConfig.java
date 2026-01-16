package com.management.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI studentManagementOpenApi(){
        return new OpenAPI()
        .info(new io.swagger.v3.oas.models.info.Info()
        .title("Student Management API")
        .description("API documentation for Student Management System")
        .version("1.0.0"));
    }
}
