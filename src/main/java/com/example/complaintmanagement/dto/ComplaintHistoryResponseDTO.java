package com.example.complaintmanagement.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintHistoryResponseDTO {

    private String status;

    private String remarks;

    private String updatedBy;

    private LocalDateTime updatedAt;
}