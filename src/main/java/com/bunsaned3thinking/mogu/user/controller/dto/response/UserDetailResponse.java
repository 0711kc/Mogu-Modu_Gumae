package com.bunsaned3thinking.mogu.user.controller.dto.response;

import java.time.LocalDateTime;

import com.bunsaned3thinking.mogu.common.util.S3Util;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDetailResponse {
	private final String userId;
	private final String name;
	private String nickname;
	private String phone;
	private String role;
	private Boolean isBlock;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	@Builder.Default
	private LocalDateTime blockDate = null;
	private String profileImage;
	private Integer level;
	private String manner;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime registerDate;
	private Double longitude;
	private Double latitude;
	private Short distanceMeters;

	public static UserDetailResponse from(User user) {
		return UserDetailResponse.builder()
			.userId(user.getUserId())
			.name(user.getName())
			.nickname(user.getNickname())
			.phone(user.getPhone())
			.role(user.getRole().getResponse())
			.isBlock(user.getIsBlock())
			.blockDate(user.getBlockDate())
			.profileImage(S3Util.toS3ImageUrl(user.getProfileImage()))
			.level(user.getLevel())
			.manner(user.getManner().getDescription())
			.registerDate(user.getRegisterDate())
			.longitude(user.getLocation().getX())
			.latitude(user.getLocation().getY())
			.distanceMeters(user.getDistanceMeters())
			.build();
	}
}
