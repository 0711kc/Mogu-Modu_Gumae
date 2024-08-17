package com.bunsaned3thinking.mogu.complaint.service.component;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bunsaned3thinking.mogu.complaint.controller.dto.request.ComplaintRequest;
import com.bunsaned3thinking.mogu.complaint.controller.dto.request.UpdateComplaintRequest;
import com.bunsaned3thinking.mogu.complaint.controller.dto.response.ComplaintHeadResponse;
import com.bunsaned3thinking.mogu.complaint.controller.dto.response.ComplaintResponse;
import com.bunsaned3thinking.mogu.complaint.service.module.ComplaintService;
import com.bunsaned3thinking.mogu.image.service.ImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintComponentServiceImpl implements ComplaintComponentService {
	private final ComplaintService complaintService;
	private final ImageService imageService;

	@Override
	public ResponseEntity<ComplaintResponse> createComplaint(ComplaintRequest complaintRequest,
		List<MultipartFile> multipartFileList, String userId) {
		List<String> imageLinks = imageService.uploadAll(multipartFileList);
		return complaintService.createComplaint(complaintRequest, imageLinks, userId);
	}

	@Override
	public ResponseEntity<ComplaintResponse> findComplaint(Long id) {
		return complaintService.findComplaint(id);
	}

	@Override
	public ResponseEntity<List<ComplaintHeadResponse>> findAllComplaint() {
		return complaintService.findAllComplaint();
	}

	@Override
	public ResponseEntity<ComplaintResponse> updateComplaint(Long id, UpdateComplaintRequest updateComplaintRequest,
		String userId) {
		return complaintService.updateComplaint(id, updateComplaintRequest, userId);
	}
}
