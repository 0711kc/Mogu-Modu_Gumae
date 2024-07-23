package com.busaned_thinking.mogu.complaint.controller.dto.request;

import com.busaned_thinking.mogu.complaint.entity.Complaint;
import com.busaned_thinking.mogu.post.entity.Post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ComplaintRequest {

	@NotBlank(message = "제목을 입력해주세요.")
	@Size(max = 50)
	private String title;


	public Complaint toEntity() {
		return Complaint.builder()
			.title(title)
			.build();
	}
}
