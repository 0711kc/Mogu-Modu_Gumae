package com.bunsaned3thinking.mogu.post.controller.dto.response;

import com.bunsaned3thinking.mogu.post.entity.Post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostWithDetailResponse {
	private final PostResponse post;
	private final PostDetailResponse detail;

	public static PostWithDetailResponse from(final Post post) {
		return PostWithDetailResponse.builder()
			.post(PostResponse.from(post))
			.detail(PostDetailResponse.from(post.getPostDetail()))
			.build();
	}
}
