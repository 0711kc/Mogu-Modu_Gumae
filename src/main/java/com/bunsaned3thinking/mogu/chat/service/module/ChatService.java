package com.bunsaned3thinking.mogu.chat.service.module;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatResponse;

public interface ChatService {
	ResponseEntity<ChatResponse> createChat(Long postId);

	ResponseEntity<Void> deleteChat(Long id);

	ResponseEntity<ChatResponse> findChat(Long id);

	ResponseEntity<List<ChatResponse>> findChatByUser(String userId);

	ResponseEntity<ChatResponse> updateChat(Long id);

	ResponseEntity<Void> exitChatUser(String userId, Long chatId);
}
