package com.bunsaned3thinking.mogu.notice.service;

import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.notice.controller.dto.request.NoticeRequest;
import com.bunsaned3thinking.mogu.notice.controller.dto.request.UpdateNoticeRequest;
import com.bunsaned3thinking.mogu.notice.controller.dto.response.NoticeResponse;

public interface NoticeService {
	ResponseEntity<NoticeResponse> createNotice(NoticeRequest noticeRequest);

	ResponseEntity<Void> deleteNotice(Long id);

	ResponseEntity<NoticeResponse> findNotice(Long id);

	ResponseEntity<NoticeResponse> updateNotice(Long id, UpdateNoticeRequest updateNoticeRequest);

}
