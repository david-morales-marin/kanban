package com.example.kanban.config;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "BearerAuth",
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig{ }
