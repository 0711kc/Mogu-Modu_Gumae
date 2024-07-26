package com.busaned_thinking.mogu.complaint.entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ComplaintType {
	SERVICE((short)0, "서비스 문의", "service"),
	QUESTION((short)1, "질문", "question"),
	DISCONTENT((short)2, "불만사항", "discontent"),
	BUSINESS((short)3, "비지니스 문의", "business"),
	OTHER((short)4, "기타", "other");

	private final short index;
	private final String response;
	private final String request;

	@JsonValue
	public String getRequest() {
		return request;
	}

	@JsonCreator
	public static ComplaintType from(String request) {
		return Arrays.stream(ComplaintType.values())
			.filter(complaintType -> complaintType.getRequest().equals(request))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}

	public static ComplaintType findByIndex(short index) {
		return Arrays.stream(ComplaintType.values())
			.filter(complaintType -> complaintType.getIndex() == index)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
