package com.busaned_thinking.mogu.complaint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busaned_thinking.mogu.complaint.controller.dto.request.ComplaintRequest;
import com.busaned_thinking.mogu.complaint.controller.dto.request.UpdateComplaintRequest;
import com.busaned_thinking.mogu.complaint.controller.dto.response.ComplaintResponse;
import com.busaned_thinking.mogu.complaint.service.ComplaintService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/complaint")
@RequiredArgsConstructor
public class ComplaintController {
	private final ComplaintService complaintService;

	@PostMapping("/new")
	public ResponseEntity<ComplaintResponse> createComplaint(
		@RequestBody @Valid final ComplaintRequest complaintRequest) {
		return complaintService.createComplaint(complaintRequest);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ComplaintResponse> findComplaint(@PathVariable final Long id) {
		return complaintService.findComplaint(id);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ComplaintResponse> updateComplaint(@PathVariable final Long id,
		@RequestBody @Valid UpdateComplaintRequest updateComplaintRequest) {
		return complaintService.updateComplaint(id, updateComplaintRequest);
	}

	// @PostMapping("/image/new")
	// public ResponseEntity<ComplaintResponse> createComplaintImage(
	// 	@RequestBody @Valid final ComplaintRequest complaintRequest) {
	// 	return complaintService.createComplaint(complaintRequest);
	// }

}
