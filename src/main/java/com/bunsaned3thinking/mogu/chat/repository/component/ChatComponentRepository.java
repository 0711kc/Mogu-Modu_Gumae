package com.bunsaned3thinking.mogu.chat.repository.component;

import java.util.List;
import java.util.Optional;

import com.bunsaned3thinking.mogu.chat.entity.Chat;
import com.bunsaned3thinking.mogu.chat.entity.ChatMessage;
import com.bunsaned3thinking.mogu.chat.entity.ChatUser;
import com.bunsaned3thinking.mogu.chat.entity.UnreadMessage;
import com.bunsaned3thinking.mogu.chat.entity.UnreadMessageId;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.user.entity.User;

public interface ChatComponentRepository {
	Optional<Post> findPostById(Long postId);

	Chat saveChat(Chat chat);

	Optional<Chat> findChatById(Long chatId);

	List<Chat> findChatByUserId(String userId);

	ChatUser saveChatUser(ChatUser chatUser);

	boolean existsChatByPostId(Long postId);

	boolean existsUserByUserId(String userId);

	Optional<ChatUser> findChatUserByUserUidAndChatId(Long userUid, Long chatId);

	Optional<ChatUser> findChatUserByUserIdAndChatId(String userId, Long chatId);

	List<ChatUser> findChatUserByChatId(Long chatId);

	void deleteChatById(Long chatId);

	Optional<User> findUserByUserId(String userId);

	boolean existsChatUserByChatIdAndUserId(Long chatId, String userId);

	ChatMessage saveChatMessage(ChatMessage chatMessage);

	List<ChatMessage> findChatMessagesByChatId(Long chatId);

	void saveChatMessages(List<ChatMessage> chatMessages);

	List<UnreadMessage> saveUnreadMessages(List<UnreadMessage> unreadMessages);

	void deleteUnreadMessagesByMessageIdAndUserId(UnreadMessageId unreadMessageId);
}
