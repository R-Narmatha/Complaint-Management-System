package com.example.complaintmanagement.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.complaintmanagement.dto.ComplaintResponseDTO;
import com.example.complaintmanagement.dto.DashboardResponseDTO;
import com.example.complaintmanagement.dto.UpdateComplaintStatusDTO;
import com.example.complaintmanagement.service.AdminComplaintService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminComplaintController {


    private final AdminComplaintService adminComplaintService;



    /*
     * View all complaints
     */
    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintResponseDTO>> getAllComplaints(){

        return ResponseEntity.ok(
                adminComplaintService.getAllComplaints()
        );
    }





    /*
     * Assign complaint to admin
     */
    @PutMapping("/complaints/{id}/assign")
    public ResponseEntity<ComplaintResponseDTO> assignComplaint(
            @PathVariable Long id){


        return ResponseEntity.ok(
                adminComplaintService.assignComplaint(id)
        );

    }





    /*
     * Update complaint status
     */
    @PutMapping("/complaints/{id}/status")
    public ResponseEntity<ComplaintResponseDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateComplaintStatusDTO request){


        return ResponseEntity.ok(
                adminComplaintService.updateStatus(
                        id,
                        request)
        );

    }





    /*
     * Admin Dashboard
     */
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponseDTO> dashboard(){


        return ResponseEntity.ok(
                adminComplaintService.getDashboard()
        );

    }

}