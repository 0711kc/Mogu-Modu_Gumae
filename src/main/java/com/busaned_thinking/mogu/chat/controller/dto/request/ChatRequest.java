package com.busaned_thinking.mogu.chat.controller.dto.request;

import com.busaned_thinking.mogu.chat.entity.Chat;
import com.busaned_thinking.mogu.post.entity.Post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChatRequest {
	@NotBlank(message = "내용을 입력해주세요.")
	@Size(max = 1000)
	private String lastMsg;


	public Chat toEntity() {
		return Chat.builder()
			.lastMsg(lastMsg)
			.build();
	}
}
