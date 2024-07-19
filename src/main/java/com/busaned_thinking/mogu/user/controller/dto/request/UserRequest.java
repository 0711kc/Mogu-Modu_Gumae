package com.busaned_thinking.mogu.user.controller.dto.request;

import com.busaned_thinking.mogu.user.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class UserRequest {
	@NotBlank(message = "아이디를 입력해주세요.")
	@Size(max = 12)
	private String userId;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Size(max = 80)
	private String password;

	@NotBlank(message = "이름을 입력해주세요.")
	@Size(max = 12)
	private String name;

	public User toEntity() {
		return User.builder()
			.userId(userId)
			.password(password)
			.name(name)
			.build();
	}
}
