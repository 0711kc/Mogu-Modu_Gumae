package com.bunsaned3thinking.mogu.report.dto.response;

import com.bunsaned3thinking.mogu.report.entity.Report;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResponse {
	private final String content;
	private final String type;
	private final Long postId;
	private final String userId;

	public static ReportResponse from(Report report) {
		return ReportResponse.builder()
			.content(report.getContent())
			.postId(report.getPost().getId())
			.userId(report.getUser().getUserId())
			.type(report.getType().getResponse())
			.build();
	}
}
