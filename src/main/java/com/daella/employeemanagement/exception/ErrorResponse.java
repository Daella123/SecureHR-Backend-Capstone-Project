package com.daella.employeemanagement.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;


@Data
@Builder
@Schema(description = "Error response body returned when an exception occurs")
public class ErrorResponse {

    @Schema(description = "Time when the error occurred in ISO‑8601 format", example = "2025-11-18T12:00:00Z")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant timestamp;

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "Reason phrase for the status code", example = "Bad Request")
    private String error;

    @Schema(description = "Detailed error message(s)")
    private List<String> message;

    @Schema(description = "Requested path that resulted in the error", example = "/api/employees/1")
    private String path;
}