package com.bunsaned3thinking.mogu.complaint.controller.dto.response;

import java.util.List;

import com.bunsaned3thinking.mogu.complaint.entity.Complaint;
import com.bunsaned3thinking.mogu.complaint.entity.ComplaintImage;
import com.bunsaned3thinking.mogu.complaint.entity.ComplaintState;
import com.bunsaned3thinking.mogu.complaint.entity.ComplaintType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComplaintResponse {
	private final String title;
	private final String content;
	private final String answer;
	private final String type;
	private final String state;
	private final List<String> imageLinks;

	public static ComplaintResponse from(Complaint complaint) {
		return ComplaintResponse.builder()
			.title(complaint.getTitle())
			.content(complaint.getContent())
			.answer(complaint.getAnswer())
			.type(ComplaintType.findByIndex(complaint.getType()).getResponse())
			.state(ComplaintState.findByIndex(complaint.getState()).getResponse())
			.imageLinks(complaint.getComplaintImages().stream()
				.map(ComplaintImage::getImage)
				.toList())
			.build();
	}
}
