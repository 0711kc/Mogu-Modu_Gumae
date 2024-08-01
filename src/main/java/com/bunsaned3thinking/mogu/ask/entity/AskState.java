package com.bunsaned3thinking.mogu.ask.entity;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AskState {
	WAITING((short)0, "대기중", "신청이 들어왔습니다.", null),
	REJECTED((short)1, "거절", "신청이 거절되었습니다.", false),
	APPROVED((short)2, "승인", "신청이 승인되었습니다.", true);

	public static final AskState DEFAULT = AskState.WAITING;

	private final short index;
	private final String response;
	private final String alarmMessage;
	private final Boolean state;

	public static AskState findByIndex(short index) {
		return Arrays.stream(AskState.values())
			.filter(askState -> askState.getIndex() == index)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}

	public static AskState findByState(boolean state) {
		if (state) {
			return AskState.APPROVED;
		}
		return AskState.REJECTED;
	}
}
