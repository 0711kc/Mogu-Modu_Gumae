package com.busaned_thinking.mogu.post.controller.dto.request;

import com.busaned_thinking.mogu.post.entity.Post;
import com.busaned_thinking.mogu.user.entity.User;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostRequest {
	@NotBlank(message = "제목을 입력해주세요.")
	@Size(max = 50)
	private String title;


	public Post toEntity() {
		return Post.builder()
			.title(title)
			.build();
	}
}
