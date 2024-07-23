package com.busaned_thinking.mogu.chat.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class ChatUserId implements Serializable {
	private Long chat;
	private Long user;

	public static ChatUserId of(Long chatId, Long userId) {
		return ChatUserId.builder()
			.chat(chatId)
			.user(userId)
			.build();
	}
}
