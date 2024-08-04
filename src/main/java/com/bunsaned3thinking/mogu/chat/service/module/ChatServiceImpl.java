package com.bunsaned3thinking.mogu.chat.service.module;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatResponse;
import com.bunsaned3thinking.mogu.chat.entity.Chat;
import com.bunsaned3thinking.mogu.chat.entity.ChatUser;
import com.bunsaned3thinking.mogu.chat.repository.component.ChatComponentRepository;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {
	private final ChatComponentRepository chatComponentRepository;

	@Override
	public ResponseEntity<ChatResponse> createChat(Long postId) {
		Post post = chatComponentRepository.findPostById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		boolean isExistChat = chatComponentRepository.existsChatByPostId(postId);
		if (isExistChat) {
			throw new IllegalArgumentException("[Error] 이미 채팅방이 생성된 게시글입니다.");
		}
		Chat chat = Chat.from(post);
		Chat savedChat = chatComponentRepository.saveChat(chat);
		ChatUser chatUser = ChatUser.of(savedChat, post.getUser());
		chatComponentRepository.saveChatUser(chatUser);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ChatResponse.from(savedChat));
	}

	@Override
	public ResponseEntity<Void> deleteChat(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<ChatResponse> findChat(Long id) {
		Chat chat = chatComponentRepository.findChatById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 채팅방을 찾을 수 없습니다."));
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ChatResponse.from(chat));
	}

	@Override
	public ResponseEntity<List<ChatResponse>> findChatByUser(String userId) {
		boolean isExistUser = chatComponentRepository.existsUserByUserId(userId);
		if (!isExistUser) {
			throw new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다.");
		}
		List<Chat> chats = chatComponentRepository.findChatByUserId(userId);
		List<ChatResponse> responses = chats.stream()
			.map(ChatResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responses);
	}

	@Override
	public ResponseEntity<ChatResponse> updateChat(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<Void> exitChatUser(String userId, Long chatId) {
		User user = chatComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		ChatUser chatUser = chatComponentRepository.findChatUserByUserUidAndChatId(user.getUid(), chatId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 해당 채팅방에 들어간 사용자를 찾을 수 없습니다."));
		chatUser.updateIsExit(true);
		chatComponentRepository.saveChatUser(chatUser);
		List<ChatUser> chatUsers = chatComponentRepository.findChatUserByChatId(chatId);
		List<ChatUser> remainUsers = chatUsers.stream()
			.filter(existChatUser -> !existChatUser.isExit())
			.toList();
		if (remainUsers.isEmpty()) {
			chatComponentRepository.deleteChatById(chatId);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<ChatResponse> enterChatUser(String userId, Long chatId) {
		Chat chat = chatComponentRepository.findChatById(chatId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 채팅방을 찾을 수 없습니다."));
		User user = chatComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		ChatUser chatUser = ChatUser.of(chat, user);
		chatComponentRepository.saveChatUser(chatUser);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ChatResponse.from(chat));
	}
}
