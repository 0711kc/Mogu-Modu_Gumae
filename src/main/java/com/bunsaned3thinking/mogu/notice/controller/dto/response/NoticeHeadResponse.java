package com.bunsaned3thinking.mogu.notice.controller.dto.response;

import com.bunsaned3thinking.mogu.notice.entity.Notice;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class NoticeHeadResponse {
	private final Long id;
	private final String title;

	public static NoticeHeadResponse from(Notice notice) {
		return NoticeHeadResponse.builder()
			.id(notice.getId())
			.title(notice.getTitle())
			.build();
	}
}
