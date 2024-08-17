package com.bunsaned3thinking.mogu.complaint.service.component;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bunsaned3thinking.mogu.complaint.controller.dto.request.ComplaintRequest;
import com.bunsaned3thinking.mogu.complaint.controller.dto.request.UpdateComplaintRequest;
import com.bunsaned3thinking.mogu.complaint.controller.dto.response.ComplaintResponse;

public interface ComplaintComponentService {

	ResponseEntity<ComplaintResponse> createComplaint(ComplaintRequest complaintRequest,
		List<MultipartFile> multipartFileList, String userId);

	ResponseEntity<ComplaintResponse> findComplaint(Long id);

	ResponseEntity<ComplaintResponse> updateComplaint(Long id, UpdateComplaintRequest updateComplaintRequest,
		String userId);
}
