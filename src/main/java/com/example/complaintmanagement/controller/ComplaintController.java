package com.example.complaintmanagement.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.complaintmanagement.dto.ComplaintHistoryResponseDTO;
import com.example.complaintmanagement.dto.ComplaintRequestDTO;
import com.example.complaintmanagement.dto.ComplaintResponseDTO;
import com.example.complaintmanagement.service.ComplaintService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/complaints")
@RequiredArgsConstructor
public class ComplaintController {


    private final ComplaintService complaintService;



    /*
     * Submit Complaint
     */
    @PostMapping
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<ComplaintResponseDTO> submitComplaint(
            @Valid @RequestBody ComplaintRequestDTO request){


        return ResponseEntity.ok(
                complaintService.submitComplaint(request)
        );

    }





    /*
     * View My Complaints
     */
    @GetMapping
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<List<ComplaintResponseDTO>> getMyComplaints(){


        return ResponseEntity.ok(
                complaintService.getMyComplaints()
        );

    }





    /*
     * View Complaint By ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<ComplaintResponseDTO> getComplaintById(
            @PathVariable Long id){


        return ResponseEntity.ok(
                complaintService.getComplaintById(id)
        );

    }





    /*
     * View Complaint Timeline
     */
    @GetMapping("/{id}/history")
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<List<ComplaintHistoryResponseDTO>> 
            getComplaintHistory(
                    @PathVariable Long id){


        return ResponseEntity.ok(
                complaintService.getComplaintHistory(id)
        );

    }

}