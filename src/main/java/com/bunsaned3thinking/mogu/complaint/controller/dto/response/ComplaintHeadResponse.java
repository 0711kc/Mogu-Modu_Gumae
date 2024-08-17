package com.bunsaned3thinking.mogu.complaint.controller.dto.response;

import com.bunsaned3thinking.mogu.complaint.entity.Complaint;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ComplaintHeadResponse {
	private final Long id;
	private final String title;
	private final String type;
	private final String state;

	public static ComplaintHeadResponse from(Complaint complaint) {
		return ComplaintHeadResponse.builder()
			.id(complaint.getId())
			.title(complaint.getTitle())
			.type(complaint.getType().getResponse())
			.state(complaint.getState().getResponse())
			.build();
	}
}
