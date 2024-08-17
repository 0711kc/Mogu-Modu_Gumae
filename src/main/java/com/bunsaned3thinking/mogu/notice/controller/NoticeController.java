package com.bunsaned3thinking.mogu.notice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunsaned3thinking.mogu.notice.controller.dto.request.NoticeRequest;
import com.bunsaned3thinking.mogu.notice.controller.dto.request.UpdateNoticeRequest;
import com.bunsaned3thinking.mogu.notice.controller.dto.response.NoticeHeadResponse;
import com.bunsaned3thinking.mogu.notice.controller.dto.response.NoticeResponse;
import com.bunsaned3thinking.mogu.notice.service.NoticeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
	private final NoticeService noticeService;

	@PostMapping
	public ResponseEntity<NoticeResponse> createNotice(@RequestBody @Valid final NoticeRequest noticeRequest) {
		return noticeService.createNotice(noticeRequest);
	}

	@GetMapping("/{id}")
	public ResponseEntity<NoticeResponse> findNotice(@PathVariable final Long id) {
		return noticeService.findNotice(id);
	}

	@GetMapping("/all")
	public ResponseEntity<List<NoticeHeadResponse>> findAllNotice() {
		return noticeService.findAllNotice();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<NoticeResponse> updateNotice(@PathVariable final Long id,
		@RequestBody @Valid final UpdateNoticeRequest updateNoticeRequest) {
		return noticeService.updateNotice(id, updateNoticeRequest);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNotice(@PathVariable final Long id) {
		return noticeService.deleteNotice(id);
	}

}
