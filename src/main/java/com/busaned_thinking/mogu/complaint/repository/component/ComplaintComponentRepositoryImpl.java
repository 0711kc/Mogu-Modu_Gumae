package com.busaned_thinking.mogu.complaint.repository.component;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.busaned_thinking.mogu.complaint.entity.Complaint;
import com.busaned_thinking.mogu.complaint.entity.ComplaintImage;
import com.busaned_thinking.mogu.complaint.repository.module.ComplaintImageRepository;
import com.busaned_thinking.mogu.complaint.repository.module.ComplaintRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ComplaintComponentRepositoryImpl implements ComplaintComponentRepository {
	private final ComplaintRepository complaintRepository;
	private final ComplaintImageRepository complaintImageRepository;

	@Override
	public void saveAllComplaintImages(List<ComplaintImage> complaintImages) {
		complaintImageRepository.saveAll(complaintImages);
	}

	@Override
	public Complaint saveComplaint(Complaint complaint) {
		return complaintRepository.save(complaint);
	}

	@Override
	public Optional<Complaint> findByIdComplaint(Long id) {
		return complaintRepository.findById(id);
	}
}
