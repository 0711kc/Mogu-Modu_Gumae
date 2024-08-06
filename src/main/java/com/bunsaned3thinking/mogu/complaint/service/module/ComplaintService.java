package com.bunsaned3thinking.mogu.complaint.service.module;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.complaint.controller.dto.request.ComplaintRequest;
import com.bunsaned3thinking.mogu.complaint.controller.dto.request.UpdateComplaintRequest;
import com.bunsaned3thinking.mogu.complaint.controller.dto.response.ComplaintResponse;

public interface ComplaintService {

	ResponseEntity<ComplaintResponse> createComplaint(ComplaintRequest complaintRequest,
		List<String> complaintImageLinks, String userId);

	ResponseEntity<ComplaintResponse> findComplaint(Long id);

	ResponseEntity<ComplaintResponse> updateComplaint(Long id, UpdateComplaintRequest updateComplaintRequest);

}