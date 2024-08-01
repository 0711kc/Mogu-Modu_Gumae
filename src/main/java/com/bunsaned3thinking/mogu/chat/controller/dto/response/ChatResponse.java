package com.bunsaned3thinking.mogu.chat.controller.dto.response;

import com.bunsaned3thinking.mogu.chat.entity.Chat;

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
