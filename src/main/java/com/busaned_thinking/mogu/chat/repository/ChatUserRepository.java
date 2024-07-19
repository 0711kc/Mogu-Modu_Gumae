package com.busaned_thinking.mogu.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busaned_thinking.mogu.chat.entity.ChatUser;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
}
