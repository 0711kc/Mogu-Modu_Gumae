package com.busaned_thinking.mogu.chat.controller.dto.response;

import com.busaned_thinking.mogu.chat.entity.Chat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatResponse {
	private final String title;

	public static ChatResponse from(Chat chat) {
		return ChatResponse.builder()
			.title(chat.getLastMsg())
			.build();
	}
}
