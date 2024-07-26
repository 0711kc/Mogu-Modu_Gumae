package com.busaned_thinking.mogu.user.entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	user((short)0, "사용자", "user", "ROLE_USER"),
	admin((short)1, "관리자", "admin", "ROLE_ADMIN");

	private final short index;
	private final String response;
	private final String request;
	private final String jwt;

	@JsonValue
	public String getRequest() {
		return request;
	}

	@JsonCreator
	public static Role from(String request) {
		return Arrays.stream(Role.values())
			.filter(memberRole -> memberRole.getRequest().equals(request))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);

		/*
			같은 의미
			for(Role role : Role.values()) {
				if (role.getRequest().equals(request)) {
					return role;
				}
			}
			throw new IllegalArgumentException();
		*/
	}

	public static Role findByIndex(short index) {
		return Arrays.stream(Role.values())
			.filter(memberRole -> memberRole.getIndex() == index)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}

	public static Role findByJwt(String jwtRole) {
		return Arrays.stream(Role.values())
			.filter(memberRole -> memberRole.getJwt().equals(jwtRole))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
