package com.busaned_thinking.mogu.ask.entity;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AskState {
	WAITING((short)0, "대기중", null),
	REJECTED((short)1, "거절", false),
	APPROVED((short)2, "승인", true);

	public static final AskState DEFAULT = AskState.WAITING;

	private final short index;
	private final String response;
	private final Boolean state;

	public static AskState findByIndex(short index) {
		return Arrays.stream(AskState.values())
			.filter(askState -> askState.getIndex() == index)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}

	public static AskState findByState(Boolean state) {
		return Arrays.stream(AskState.values())
			.filter(askState -> askState.getState().equals(state))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
