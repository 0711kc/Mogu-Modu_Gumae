package com.bunsaned3thinking.mogu.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.chat.entity.ChatUser;
import com.bunsaned3thinking.mogu.chat.entity.ChatUserId;

public interface ChatUserRepository extends JpaRepository<ChatUser, ChatUserId> {
}
