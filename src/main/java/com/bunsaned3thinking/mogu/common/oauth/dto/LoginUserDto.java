package com.bunsaned3thinking.mogu.common.oauth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class LoginUserDto {
	private final String role;
	private final String name;
	private final String username;

	public static LoginUserDto of(final String role, final String name, final String username) {
		return LoginUserDto.builder()
			.role(role)
			.name(name)
			.username(username)
			.build();
	}
}
