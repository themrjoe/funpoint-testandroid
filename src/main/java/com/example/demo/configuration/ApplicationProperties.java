package com.example.demo.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "com.example.funpoint")
public class ApplicationProperties {

    @NotBlank
    private String jwtTokenSecret;

    @NotNull
    private Long jwtTokenExpired;
}
