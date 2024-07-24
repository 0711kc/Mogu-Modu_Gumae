package com.busaned_thinking.mogu.notice.controller.dto.request;

import com.busaned_thinking.mogu.notice.entity.Notice;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UpdateNoticeRequest {
	@Size(max = 50)
	private String title;
	@Size(max = 500)
	private String content;

	public static UpdateNoticeRequest from(Notice notice) {
		return UpdateNoticeRequest.builder().title(notice.getTitle()).content(notice.getContent()).build();
	}
}
