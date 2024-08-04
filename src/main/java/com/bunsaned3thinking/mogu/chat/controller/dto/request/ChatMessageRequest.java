package com.bunsaned3thinking.mogu.chat.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatMessageRequest {
	public enum MessageType {
		ENTER, TALK
	}

	private MessageType messageType; // 메시지 타입
	private Long chatRoomId; // 방 번호
	private String senderId; // 채팅을 보낸 사람
	private String message; // 메시지
}
