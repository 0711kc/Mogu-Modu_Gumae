package com.busaned_thinking.mogu.complaint.controller.dto.response;

import com.busaned_thinking.mogu.complaint.entity.Complaint;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComplaintResponse {
	private final String title;
	private final String content;
	private final Short type;

	public static ComplaintResponse from(Complaint complaint) {
		return ComplaintResponse.builder()
			.title(complaint.getTitle())
			.content(complaint.getContent())
			.type(complaint.getType())
			.build();
	}
}
