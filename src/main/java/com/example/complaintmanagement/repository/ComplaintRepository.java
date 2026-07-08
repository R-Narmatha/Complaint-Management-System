package com.example.complaintmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.complaintmanagement.entity.Complaint;
import com.example.complaintmanagement.entity.ComplaintStatus;
import com.example.complaintmanagement.entity.User;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByCitizen(User citizen);

    List<Complaint> findByAssignedAdmin(User admin);

    List<Complaint> findByStatus(ComplaintStatus status);

    long countByStatus(ComplaintStatus status);

    long countByCitizen(User citizen);

    List<Complaint> findByCitizenOrderByCreatedAtDesc(User citizen);

    List<Complaint> findAllByOrderByCreatedAtDesc();
}