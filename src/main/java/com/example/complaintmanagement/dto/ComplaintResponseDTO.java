package com.example.complaintmanagement.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintResponseDTO {

    private Long id;

    private String title;

    private String description;

    private String category;

    private String location;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String citizenName;

    private String assignedAdmin;
}