package com.busaned_thinking.mogu.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busaned_thinking.mogu.complaint.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
