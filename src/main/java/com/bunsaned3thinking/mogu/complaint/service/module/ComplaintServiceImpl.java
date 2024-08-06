package com.bunsaned3thinking.mogu.complaint.service.module;

import java.beans.FeatureDescriptor;
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
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.common.config.S3Config;
import com.bunsaned3thinking.mogu.complaint.controller.dto.request.ComplaintRequest;
import com.bunsaned3thinking.mogu.complaint.controller.dto.request.UpdateComplaintRequest;
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
	public ResponseEntity<ComplaintResponse> updateComplaint(Long id, UpdateComplaintRequest updateComplaintRequest) {
		Complaint complaint = complaintComponentRepository.findComplaintById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 문의를 찾을 수 없습니다."));
		UpdateComplaintRequest originComplaint = UpdateComplaintRequest.from(complaint);
		copyNonNullProperties(updateComplaintRequest, originComplaint);
		update(complaint, originComplaint);
		Complaint updatedComplaint = complaintComponentRepository.saveComplaint(complaint);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ComplaintResponse.from(updatedComplaint));
	}

	private void update(Complaint complaint, UpdateComplaintRequest updateComplaintRequest) {
		String answer = updateComplaintRequest.getAnswer();
		ComplaintState complaintState = ComplaintState.COMPLETED;
		complaint.update(answer, complaintState);
	}

	private List<ComplaintImage> createComplaintImages(List<String> complaintImageLinks, Complaint complaint) {
		if (complaintImageLinks.isEmpty()) {
			return List.of(ComplaintImage.of(complaint, S3Config.basicPostImage()));
		}
		return complaintImageLinks.stream()
			.map(complaintImageLink -> ComplaintImage.of(complaint, complaintImageLink))
			.toList();
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
