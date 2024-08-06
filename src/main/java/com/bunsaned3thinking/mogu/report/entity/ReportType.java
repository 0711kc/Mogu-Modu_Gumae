package com.bunsaned3thinking.mogu.report.entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {

	SCAM((short)0, "사기, 허위 매물", "scam"),
	ONLINE_ABUSE((short)1, "언어 폭력", "onlineAbuse"),
	DISPLEASURE((short)2, "불쾌감을 주는 게시물", "displeasure"),
	OTHER((short)3, "기타", "other");

	private final short index;
	private final String response;
	private final String request;

	@JsonValue
	public String getRequest() {
		return request;
	}

	@JsonCreator
	public static ReportType from(String request) {
		return Arrays.stream(ReportType.values())
			.filter(reportType -> reportType.getRequest().equals(request))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}

	public static ReportType findByIndex(short index) {
		return Arrays.stream(ReportType.values())
			.filter(reportType -> reportType.getIndex() == index)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}

}
