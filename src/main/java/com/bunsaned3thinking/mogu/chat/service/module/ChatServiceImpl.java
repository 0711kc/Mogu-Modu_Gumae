package com.bunsaned3thinking.mogu.chat.service.module;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatMessageResponse;
import com.bunsaned3thinking.mogu.chat.controller.dto.response.ChatResponse;
import com.bunsaned3thinking.mogu.chat.entity.Chat;
import com.bunsaned3thinking.mogu.chat.entity.ChatMessage;
import com.bunsaned3thinking.mogu.chat.entity.ChatUser;
import com.bunsaned3thinking.mogu.chat.entity.UnreadMessage;
import com.bunsaned3thinking.mogu.chat.entity.UnreadMessageId;
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
		boolean isExistChat = chatComponentRepository.existsChatByPostId(post.getId());
		if (isExistChat) {
			throw new IllegalArgumentException("[Error] 이미 채팅방이 생성된 게시글입니다.");
		}
		Chat chat = Chat.from(post);
		chatComponentRepository.saveChat(chat);
		ChatUser chatUser = ChatUser.of(chat, post.getUser());
		chatComponentRepository.saveChatUser(chatUser);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ChatResponse.from(chat));
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
	public ResponseEntity<Void> exitChatUser(String userId, Long chatId) {
		ChatUser chatUser = chatComponentRepository.findChatUserByUserIdAndChatId(userId, chatId)
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
	public void enterChatUser(String userId, Long chatId) {
		Chat chat = chatComponentRepository.findChatById(chatId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 채팅방을 찾을 수 없습니다."));
		User user = chatComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		ChatUser chatUser = ChatUser.of(chat, user);
		ChatUser savedChatUser = chatComponentRepository.saveChatUser(chatUser);
		chat.addChatUser(savedChatUser);
		Chat savedChat = chatComponentRepository.saveChat(chat);
		ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ChatResponse.from(savedChat));
	}

	@Override
	public boolean checkChatUser(Long chatId, String userId) {
		return chatComponentRepository.existsChatUserByChatIdAndUserId(chatId, userId);
	}

	@Override
	public ChatMessageResponse createChatMessage(Long chatId, String userId, String message, List<String> readUserIds) {
		List<ChatUser> chatUsers = chatComponentRepository.findChatUserByChatId(chatId);
		ChatUser chatUser = chatUsers.stream()
			.filter(user -> user.getUser().getUserId().equals(userId))
			.findFirst()
			.orElseThrow(() -> new EntityNotFoundException("[Error] 채팅방에 참여한 사용자가 아닙니다."));
		List<User> readUsers = readUserIds.stream()
			.map(chatComponentRepository::findUserByUserId)
			.map(Optional::orElseThrow)
			.toList();
		List<User> unreadUsers = chatUsers.stream()
			.map(ChatUser::getUser)
			.filter(user -> !(readUsers.contains(user) | user.getUserId().equals(userId)))
			.toList();

		ChatMessage chatMessage = ChatMessage.of(message, chatUser);
		ChatMessage savedChatMessage = chatComponentRepository.saveChatMessage(chatMessage);
		List<UnreadMessage> unreadMessages = unreadUsers.stream()
			.map(unreadUser -> UnreadMessage.of(savedChatMessage, unreadUser))
			.toList();

		List<UnreadMessage> savedUnreadMessages = chatComponentRepository.saveUnreadMessages(unreadMessages);
		savedChatMessage.updateUnreadMessages(savedUnreadMessages);
		return ChatMessageResponse.from(savedChatMessage);
	}

	@Override
	public void readMessage(Long chatId, String userId) {
		ChatUser chatUser = chatComponentRepository.findChatUserByUserIdAndChatId(userId, chatId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 채팅방에 참여한 사용자가 아닙니다."));
		List<ChatMessage> chatMessages = chatComponentRepository.findChatMessagesByChatId(chatId);

		chatMessages.forEach(chatMessage -> chatMessage.readMessage(chatUser.getUser().getUid()));
		chatMessages.forEach(chatMessage ->
			chatComponentRepository.deleteUnreadMessagesByMessageIdAndUserId(
				UnreadMessageId.of(chatMessage.getId(), chatUser.getUser().getUid())));

		chatComponentRepository.saveChatMessages(chatMessages);
	}

	@Override
	public ResponseEntity<List<ChatMessageResponse>> findAllChatMessages(Long chatId, String userId) {
		boolean isExistChatUser = chatComponentRepository.existsChatUserByChatIdAndUserId(chatId, userId);
		if (!isExistChatUser) {
			throw new IllegalArgumentException("[Error] 채팅방에 참여한 사용자가 아닙니다.");
		}
		List<ChatMessage> chatMessages = chatComponentRepository.findChatMessagesByChatId(chatId);
		List<ChatMessageResponse> responses = chatMessages.stream()
			.map(ChatMessageResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responses);
	}
}
