package com.bunsaned3thinking.mogu.chat.service.component;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatMessageResponse;
import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatResponse;

public interface ChatComponentService {
	ResponseEntity<ChatResponse> findChat(Long id);

	ResponseEntity<List<ChatResponse>> findChatByUser(String userId);

	ResponseEntity<Void> exitChatUser(String userId, Long chatId);

	ResponseEntity<List<ChatMessageResponse>> findAllChatMessages(Long chatId, String userId);
}
