package com.bunsaned3thinking.mogu.chat.repository.component;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.chat.entity.Chat;
import com.bunsaned3thinking.mogu.chat.entity.ChatMessage;
import com.bunsaned3thinking.mogu.chat.entity.ChatUser;
import com.bunsaned3thinking.mogu.chat.entity.UnreadMessage;
import com.bunsaned3thinking.mogu.chat.entity.UnreadMessageId;
import com.bunsaned3thinking.mogu.chat.repository.module.ChatMessageRepository;
import com.bunsaned3thinking.mogu.chat.repository.module.ChatRepository;
import com.bunsaned3thinking.mogu.chat.repository.module.ChatUserRepository;
import com.bunsaned3thinking.mogu.chat.repository.module.UnreadMessageRepository;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.repository.module.jpa.PostJpaRepository;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.bunsaned3thinking.mogu.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class ChatComponentRepositoryImpl implements ChatComponentRepository {
	private final PostJpaRepository postJpaRepository;
	private final ChatRepository chatRepository;
	private final ChatUserRepository chatUserRepository;
	private final UserRepository userRepository;
	private final ChatMessageRepository chatMessageRepository;
	private final UnreadMessageRepository unreadMessageRepository;

	@Override
	public Optional<Post> findPostById(Long postId) {
		return postJpaRepository.findById(postId);
	}

	@Override
	public Chat saveChat(Chat chat) {
		return chatRepository.save(chat);
	}

	@Override
	public Optional<Chat> findChatById(Long chatId) {
		return chatRepository.findById(chatId);
	}

	@Override
	public List<Chat> findChatByUserId(String userId) {
		return chatRepository.findByUserId(userId);
	}

	@Override
	public ChatUser saveChatUser(ChatUser chatUser) {
		return chatUserRepository.save(chatUser);
	}

	@Override
	public boolean existsChatByPostId(Long postId) {
		return chatRepository.existsByPostId(postId);
	}

	@Override
	public boolean existsUserByUserId(String userId) {
		return userRepository.existsByUserId(userId);
	}

	@Override
	public Optional<ChatUser> findChatUserByUserUidAndChatId(Long userUid, Long chatId) {
		return chatUserRepository.findByUserUidAndChatId(userUid, chatId);
	}

	@Override
	public Optional<ChatUser> findChatUserByUserIdAndChatId(String userId, Long chatId) {
		return chatUserRepository.findByUserIdAndChatId(userId, chatId);
	}

	@Override
	public List<ChatUser> findChatUserByChatId(Long chatId) {
		return chatUserRepository.findByChatId(chatId);
	}

	@Override
	public void deleteChatById(Long chatId) {
		chatRepository.deleteById(chatId);
	}

	@Override
	public Optional<User> findUserByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public boolean existsChatUserByChatIdAndUserId(Long chatId, String userId) {
		return chatUserRepository.existsByPostIdAndUserId(chatId, userId);
	}

	@Override
	public ChatMessage saveChatMessage(ChatMessage chatMessage) {
		return chatMessageRepository.save(chatMessage);
	}

	@Override
	public List<ChatMessage> findChatMessagesByChatId(Long chatId) {
		return chatMessageRepository.findByChatId(chatId);
	}

	@Override
	public void saveChatMessages(List<ChatMessage> chatMessages) {
		chatMessageRepository.saveAll(chatMessages);
	}

	@Override
	public List<UnreadMessage> saveUnreadMessages(List<UnreadMessage> unreadMessages) {
		return unreadMessageRepository.saveAll(unreadMessages);
	}

	@Override
	public void deleteUnreadMessagesByMessageIdAndUserId(UnreadMessageId unreadMessageId) {
		unreadMessageRepository.deleteById(unreadMessageId);
	}
}
