package com.bunsaned3thinking.mogu.chat.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bunsaned3thinking.mogu.chat.controller.dto.request.ChatRequest;
import com.bunsaned3thinking.mogu.chat.controller.dto.request.UpdateChatRequest;
import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
	@Override
	public ResponseEntity<ChatResponse> createChat(ChatRequest chatRequest) {
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteChat(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<ChatResponse> findChat(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<ChatResponse> updateChat(Long id, UpdateChatRequest updateChatRequest) {
		return null;
	}
}
