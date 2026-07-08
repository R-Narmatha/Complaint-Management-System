package com.example.complaintmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.complaintmanagement.entity.Complaint;
import com.example.complaintmanagement.entity.ComplaintStatusHistory;

@Repository
public interface ComplaintStatusHistoryRepository extends JpaRepository<ComplaintStatusHistory, Long> {

    List<ComplaintStatusHistory> findByComplaintOrderByUpdatedAtAsc(Complaint complaint);
}