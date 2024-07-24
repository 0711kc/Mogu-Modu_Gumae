package com.busaned_thinking.mogu.notice.service;

import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.busaned_thinking.mogu.notice.controller.dto.request.NoticeRequest;
import com.busaned_thinking.mogu.notice.controller.dto.request.UpdateNoticeRequest;
import com.busaned_thinking.mogu.notice.controller.dto.response.NoticeResponse;
import com.busaned_thinking.mogu.notice.entity.Notice;
import com.busaned_thinking.mogu.notice.repository.NoticeRepository;

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
		Notice notice = noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("[Error] 공지를 찾을 수 없습니다."));
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(NoticeResponse.from(notice));
	}

	@Override
	public ResponseEntity<Void> deleteNotice(Long id) {
		Notice notice = noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("[Error] 공지를 찾을 수 없습니다."));
		noticeRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<NoticeResponse> updateNotice(Long id, UpdateNoticeRequest updateNoticeRequest) {
		Notice notice = noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("[Error] 공지를 찾을 수 없습니다."));
		UpdateNoticeRequest originNotice = UpdateNoticeRequest.from(notice);
		copyNonNullProperties(updateNoticeRequest, originNotice);
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

	private static void copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = Arrays.stream(pds)
			.map(FeatureDescriptor::getName)
			.filter(name -> src.getPropertyValue(name) == null)
			.collect(Collectors.toSet());
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

}
