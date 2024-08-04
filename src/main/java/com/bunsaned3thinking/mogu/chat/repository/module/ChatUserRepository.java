package com.bunsaned3thinking.mogu.chat.repository.module;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.chat.entity.ChatUser;
import com.bunsaned3thinking.mogu.chat.entity.ChatUserId;

public interface ChatUserRepository extends JpaRepository<ChatUser, ChatUserId> {
	Optional<ChatUser> findByUserIdAndChatId(String userId, Long chatId);

	List<ChatUser> findByChatId(Long chatId);
}
