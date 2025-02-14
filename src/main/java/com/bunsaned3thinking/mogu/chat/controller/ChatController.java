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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "채팅 API", description = "채팅 관련 API 입니다.")
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
	private final ChatComponentService chatComponentService;

	@GetMapping("/{id}")
	@Operation(summary = "채팅방 조회", description = "채팅방 정보를 조회합니다.")
	public ResponseEntity<ChatResponse> findChat(
		@Parameter(name = "Chat ID", description = "채팅방의 id", in = ParameterIn.PATH)
		@PathVariable final Long id) {
		return chatComponentService.findChat(id);
	}

	@GetMapping
	@Operation(summary = "나의 모든 채팅방 조회", description = "나의 모든 채팅방 정보를 조회합니다.")
	public ResponseEntity<List<ChatResponse>> findChatByUser(Principal principal) {
		return chatComponentService.findChatByUser(principal.getName());
	}

	@DeleteMapping("/{chatId}")
	@Operation(summary = "채팅방 나가기", description = "채팅방에서 본인을 삭제합니다.")
	public ResponseEntity<Void> exitChat(Principal principal,
		@Parameter(name = "Chat ID", description = "채팅방의 id", in = ParameterIn.PATH)
		@PathVariable final Long chatId) {
		return chatComponentService.exitChatUser(principal.getName(), chatId);
	}

	@GetMapping("{chatId}/messages")
	@Operation(summary = "채팅 메시지 조회", description = "특정 채팅방에서의 모든 채팅 정보를 조회합니다.")
	public ResponseEntity<List<ChatMessageResponse>> findChatMessages(
		@Parameter(name = "Chat ID", description = "채팅방의 id", in = ParameterIn.PATH)
		@PathVariable final Long chatId,
		Principal principal) {
		return chatComponentService.findAllChatMessages(chatId, principal.getName());
	}
}
