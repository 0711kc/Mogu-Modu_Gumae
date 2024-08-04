package com.bunsaned3thinking.mogu.post.controller.dto.response;

import com.bunsaned3thinking.mogu.post.entity.Report;
import com.bunsaned3thinking.mogu.post.entity.ReportType;

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
			.type(ReportType.findByIndex(report.getType()).getResponse())
			.build();
	}
}
