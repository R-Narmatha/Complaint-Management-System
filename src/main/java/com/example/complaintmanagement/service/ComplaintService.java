package com.example.complaintmanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.complaintmanagement.dto.ComplaintHistoryResponseDTO;
import com.example.complaintmanagement.dto.ComplaintRequestDTO;
import com.example.complaintmanagement.dto.ComplaintResponseDTO;
import com.example.complaintmanagement.entity.Complaint;
import com.example.complaintmanagement.entity.ComplaintStatus;
import com.example.complaintmanagement.entity.ComplaintStatusHistory;
import com.example.complaintmanagement.entity.User;
import com.example.complaintmanagement.exception.ComplaintNotFoundException;
import com.example.complaintmanagement.repository.ComplaintRepository;
import com.example.complaintmanagement.repository.ComplaintStatusHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    private final ComplaintStatusHistoryRepository historyRepository;

    private final UserService userService;

    /**
     * Submit Complaint
     */
    public ComplaintResponseDTO submitComplaint(
            ComplaintRequestDTO request) {

        User citizen = userService.getCurrentUser();

        Complaint complaint = Complaint.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .location(request.getLocation())
                .status(ComplaintStatus.SUBMITTED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .citizen(citizen)
                .build();

        complaint = complaintRepository.save(complaint);

        ComplaintStatusHistory history =
                ComplaintStatusHistory.builder()
                        .complaint(complaint)
                        .status(ComplaintStatus.SUBMITTED)
                        .remarks("Complaint Submitted")
                        .updatedBy(citizen)
                        .updatedAt(LocalDateTime.now())
                        .build();

        historyRepository.save(history);

        return mapToDTO(complaint);
    }

    /**
     * Get Logged-in Citizen Complaints
     */
    public List<ComplaintResponseDTO> getMyComplaints() {

        User citizen = userService.getCurrentUser();

        return complaintRepository
                .findByCitizenOrderByCreatedAtDesc(citizen)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get Complaint By Id
     */
    public ComplaintResponseDTO getComplaintById(Long id) {

        User citizen = userService.getCurrentUser();

        Complaint complaint = complaintRepository
                .findById(id)
                .orElseThrow(() ->
                        new ComplaintNotFoundException(
                                "Complaint not found"));

        if (!complaint.getCitizen().getId()
                .equals(citizen.getId())) {

            throw new RuntimeException(
                    "You cannot access this complaint.");
        }

        return mapToDTO(complaint);
    }
    public List<ComplaintHistoryResponseDTO> getComplaintHistory(Long complaintId) {

    User citizen = userService.getCurrentUser();

    Complaint complaint = complaintRepository
            .findById(complaintId)
            .orElseThrow(() ->
                    new ComplaintNotFoundException("Complaint not found"));

    if (!complaint.getCitizen().getId().equals(citizen.getId())) {
        throw new RuntimeException("You cannot access this complaint.");
    }

    return historyRepository
            .findByComplaintOrderByUpdatedAtAsc(complaint)
            .stream()
            .map(history -> ComplaintHistoryResponseDTO.builder()
                    .status(history.getStatus().name())
                    .remarks(history.getRemarks())
                    .updatedBy(history.getUpdatedBy().getFullName())
                    .updatedAt(history.getUpdatedAt())
                    .build())
            .toList();
}

private ComplaintResponseDTO mapToDTO(Complaint complaint) {

    return ComplaintResponseDTO.builder()
            .id(complaint.getId())
            .title(complaint.getTitle())
            .description(complaint.getDescription())
            .category(complaint.getCategory())
            .location(complaint.getLocation())
            .status(complaint.getStatus().name())
            .createdAt(complaint.getCreatedAt())
            .updatedAt(complaint.getUpdatedAt())
            .citizenName(complaint.getCitizen().getFullName())
            .assignedAdmin(
                    complaint.getAssignedAdmin() == null
                            ? null
                            : complaint.getAssignedAdmin().getFullName())
            .build();
}
    
}