package com.bunsaned3thinking.mogu.user.controller.dto.request;

import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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
	@Size(max = 12, message = "닉네임의 최대 크기는 12글자입니다.")
	private String nickname;

	@DecimalMin(value = "-180.0", message = "-180.0 ~ 180.0 사이의 숫자를 입력해주세요")
	@DecimalMax(value = "180.0", message = "-180.0 ~ 180.0 사이의 숫자를 입력해주세요")
	private Double longitude;

	@DecimalMin(value = "-90.0", message = "-90.0 ~ 90.0 사이의 숫자를 입력해주세요")
	@DecimalMax(value = "90.0", message = "-90.0 ~ 90.0 사이의 숫자를 입력해주세요")
	private Double latitude;

	@Size(min = 500, max = 3000, message = "활동 범위는 500~3000 사이의 숫자로 입력해주세요")
	private Short distanceMeters;

	public static UpdateUserRequest from(User user) {
		return UpdateUserRequest.builder()
			.nickname(user.getNickname())
			.longitude(user.getLocation().getX())
			.latitude(user.getLocation().getY())
			.distanceMeters(user.getDistanceMeters())
			.build();
	}
}
