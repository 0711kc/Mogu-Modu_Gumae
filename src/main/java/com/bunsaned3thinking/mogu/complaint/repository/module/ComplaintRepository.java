package com.bunsaned3thinking.mogu.complaint.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.complaint.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
