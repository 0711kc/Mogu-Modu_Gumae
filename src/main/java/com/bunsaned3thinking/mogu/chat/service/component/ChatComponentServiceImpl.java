package com.bunsaned3thinking.mogu.chat.service.component;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatMessageResponse;
import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatResponse;
import com.bunsaned3thinking.mogu.chat.service.module.ChatService;
import com.bunsaned3thinking.mogu.post.service.module.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatComponentServiceImpl implements ChatComponentService {
	private final ChatService chatService;
	private final PostService postService;

	@Override
	public ResponseEntity<ChatResponse> findChat(Long id) {
		return chatService.findChat(id);
	}

	@Override
	public ResponseEntity<List<ChatResponse>> findChatByUser(String userId) {
		return chatService.findChatByUser(userId);
	}

	@Override
	public ResponseEntity<Void> exitChatUser(String userId, Long chatId) {
		boolean isChief = postService.isChief(userId, chatId);
		ResponseEntity<Void> response = chatService.exitChatUser(userId, chatId);
		if (isChief) {
			response = postService.deletePost(userId, chatId);
		}
		return response;
	}

	@Override
	public ResponseEntity<List<ChatMessageResponse>> findAllChatMessages(Long chatId, String userId) {
		return chatService.findAllChatMessages(chatId, userId);
	}
}
