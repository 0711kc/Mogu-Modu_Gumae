package com.busaned_thinking.mogu.user.controller.dto.request;

import com.busaned_thinking.mogu.user.entity.User;

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
public class UpdateUserRequest {
	@Size(max = 12)
	private String name;

	public static UpdateUserRequest from(User user) {
		return UpdateUserRequest.builder()
			.name(user.getName())
			.build();
	}
}
