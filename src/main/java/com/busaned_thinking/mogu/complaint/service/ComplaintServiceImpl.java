package com.busaned_thinking.mogu.complaint.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.busaned_thinking.mogu.complaint.controller.dto.request.ComplaintRequest;
import com.busaned_thinking.mogu.complaint.controller.dto.request.UpdateComplaintRequest;
import com.busaned_thinking.mogu.complaint.controller.dto.response.ComplaintResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {
	@Override
	public ResponseEntity<ComplaintResponse> createComplaint(ComplaintRequest complaintRequest) {
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteComplaint(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<ComplaintResponse> findComplaint(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<ComplaintResponse> updateComplaint(Long id, UpdateComplaintRequest updateComplaintRequest) {
		return null;
	}
}
