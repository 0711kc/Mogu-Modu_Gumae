package com.bunsaned3thinking.mogu.chat.repository.module;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunsaned3thinking.mogu.chat.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	@Query("select cm from ChatMessage cm where cm.chat.id = :chatId")
	List<ChatMessage> findByChatId(Long chatId);
}
