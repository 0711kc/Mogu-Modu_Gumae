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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "문의 API", description = "문의 관련 API 입니다.")
@RequestMapping("/complaint")
@RequiredArgsConstructor
public class ComplaintController {
	private final ComplaintComponentService complaintComponentService;

	@PostMapping
	@Operation(summary = "문의 생성", description = "문의 정보를 생성합니다.")
	public ResponseEntity<ComplaintResponse> createComplaint(
		Principal principal,
		@Parameter(description = "생성할 문의 정보")
		@RequestPart(name = "request") @Valid final ComplaintRequest complaintRequest,
		@Parameter(description = "문의 이미지 리스트")
		@RequestPart(value = "multipartFileList", required = false) Optional<List<MultipartFile>> multipartFileList) {
		return complaintComponentService.createComplaint(complaintRequest, multipartFileList.orElseGet(ArrayList::new),
			principal.getName());
	}

	@GetMapping("/{id}")
	@Operation(summary = "문의 조회", description = "문의 정보를 조회합니다.")
	public ResponseEntity<ComplaintResponse> findComplaint(
		@Parameter(name = "Complaint ID", description = "문의의 id", in = ParameterIn.PATH)
		@PathVariable final Long id, Principal principal) {
		return complaintComponentService.findComplaint(id, principal.getName());
	}

	@GetMapping("/all")
	@Operation(summary = "모든 문의 조회", description = "모든 문의 정보를 조회합니다.")
	public ResponseEntity<List<ComplaintHeadResponse>> findAllComplaint() {
		return complaintComponentService.findAllComplaint();
	}

	@GetMapping("/all/my")
	@Operation(summary = "나의 모든 문의 조회", description = "본인이 작성한 모든 문의 정보를 조회합니다.")
	public ResponseEntity<List<ComplaintHeadResponse>> findAllComplaintByUser(Principal principal) {
		return complaintComponentService.findAllComplaintByUser(principal.getName());
	}

	@PatchMapping("/{id}")
	@Operation(summary = "문의 수정", description = "문의 정보를 수정합니다.")
	public ResponseEntity<ComplaintResponse> updateComplaint(
		@Parameter(name = "Complaint ID", description = "문의의 id", in = ParameterIn.PATH)
		@PathVariable final Long id,
		Principal principal,
		@Parameter(description = "수정할 문의 정보")
		@RequestBody @Valid UpdateComplaintRequest updateComplaintRequest) {
		return complaintComponentService.updateComplaint(id, updateComplaintRequest, principal.getName());
	}

}
