package com.busaned_thinking.mogu.notice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busaned_thinking.mogu.notice.controller.dto.request.NoticeRequest;
import com.busaned_thinking.mogu.notice.controller.dto.request.UpdateNoticeRequest;
import com.busaned_thinking.mogu.notice.controller.dto.response.NoticeResponse;
import com.busaned_thinking.mogu.notice.service.NoticeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
	private final NoticeService noticeService;

	@PostMapping("/new")
	public ResponseEntity<NoticeResponse> createNotice(@RequestBody @Valid final NoticeRequest noticeRequest,
		@AuthenticationPrincipal User user) {
		return noticeService.createNotice(noticeRequest);
	}

	@GetMapping("/{id}")
	public ResponseEntity<NoticeResponse> findNotice(@PathVariable final Long id, @AuthenticationPrincipal User user) {
		return noticeService.findNotice(id);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<NoticeResponse> updateNotice(@PathVariable final Long id,
		@RequestBody @Valid final UpdateNoticeRequest updateNoticeRequest, @AuthenticationPrincipal User user) {
		return noticeService.updateNotice(id, updateNoticeRequest);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNotice(@PathVariable final Long id, @AuthenticationPrincipal User user) {
		return noticeService.deleteNotice(id);
	}

}
