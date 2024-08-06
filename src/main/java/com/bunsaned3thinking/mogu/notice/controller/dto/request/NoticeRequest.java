package com.bunsaned3thinking.mogu.notice.controller.dto.request;

import java.time.LocalDateTime;

import com.bunsaned3thinking.mogu.notice.entity.Notice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NoticeRequest {
	@NotBlank(message = "제목을 입력해주세요.")
	@Size(max = 50)
	private String title;

	@NotBlank(message = "내용을 입력해주세요.")
	@Size(max = 500)
	private String content;

	public Notice toEntity() {
		return Notice.builder()
			.title(title)
			.content(content)
			.date(LocalDateTime.now())
			.build();
	}

}
