package com.busaned_thinking.mogu.complaint.service.component;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.busaned_thinking.mogu.complaint.controller.dto.request.ComplaintRequest;
import com.busaned_thinking.mogu.complaint.controller.dto.request.UpdateComplaintRequest;
import com.busaned_thinking.mogu.complaint.controller.dto.response.ComplaintResponse;

public interface ComplaintComponentService {

	ResponseEntity<ComplaintResponse> createComplaint(ComplaintRequest complaintRequest,
		List<MultipartFile> multipartFileList);

	ResponseEntity<ComplaintResponse> findComplaint(Long id);

	ResponseEntity<ComplaintResponse> updateComlaint(Long id, UpdateComplaintRequest updateComplaintRequest);
}
