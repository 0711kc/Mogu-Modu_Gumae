package com.bunsaned3thinking.mogu.chat.repository.component;

import java.util.List;
import java.util.Optional;

import com.bunsaned3thinking.mogu.chat.entity.Chat;
import com.bunsaned3thinking.mogu.chat.entity.ChatUser;
import com.bunsaned3thinking.mogu.post.entity.Post;

public interface ChatComponentRepository {
	Optional<Post> findPostById(Long postId);

	Chat saveChat(Chat chat);

	Optional<Chat> findChatById(Long chatId);

	List<Chat> findChatByUserId(String userId);

	void saveChatUser(ChatUser chatUser);

	boolean existsChatByPostId(Long postId);

	boolean existsUserByUserId(String userId);

	Optional<ChatUser> findChatUserByUserIdAndChatId(String userId, Long chatId);

	List<ChatUser> findChatUserByChatId(Long chatId);

	void deleteChatById(Long chatId);
}
