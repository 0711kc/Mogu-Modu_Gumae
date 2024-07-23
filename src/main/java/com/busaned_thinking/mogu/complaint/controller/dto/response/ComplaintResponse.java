package com.busaned_thinking.mogu.complaint.controller.dto.response;

import com.busaned_thinking.mogu.post.controller.dto.response.PostResponse;
import com.busaned_thinking.mogu.post.entity.Post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComplaintResponse {
	private final String title;

	public static ComplaintResponse from(Post post) {
		return ComplaintResponse.builder()
			.title(post.getTitle())
			.build();
	}
}
