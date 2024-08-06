package com.bunsaned3thinking.mogu.complaint.controller.dto.request;

import com.bunsaned3thinking.mogu.complaint.entity.Complaint;
import com.bunsaned3thinking.mogu.complaint.entity.ComplaintType;
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
public class ComplaintRequest {

	@NotBlank(message = "제목을 입력해주세요.")
	@Size(max = 50)
	private String title;

	@NotBlank(message = "내용을 입력해주세요.")
	@Size(max = 500)
	private String content;

	@NotNull(message = "타입을 선택해주세요.")
	private ComplaintType type;

	public Complaint toEntity(User user) {
		return Complaint.builder()
			.title(title)
			.content(content)
			.type(type.getIndex())
			.user(user)
			.build();
	}
}
