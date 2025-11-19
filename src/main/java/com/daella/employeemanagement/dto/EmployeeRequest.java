package com.daella.employeemanagement.dto;


import com.daella.employeemanagement.model.Department;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;


@Data
@Schema(description = "Request payload for creating or updating an employee")
public class EmployeeRequest {

    @Schema(description = "Full name of the employee", example = "Alice Johnson")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "Job position or title", example = "Software Engineer")
    @NotBlank(message = "Position is required")
    private String position;

    @Schema(description = "Department where the employee works", example = "ENGINEERING")
    @NotNull(message = "Department is required")
    private Department department;

    @Schema(description = "Date the employee was hired (ISO format)", example = "2025-01-15")
    @NotNull(message = "Hire date is required")
    @PastOrPresent(message = "Hire date cannot be in the future")
    private LocalDate hireDate;
}