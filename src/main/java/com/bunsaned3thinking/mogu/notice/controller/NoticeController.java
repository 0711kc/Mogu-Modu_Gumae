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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "공지사항 API", description = "공지사항 관련 API 입니다.")
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
	private final NoticeService noticeService;

	@PostMapping
	@Operation(summary = "공지사항 생성", description = "공지사항 정보를 생성합니다.")
	public ResponseEntity<NoticeResponse> createNotice(@RequestBody @Valid final NoticeRequest noticeRequest) {
		return noticeService.createNotice(noticeRequest);
	}

	@GetMapping("/{id}")
	@Operation(summary = "공지사항 조회", description = "공지사항 정보를 조회합니다.")
	public ResponseEntity<NoticeResponse> findNotice(
		@Parameter(name = "Notice ID", description = "공지사항의 id", in = ParameterIn.PATH)
		@PathVariable final Long id) {
		return noticeService.findNotice(id);
	}

	@GetMapping("/all")
	@Operation(summary = "모든 공지사항 조회", description = "모든 공지사항 정보를 조회합니다.")
	public ResponseEntity<List<NoticeHeadResponse>> findAllNotice() {
		return noticeService.findAllNotice();
	}

	@PatchMapping("/{id}")
	@Operation(summary = "공지사항 수정", description = "공지사항 정보를 수정합니다.")
	public ResponseEntity<NoticeResponse> updateNotice(
		@Parameter(name = "Notice ID", description = "공지사항의 id", in = ParameterIn.PATH)
		@PathVariable final Long id,
		@RequestBody @Valid final UpdateNoticeRequest updateNoticeRequest) {
		return noticeService.updateNotice(id, updateNoticeRequest);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "공지사항 삭제", description = "공지사항 정보를 삭제합니다.")
	public ResponseEntity<Void> deleteNotice(
		@Parameter(name = "Notice ID", description = "공지사항의 id", in = ParameterIn.PATH)
		@PathVariable final Long id) {
		return noticeService.deleteNotice(id);
	}

}
