package com.bunsaned3thinking.mogu.complaint.repository.module;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunsaned3thinking.mogu.complaint.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
	@Query("select c from Complaint c join fetch c.user u where u.userId = :userId")
	List<Complaint> findAllByUserId(String userId);
}
