package com.busaned_thinking.mogu.chat.service;

import org.springframework.http.ResponseEntity;

import com.busaned_thinking.mogu.chat.controller.dto.request.ChatRequest;
import com.busaned_thinking.mogu.chat.controller.dto.request.UpdateChatRequest;
import com.busaned_thinking.mogu.chat.controller.dto.response.ChatResponse;

public interface ChatService {
	ResponseEntity<ChatResponse> createChat(ChatRequest chatRequest);

	ResponseEntity<Void> deleteChat(Long id);

	ResponseEntity<ChatResponse> findChat(Long id);

	ResponseEntity<ChatResponse> updateChat(Long id, UpdateChatRequest updateChatRequest);
}
