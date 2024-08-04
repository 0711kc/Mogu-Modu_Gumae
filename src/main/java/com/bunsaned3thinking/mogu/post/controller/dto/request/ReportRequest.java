package com.bunsaned3thinking.mogu.post.controller.dto.request;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.Report;
import com.bunsaned3thinking.mogu.post.entity.ReportType;
import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReportRequest {
	@NotBlank(message = "내용을 입력해주세요.")
	@Size(max = 500)
	private String content;

	@NotNull(message = "타입을 선택해주세요.")
	private ReportType type;

	public Report toEntity(Post post, User user) {
		return Report.builder()
			.content(content)
			.type(type.getIndex())
			.post(post)
			.user(user)
			.build();
	}
}
