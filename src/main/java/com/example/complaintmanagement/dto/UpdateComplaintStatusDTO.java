package com.example.complaintmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateComplaintStatusDTO {

    @NotBlank(message = "Status is required")
    private String status;

    private String remarks;
}