package com.bunsaned3thinking.mogu.user.controller.dto.response;

import com.bunsaned3thinking.mogu.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
	private final Long uid;
	private final String userId;
	private final String nickname;
	private final String profileImage;
	private final Integer level;
	private final String manner;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.uid(user.getUid())
			.userId(user.getUserId())
			.nickname(user.getNickname())
			.profileImage(user.getProfileImage())
			.level(user.getLevel())
			.manner(user.getManner().getDescription())
			.build();
	}
}
