package com.busaned_thinking.mogu.post.controller.dto.response;

import com.busaned_thinking.mogu.post.entity.Post;
import com.busaned_thinking.mogu.user.controller.dto.response.UserResponse;
import com.busaned_thinking.mogu.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {
	private final String title;

	public static PostResponse from(Post post) {
		return PostResponse.builder()
			.title(post.getTitle())
			.build();
	}
}
