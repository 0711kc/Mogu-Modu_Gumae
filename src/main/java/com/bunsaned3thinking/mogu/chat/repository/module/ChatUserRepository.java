package com.bunsaned3thinking.mogu.chat.repository.module;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunsaned3thinking.mogu.chat.entity.ChatUser;
import com.bunsaned3thinking.mogu.chat.entity.ChatUserId;

public interface ChatUserRepository extends JpaRepository<ChatUser, ChatUserId> {
	@Query("select cu from ChatUser cu left join fetch cu.user "
		+ "where cu.user.userId = :userId and cu.chat.id = :chatId")
	Optional<ChatUser> findByUserIdAndChatId(String userId, Long chatId);

	List<ChatUser> findByChatId(Long chatId);

	@Query("select case when count(cu) > 0 then true else false end from ChatUser cu left join cu.user "
		+ "where cu.user.userId = :userId and cu.chat.id = :chatId")
	boolean existsByPostIdAndUserUid(Long chatId, String userId);
}
