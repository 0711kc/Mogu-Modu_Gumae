package com.busaned_thinking.mogu.user.controller.dto.response;

import com.busaned_thinking.mogu.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
	private final String userId;
	private final String name;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.userId(user.getUserId())
			.name(user.getName())
			.build();
	}
}
