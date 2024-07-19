package com.busaned_thinking.mogu.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busaned_thinking.mogu.chat.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
