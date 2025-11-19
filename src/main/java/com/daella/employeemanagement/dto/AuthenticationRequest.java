package com.daella.employeemanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Schema(description = "Request payload for authenticating a user")
public class AuthenticationRequest {

    @Schema(description = "Username or email used to log in", example = "john_doe")
    @NotBlank(message = "Username or email is required")
    private String usernameOrEmail;

    @Schema(description = "User's password", example = "P@ssw0rd!")
    @NotBlank(message = "Password is required")
    private String password;
}