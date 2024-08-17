package com.bunsaned3thinking.mogu.notice.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bunsaned3thinking.mogu.common.util.UpdateUtil;
import com.bunsaned3thinking.mogu.notice.controller.dto.request.NoticeRequest;
import com.bunsaned3thinking.mogu.notice.controller.dto.request.UpdateNoticeRequest;
import com.bunsaned3thinking.mogu.notice.controller.dto.response.NoticeHeadResponse;
import com.bunsaned3thinking.mogu.notice.controller.dto.response.NoticeResponse;
import com.bunsaned3thinking.mogu.notice.entity.Notice;
import com.bunsaned3thinking.mogu.notice.repository.NoticeRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
	private final NoticeRepository noticeRepository;

	@Override
	public ResponseEntity<NoticeResponse> createNotice(NoticeRequest noticeRequest) {
		Notice notice = noticeRequest.toEntity();
		Notice savedNotice = noticeRepository.save(notice);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(NoticeResponse.from(savedNotice));
	}

	@Override
	public ResponseEntity<NoticeResponse> findNotice(Long id) {
		Notice notice = noticeRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 공지를 찾을 수 없습니다."));
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(NoticeResponse.from(notice));
	}

	@Override
	public ResponseEntity<List<NoticeHeadResponse>> findAllNotice() {
		List<Notice> notices = noticeRepository.findAll();
		List<NoticeHeadResponse> responses = notices.stream()
			.map(NoticeHeadResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responses);
	}

	@Override
	public ResponseEntity<Void> deleteNotice(Long id) {
		boolean isExistNotice = noticeRepository.existsById(id);
		if (!isExistNotice) {
			throw new EntityNotFoundException("[Error] 공지를 찾을 수 없습니다.");
		}
		noticeRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<NoticeResponse> updateNotice(Long id, UpdateNoticeRequest updateNoticeRequest) {
		Notice notice = noticeRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 공지를 찾을 수 없습니다."));
		UpdateNoticeRequest originNotice = UpdateNoticeRequest.from(notice);
		UpdateUtil.copyNonNullProperties(updateNoticeRequest, originNotice);
		update(notice, originNotice);
		Notice updatedNotice = noticeRepository.save(notice);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(NoticeResponse.from(updatedNotice));
	}

	private void update(Notice notice, UpdateNoticeRequest updateNoticeRequest) {
		String title = updateNoticeRequest.getTitle();
		String content = updateNoticeRequest.getContent();

		notice.update(title, content);
	}
}
