package com.bunsaned3thinking.mogu.complaint.service.module;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.common.config.S3Config;
import com.bunsaned3thinking.mogu.common.util.UpdateUtil;
import com.bunsaned3thinking.mogu.complaint.controller.dto.request.ComplaintRequest;
import com.bunsaned3thinking.mogu.complaint.controller.dto.request.UpdateComplaintRequest;
import com.bunsaned3thinking.mogu.complaint.controller.dto.response.ComplaintHeadResponse;
import com.bunsaned3thinking.mogu.complaint.controller.dto.response.ComplaintResponse;
import com.bunsaned3thinking.mogu.complaint.entity.Complaint;
import com.bunsaned3thinking.mogu.complaint.entity.ComplaintImage;
import com.bunsaned3thinking.mogu.complaint.entity.ComplaintState;
import com.bunsaned3thinking.mogu.complaint.repository.component.ComplaintComponentRepository;
import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintServiceImpl implements ComplaintService {
	private final ComplaintComponentRepository complaintComponentRepository;

	@Override
	public ResponseEntity<ComplaintResponse> createComplaint(ComplaintRequest complaintRequest,
		List<String> complaintImageLinks, String userId) {
		User user = complaintComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		Complaint complaint = complaintRequest.toEntity(user);
		Complaint savedComplaint = complaintComponentRepository.saveComplaint(complaint);
		List<ComplaintImage> complaintImages = createComplaintImages(complaintImageLinks, savedComplaint);
		List<ComplaintImage> savedComplaintImages = complaintComponentRepository.saveAllComplaintImages(
			complaintImages);
		savedComplaint.updateComplaintImages(savedComplaintImages);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ComplaintResponse.from(savedComplaint));
	}

	@Override
	public ResponseEntity<ComplaintResponse> findComplaint(Long id) {
		Complaint complaint = complaintComponentRepository.findComplaintById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 문의를 찾을 수 없습니다."));
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ComplaintResponse.from(complaint));
	}

	@Override
	public ResponseEntity<List<ComplaintHeadResponse>> findAllComplaint() {
		List<Complaint> complaints = complaintComponentRepository.findAllComplaint();
		List<ComplaintHeadResponse> responses = complaints.stream()
			.map(ComplaintHeadResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responses);
	}

	@Override
	public ResponseEntity<List<ComplaintHeadResponse>> findAllComplaintByUser(String userId) {
		List<Complaint> complaints = complaintComponentRepository.findAllComplaintByUserId(userId);
		List<ComplaintHeadResponse> responses = complaints.stream()
			.map(ComplaintHeadResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responses);
	}

	@Override
	public ResponseEntity<ComplaintResponse> updateComplaint(Long id, UpdateComplaintRequest updateComplaintRequest,
		String userId) {
		Complaint complaint = complaintComponentRepository.findComplaintById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 문의를 찾을 수 없습니다."));
		User admin = complaintComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		UpdateComplaintRequest originComplaint = UpdateComplaintRequest.from(complaint);
		UpdateUtil.copyNonNullProperties(updateComplaintRequest, originComplaint);
		update(complaint, originComplaint, admin);
		Complaint updatedComplaint = complaintComponentRepository.saveComplaint(complaint);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ComplaintResponse.from(updatedComplaint));
	}

	private void update(Complaint complaint, UpdateComplaintRequest updateComplaintRequest, User admin) {
		String answer = updateComplaintRequest.getAnswer();
		ComplaintState complaintState = ComplaintState.COMPLETED;
		complaint.update(answer, complaintState, admin);
	}

	private List<ComplaintImage> createComplaintImages(List<String> complaintImageLinks, Complaint complaint) {
		if (complaintImageLinks.isEmpty()) {
			ArrayList<ComplaintImage> complaintImages = new ArrayList<>();
			complaintImages.add(ComplaintImage.of(complaint, S3Config.PostImage));
			return complaintImages;
		}
		return complaintImageLinks.stream()
			.map(complaintImageLink -> ComplaintImage.of(complaint, complaintImageLink))
			.toList();
	}
}
