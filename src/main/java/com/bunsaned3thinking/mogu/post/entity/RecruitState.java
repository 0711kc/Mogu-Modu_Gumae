package com.bunsaned3thinking.mogu.post.entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitState {
	RECRUITING((short)0, "모집중", "recruiting"),
	CLOSING((short)1, "마감 완료", "closing");

	public static final RecruitState DEFAULT = RECRUITING;

	private final short index;
	private final String response;
	private final String request;

	@JsonValue
	public String getRequest() {
		return request;
	}

	@JsonCreator
	public static RecruitState from(String request) {
		return Arrays.stream(RecruitState.values())
			.filter(recruitState -> recruitState.getRequest().equals(request))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
