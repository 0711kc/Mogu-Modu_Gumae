package com.busaned_thinking.mogu.complaint.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.busaned_thinking.mogu.complaint.controller.dto.request.ComplaintRequest;
import com.busaned_thinking.mogu.complaint.controller.dto.request.UpdateComplaintRequest;
import com.busaned_thinking.mogu.complaint.controller.dto.response.ComplaintResponse;
import com.busaned_thinking.mogu.complaint.service.component.ComplaintComponentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/complaint")
@RequiredArgsConstructor
public class ComplaintController {
	private final ComplaintComponentService complaintComponentService;

	@PostMapping
	public ResponseEntity<ComplaintResponse> createComplaint(
		@RequestPart(name = "request") @Valid final ComplaintRequest complaintRequest,
		@RequestPart(value = "multipartFileList", required = false) Optional<List<MultipartFile>> multipartFileList) {
		return complaintComponentService.createComplaint(complaintRequest, multipartFileList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ComplaintResponse> findComplaint(@PathVariable final Long id) {
		return complaintComponentService.findComplaint(id);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ComplaintResponse> updateComplaint(@PathVariable final Long id,
		@RequestBody @Valid UpdateComplaintRequest updateComplaintRequest) {
		return complaintComponentService.updateComlaint(id, updateComplaintRequest);
	}

}
