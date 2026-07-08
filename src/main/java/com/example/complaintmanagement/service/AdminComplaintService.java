package com.example.complaintmanagement.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.complaintmanagement.dto.ComplaintResponseDTO;
import com.example.complaintmanagement.dto.DashboardResponseDTO;
import com.example.complaintmanagement.dto.UpdateComplaintStatusDTO;

import com.example.complaintmanagement.entity.Complaint;
import com.example.complaintmanagement.entity.ComplaintStatus;
import com.example.complaintmanagement.entity.ComplaintStatusHistory;
import com.example.complaintmanagement.entity.Role;
import com.example.complaintmanagement.entity.User;

import com.example.complaintmanagement.exception.ComplaintNotFoundException;
import com.example.complaintmanagement.repository.ComplaintRepository;
import com.example.complaintmanagement.repository.ComplaintStatusHistoryRepository;
import com.example.complaintmanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AdminComplaintService {


    private final ComplaintRepository complaintRepository;

    private final ComplaintStatusHistoryRepository historyRepository;

    private final UserRepository userRepository;

    private final UserService userService;



    /*
     * View all complaints
     */
    public List<ComplaintResponseDTO> getAllComplaints(){

        return complaintRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }



    /*
     * Assign complaint to admin
     */
    public ComplaintResponseDTO assignComplaint(Long complaintId){


        Complaint complaint =
                complaintRepository.findById(complaintId)
                .orElseThrow(() ->
                    new ComplaintNotFoundException(
                            "Complaint not found"));



        User admin =
                userRepository.findAll()
                .stream()
                .filter(user ->
                        user.getRole()
                        == Role.ADMIN)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Admin not found"));



        complaint.setAssignedAdmin(admin);

        complaint.setStatus(
                ComplaintStatus.ASSIGNED);

        complaint.setUpdatedAt(
                LocalDateTime.now());


        complaintRepository.save(complaint);



        saveHistory(
                complaint,
                ComplaintStatus.ASSIGNED,
                "Complaint assigned",
                admin
        );


        return mapToDTO(complaint);

    }




    /*
     * Update complaint status
     */
    public ComplaintResponseDTO updateStatus(
            Long complaintId,
            UpdateComplaintStatusDTO request){


        Complaint complaint =
                complaintRepository.findById(complaintId)
                .orElseThrow(() ->
                        new ComplaintNotFoundException(
                                "Complaint not found"));



        ComplaintStatus status;


        try{

            status =
            ComplaintStatus.valueOf(
                    request.getStatus()
                    .toUpperCase());

        }
        catch(Exception e){

            throw new RuntimeException(
                    "Invalid complaint status");
        }



        User admin =
                userService.getCurrentUser();



        complaint.setStatus(status);

        complaint.setUpdatedAt(
                LocalDateTime.now());


        complaintRepository.save(complaint);



        saveHistory(
                complaint,
                status,
                request.getRemarks(),
                admin
        );



        return mapToDTO(complaint);

    }





    /*
     * Dashboard Statistics
     */
    public DashboardResponseDTO getDashboard(){


        return DashboardResponseDTO.builder()

                .totalComplaints(
                    complaintRepository.count())

                .submitted(
                    complaintRepository
                    .countByStatus(
                    ComplaintStatus.SUBMITTED))

                .assigned(
                    complaintRepository
                    .countByStatus(
                    ComplaintStatus.ASSIGNED))

                .inProgress(
                    complaintRepository
                    .countByStatus(
                    ComplaintStatus.IN_PROGRESS))

                .resolved(
                    complaintRepository
                    .countByStatus(
                    ComplaintStatus.RESOLVED))

                .rejected(
                    complaintRepository
                    .countByStatus(
                    ComplaintStatus.REJECTED))

                .totalCitizens(
                    userRepository
                    .countByRole(
                    Role.CITIZEN))

                .build();

    }





    private void saveHistory(
            Complaint complaint,
            ComplaintStatus status,
            String remarks,
            User user){


        ComplaintStatusHistory history =
                ComplaintStatusHistory.builder()

                .complaint(complaint)

                .status(status)

                .remarks(
                    remarks == null
                    ? "Status Updated"
                    : remarks)

                .updatedBy(user)

                .updatedAt(
                    LocalDateTime.now())

                .build();



        historyRepository.save(history);

    }





    private ComplaintResponseDTO mapToDTO(
            Complaint complaint){


        return ComplaintResponseDTO.builder()

                .id(complaint.getId())

                .title(complaint.getTitle())

                .description(
                    complaint.getDescription())

                .category(
                    complaint.getCategory())

                .location(
                    complaint.getLocation())

                .status(
                    complaint.getStatus()
                    .name())

                .createdAt(
                    complaint.getCreatedAt())

                .updatedAt(
                    complaint.getUpdatedAt())

                .citizenName(
                    complaint.getCitizen()
                    .getFullName())

                .assignedAdmin(
                    complaint.getAssignedAdmin()
                    == null
                    ? null
                    : complaint
                    .getAssignedAdmin()
                    .getFullName())

                .build();

    }

}