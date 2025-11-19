package com.daella.employeemanagement.dto;


import com.daella.employeemanagement.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
@Schema(description = "Representation of a user returned in responses")
public class UserResponse {

    @Schema(description = "Database identifier of the user", example = "1")
    private Long id;

    @Schema(description = "Username chosen by the user", example = "john_doe")
    private String username;

    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Role assigned to the user", example = "USER")
    private Role role;
}