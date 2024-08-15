package com.bunsaned3thinking.mogu.chat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatMessageResponse;
import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatResponse;
import com.bunsaned3thinking.mogu.chat.service.module.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
	private final ChatService chatService;

	@GetMapping("/{id}")
	public ResponseEntity<ChatResponse> findChat(@PathVariable final Long id) {
		return chatService.findChat(id);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<ChatResponse>> findChatByUser(@PathVariable final String userId) {
		return chatService.findChatByUser(userId);
	}

	@DeleteMapping("/{chatId}/user/{userId}")
	public ResponseEntity<Void> exitChat(@PathVariable final String userId, @PathVariable final Long chatId) {
		return chatService.exitChatUser(userId, chatId);
	}

	@GetMapping("/{chatId}/user/{userId}")
	public ResponseEntity<List<ChatMessageResponse>> findChatMessages(@PathVariable final Long chatId,
		@PathVariable final String userId) {
		return chatService.findAllChatMessages(chatId, userId);
	}
}
