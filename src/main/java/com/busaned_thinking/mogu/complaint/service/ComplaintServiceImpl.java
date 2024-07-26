package com.busaned_thinking.mogu.complaint.service;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.busaned_thinking.mogu.complaint.controller.dto.request.ComplaintRequest;
import com.busaned_thinking.mogu.complaint.controller.dto.request.UpdateComplaintRequest;
import com.busaned_thinking.mogu.complaint.controller.dto.response.ComplaintResponse;
import com.busaned_thinking.mogu.complaint.entity.Complaint;
import com.busaned_thinking.mogu.complaint.entity.ComplaintImage;
import com.busaned_thinking.mogu.complaint.entity.ComplaintState;
import com.busaned_thinking.mogu.complaint.repository.ComplaintImageRepository;
import com.busaned_thinking.mogu.complaint.repository.ComplaintRepository;
import com.busaned_thinking.mogu.config.S3Config;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintServiceImpl implements ComplaintService {
	private final ComplaintRepository complaintRepository;
	private final ComplaintImageRepository complaintImageRepository;

	@Override
	public ResponseEntity<ComplaintResponse> createComplaint(ComplaintRequest complaintRequest,
		List<String> complaintImageLinks) {
		List<ComplaintImage> complaintImages = new ArrayList<>();
		complaintImages.add(ComplaintImage.from(S3Config.basicPostImage()));
		if (complaintImageLinks != null) {
			complaintImages = complaintImageLinks.stream()
				.map(ComplaintImage::from)
				.toList();
		}
		Complaint complaint = complaintRequest.toEntity(complaintImages);
		complaintImages.stream().forEach(complaintImageRepository::save);
		Complaint savedComplaint = complaintRepository.save(complaint);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ComplaintResponse.from(savedComplaint));
	}

	@Override
	public ResponseEntity<ComplaintResponse> findComplaint(Long id) {
		Complaint complaint = complaintRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 문의를 찾을 수 없습니다."));
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ComplaintResponse.from(complaint));
	}

	@Override
	public ResponseEntity<ComplaintResponse> updateComplaint(Long id, UpdateComplaintRequest updateComplaintRequest) {
		Complaint complaint = complaintRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 문의를 찾을 수 없습니다."));
		UpdateComplaintRequest originComplaint = UpdateComplaintRequest.from(complaint);
		copyNonNullProperties(updateComplaintRequest, originComplaint);
		update(complaint, originComplaint);
		Complaint updatedComplaint = complaintRepository.save(complaint);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ComplaintResponse.from(updatedComplaint));
	}

	private void update(Complaint complaint, UpdateComplaintRequest updateComplaintRequest) {
		String answer = updateComplaintRequest.getAnswer();
		ComplaintState complaintState = ComplaintState.COMPLETED;
		complaint.update(answer, complaintState.getIndex());
	}

	private static void copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = Arrays.stream(pds)
			.map(FeatureDescriptor::getName)
			.filter(name -> src.getPropertyValue(name) == null)
			.collect(Collectors.toSet());
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}
