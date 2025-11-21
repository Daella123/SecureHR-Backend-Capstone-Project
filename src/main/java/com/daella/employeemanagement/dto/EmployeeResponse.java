package com.daella.employeemanagement.dto;


import com.daella.employeemanagement.model.Department;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@Schema(description = "Response payload for employee information")
public class EmployeeResponse {

    @Schema(description = "Identifier of the employee", example = "1")
    private Long id;

    @Schema(description = "Full name of the employee", example = "SINE Daella")
    private String name;

    @Schema(description = "Job position or title", example = "Software Engineer")
    private String position;

    @Schema(description = "Department where the employee works", example = "ENGINEERING")
    private Department department;

    @Schema(description = "Date the employee was hired (ISO format)", example = "2025-01-15")
    private LocalDate hireDate;


}