package com.bunsaned3thinking.mogu.chat.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.chat.entity.UnreadMessage;
import com.bunsaned3thinking.mogu.chat.entity.UnreadMessageId;

public interface UnreadMessageRepository extends JpaRepository<UnreadMessage, UnreadMessageId> {
}
