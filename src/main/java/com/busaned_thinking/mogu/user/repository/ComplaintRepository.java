package com.busaned_thinking.mogu.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busaned_thinking.mogu.user.service.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
