package com.busaned_thinking.mogu.complaint.entity;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ComplaintState {

	PROCESSING((short)0, "처리중"),
	COMPLETED((short)1, "처리완료");

	public static final ComplaintState DEFAULT = PROCESSING;

	private final short index;
	private final String response;

	public static ComplaintState findByIndex(short index) {
		return Arrays.stream(ComplaintState.values())
			.filter(complaintState -> complaintState.getIndex() == index)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
