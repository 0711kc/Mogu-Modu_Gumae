package com.bunsaned3thinking.mogu.complaint.repository.component;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.bunsaned3thinking.mogu.complaint.entity.Complaint;
import com.bunsaned3thinking.mogu.complaint.entity.ComplaintImage;
import com.bunsaned3thinking.mogu.complaint.repository.module.ComplaintImageRepository;
import com.bunsaned3thinking.mogu.complaint.repository.module.ComplaintRepository;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.bunsaned3thinking.mogu.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ComplaintComponentRepositoryImpl implements ComplaintComponentRepository {
	private final ComplaintRepository complaintRepository;
	private final ComplaintImageRepository complaintImageRepository;
	private final UserRepository userRepository;

	@Override
	public List<ComplaintImage> saveAllComplaintImages(List<ComplaintImage> complaintImages) {
		return complaintImageRepository.saveAll(complaintImages);
	}

	@Override
	public Complaint saveComplaint(Complaint complaint) {
		return complaintRepository.save(complaint);
	}

	@Override
	public Optional<Complaint> findComplaintById(Long id) {
		return complaintRepository.findById(id);
	}

	@Override
	public Optional<User> findUserByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public List<Complaint> findAllComplaint() {
		return complaintRepository.findAll();
	}

	@Override
	public List<Complaint> findAllComplaintByUserId(String userId) {
		return complaintRepository.findAllByUserId(userId);
	}
}
