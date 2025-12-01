package com.daella.employeemanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Employee Management API", version = "1.0", description = "RESTful API for managing employees with JWT authentication"),
        security = @SecurityRequirement(name = "bearerAuth"),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Local Server"
                ),
                @Server(
                        url = "https://securehr-backend-capstone-project-production.up.railway.app",
                        description = "Production Server"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
    // No implementation needed.  Annotations drive the configuration.
}