package com.bunsaned3thinking.mogu.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.bunsaned3thinking.mogu.chat.controller.dto.request.ChatMessageRequest;
import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatMessageResponse;
import com.bunsaned3thinking.mogu.chat.service.module.ChatService;
import com.bunsaned3thinking.mogu.user.service.module.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class WebSocketChatHandler extends TextWebSocketHandler {
	private final ChatService chatService;
	private final UserService userService;
	private final ObjectMapper mapper;

	// 현재 연결된 세션들
	private final Set<WebSocketSession> sessions = new HashSet<>();

	// chatRoomId: {session1, session2}
	private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

	// 소켓 연결 확인
	@Override
	public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
		sessions.add(session);
	}

	// 소켓 통신 시 메세지의 전송을 다루는 부분
	@Override
	protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();

		ChatMessageRequest chatMessageRequest = mapper.readValue(payload, ChatMessageRequest.class);
		Long chatRoomId = chatMessageRequest.getChatRoomId();
		String senderId = chatMessageRequest.getSenderId();
		if (!userService.checkUser(senderId)) {
			session.sendMessage(new TextMessage(mapper.writeValueAsString("[Error] 사용자를 찾을 수 없습니다.")));
			closeSession(session);
			return;
		}
		if (!chatService.checkChatUser(chatRoomId, senderId)) {
			session.sendMessage(new TextMessage(mapper.writeValueAsString("[Error] 사용자가 들어간 채팅방이 아닙니다.")));
			closeSession(session);
			return;
		}

		// 메모리 상에 채팅방에 대한 세션 없으면 만들어줌
		if (!chatRoomSessionMap.containsKey(chatRoomId)) {
			chatRoomSessionMap.put(chatRoomId, new HashSet<>());
		}

		Set<WebSocketSession> chatRoomSessions = chatRoomSessionMap.get(chatRoomId);

		if (chatMessageRequest.getMessageType().equals(ChatMessageRequest.MessageType.ENTER)) {
			chatRoomSessions.add(session);
			session.getAttributes().put("senderId", senderId);
			session.getAttributes().put("chatRoomId", chatRoomId);
			chatService.readMessage(chatRoomId, senderId);
			return;
		}

		if (!chatRoomSessions.contains(session)) {
			session.sendMessage(new TextMessage(mapper.writeValueAsString("[Error] 먼저 입장을 시도해주세요.")));
			return;
		}

		List<String> readUserIds = chatRoomSessions.stream().map(WebSocketSession::getAttributes)
			.map(map -> map.get("senderId"))
			.map(Object::toString)
			.toList();
		ChatMessageResponse chatMessageResponse = chatService.createChatMessage(chatRoomId, senderId,
			chatMessageRequest.getMessage(), readUserIds);
		sendMessageToChatRoom(chatMessageResponse, chatRoomSessions);
	}

	// 소켓 종료 확인
	@Override
	public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
		sessions.remove(session);
		Object chatRoomId = session.getAttributes().get("chatRoomId");
		if (chatRoomId != null) {
			chatRoomSessionMap.get((long)chatRoomId).remove(session);
		}
	}

	// ====== 채팅 관련 메소드 ======

	private void closeSession(WebSocketSession session) throws IOException {
		sessions.remove(session);
		session.close();
	}

	private void sendMessageToChatRoom(ChatMessageResponse chatMessageResponse, Set<WebSocketSession> chatRoomSession) {
		chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageResponse));
	}

	public <T> void sendMessage(WebSocketSession session, T message) {
		try {
			session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}
}
