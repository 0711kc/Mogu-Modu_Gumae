package com.bunsaned3thinking.mogu.chat.controller.dto.response;

import java.time.LocalDateTime;

import com.bunsaned3thinking.mogu.chat.entity.ChatMessage;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ChatMessageResponse {
	private final long id;
	private final String content;
	private final int unreadCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime time;
	private final long userUid;
	private final String userId;

	public static ChatMessageResponse from(final ChatMessage chatMessage) {
		return ChatMessageResponse.builder()
			.id(chatMessage.getId())
			.content(chatMessage.getContent())
			.unreadCount(chatMessage.getUnreadUsers().size())
			.time(chatMessage.getTime())
			.userUid(chatMessage.getUser().getUid())
			.userId(chatMessage.getUser().getUserId())
			.build();
	}
}
