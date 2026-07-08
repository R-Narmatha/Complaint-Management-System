package com.example.complaintmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponseDTO {

    private long totalComplaints;

    private long submitted;

    private long assigned;

    private long inProgress;

    private long resolved;

    private long rejected;

    private long totalCitizens;
}