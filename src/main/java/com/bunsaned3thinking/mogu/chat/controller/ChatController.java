package com.bunsaned3thinking.mogu.chat.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatMessageResponse;
import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatResponse;
import com.bunsaned3thinking.mogu.chat.service.component.ChatComponentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
	private final ChatComponentService chatComponentService;

	@GetMapping("/{id}")
	public ResponseEntity<ChatResponse> findChat(@PathVariable final Long id) {
		return chatComponentService.findChat(id);
	}

	@GetMapping
	public ResponseEntity<List<ChatResponse>> findChatByUser(Principal principal) {
		return chatComponentService.findChatByUser(principal.getName());
	}

	@DeleteMapping("/{chatId}")
	public ResponseEntity<Void> exitChat(Principal principal, @PathVariable final Long chatId) {
		return chatComponentService.exitChatUser(principal.getName(), chatId);
	}

	@GetMapping("{chatId}/messages")
	public ResponseEntity<List<ChatMessageResponse>> findChatMessages(@PathVariable final Long chatId,
		Principal principal) {
		return chatComponentService.findAllChatMessages(chatId, principal.getName());
	}
}
