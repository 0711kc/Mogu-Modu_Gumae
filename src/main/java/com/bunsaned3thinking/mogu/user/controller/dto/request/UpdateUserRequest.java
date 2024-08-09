package com.bunsaned3thinking.mogu.user.controller.dto.request;

import com.bunsaned3thinking.mogu.user.entity.User;

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
	private String nickname;

	private Double longitude;

	private Double latitude;

	public static UpdateUserRequest from(User user) {
		return UpdateUserRequest.builder()
			.nickname(user.getNickname())
			.longitude(user.getLocation().getX())
			.latitude(user.getLocation().getY())
			.build();
	}
}
