package com.bunsaned3thinking.mogu.notice.controller.dto.response;

import com.bunsaned3thinking.mogu.notice.entity.Notice;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoticeResponse {
	private final String title;
	private final String content;

	public static NoticeResponse from(Notice notice) {
		return NoticeResponse.builder().title(notice.getTitle()).content(notice.getContent()).build();
	}

}
