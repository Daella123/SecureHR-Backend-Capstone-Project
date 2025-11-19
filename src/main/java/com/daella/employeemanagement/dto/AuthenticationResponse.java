package com.daella.employeemanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
@Schema(description = "Response payload returned after successful authentication")
public class AuthenticationResponse {

    @Schema(description = "JWT access token to authorize subsequent requests", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Type of the token", example = "Bearer")
    @Builder.Default
    private String tokenType = "Bearer";

    @Schema(description = "Expiration timestamp in milliseconds since epoch", example = "1700000000000")
    private long expiresAt;

    @Schema(description = "Authenticated user details")
    private UserResponse user;
}