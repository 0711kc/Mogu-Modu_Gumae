package com.busaned_thinking.mogu.complaint.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.busaned_thinking.mogu.complaint.entity.Complaint;
import com.busaned_thinking.mogu.complaint.entity.ComplaintImage;

import jakarta.persistence.EntityNotFoundException;
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
	public Complaint findByIdComplaint(Long id) {
		return complaintRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 문의를 찾을 수 없습니다."));
	}
}
