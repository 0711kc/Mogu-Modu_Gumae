package com.busaned_thinking.mogu.complaint.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.busaned_thinking.mogu.complaint.controller.dto.request.ComplaintRequest;
import com.busaned_thinking.mogu.complaint.controller.dto.request.UpdateComplaintRequest;
import com.busaned_thinking.mogu.complaint.controller.dto.response.ComplaintResponse;

public interface ComplaintService {

	ResponseEntity<ComplaintResponse> createComplaint(ComplaintRequest complaintRequest,
		List<String> complaintImageLinks);

	ResponseEntity<ComplaintResponse> findComplaint(Long id);

	ResponseEntity<ComplaintResponse> updateComplaint(Long id, UpdateComplaintRequest updateComplaintRequest);

}
