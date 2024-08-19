package com.bunsaned3thinking.mogu.complaint.controller;

import java.security.Principal;
import java.util.ArrayList;
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

import com.bunsaned3thinking.mogu.complaint.controller.dto.request.ComplaintRequest;
import com.bunsaned3thinking.mogu.complaint.controller.dto.request.UpdateComplaintRequest;
import com.bunsaned3thinking.mogu.complaint.controller.dto.response.ComplaintHeadResponse;
import com.bunsaned3thinking.mogu.complaint.controller.dto.response.ComplaintResponse;
import com.bunsaned3thinking.mogu.complaint.service.component.ComplaintComponentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/complaint")
@RequiredArgsConstructor
public class ComplaintController {
	private final ComplaintComponentService complaintComponentService;

	@PostMapping
	public ResponseEntity<ComplaintResponse> createComplaint(
		Principal principal,
		@RequestPart(name = "request") @Valid final ComplaintRequest complaintRequest,
		@RequestPart(value = "multipartFileList", required = false) Optional<List<MultipartFile>> multipartFileList) {
		return complaintComponentService.createComplaint(complaintRequest, multipartFileList.orElseGet(ArrayList::new),
			principal.getName());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ComplaintResponse> findComplaint(@PathVariable final Long id, Principal principal) {
		return complaintComponentService.findComplaint(id, principal.getName());
	}

	@GetMapping("/all")
	public ResponseEntity<List<ComplaintHeadResponse>> findAllComplaint() {
		return complaintComponentService.findAllComplaint();
	}

	@GetMapping("/all/my")
	public ResponseEntity<List<ComplaintHeadResponse>> findAllComplaintByUser(Principal principal) {
		return complaintComponentService.findAllComplaintByUser(principal.getName());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ComplaintResponse> updateComplaint(@PathVariable final Long id,
		Principal principal,
		@RequestBody @Valid UpdateComplaintRequest updateComplaintRequest) {
		return complaintComponentService.updateComplaint(id, updateComplaintRequest, principal.getName());
	}

}
