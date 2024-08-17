package com.bunsaned3thinking.mogu.complaint.controller.dto.response;

import java.util.List;

import com.bunsaned3thinking.mogu.common.util.S3Util;
import com.bunsaned3thinking.mogu.complaint.entity.Complaint;
import com.bunsaned3thinking.mogu.complaint.entity.ComplaintImage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComplaintResponse {
	private final Long id;
	private final String title;
	private final String content;
	private final String adminName;
	private final String answer;
	private final String type;
	private final String state;
	private final List<String> imageLinks;

	public static ComplaintResponse from(Complaint complaint) {
		String adminName = null;
		if (complaint.getAdmin() != null) {
			adminName = complaint.getAdmin().getName();
		}
		return ComplaintResponse.builder()
			.id(complaint.getId())
			.title(complaint.getTitle())
			.content(complaint.getContent())
			.adminName(adminName)
			.answer(complaint.getAnswer())
			.type(complaint.getType().getResponse())
			.state(complaint.getState().getResponse())
			.imageLinks(complaint.getComplaintImages().stream()
				.map(ComplaintImage::getImage)
				.map(S3Util::toS3ImageUrl)
				.toList())
			.build();
	}
}
