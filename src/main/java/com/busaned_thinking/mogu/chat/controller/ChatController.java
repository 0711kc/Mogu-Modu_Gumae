package com.busaned_thinking.mogu.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busaned_thinking.mogu.chat.controller.dto.request.ChatRequest;
import com.busaned_thinking.mogu.chat.controller.dto.request.UpdateChatRequest;
import com.busaned_thinking.mogu.chat.controller.dto.response.ChatResponse;
import com.busaned_thinking.mogu.chat.service.ChatService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
	private final ChatService chatService;

	@PostMapping("/new")
	public ResponseEntity<ChatResponse> createChat(@RequestBody @Valid final ChatRequest chatRequest) {
		return chatService.createChat(chatRequest);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ChatResponse> findChat(@PathVariable final Long id) {
		return chatService.findChat(id);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ChatResponse> updateChat(@PathVariable final Long id,
		@RequestBody @Valid UpdateChatRequest updateChatRequest) {
		return chatService.updateChat(id, updateChatRequest);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteChat(@PathVariable final Long id) {
		return chatService.deleteChat(id);
	}
}
