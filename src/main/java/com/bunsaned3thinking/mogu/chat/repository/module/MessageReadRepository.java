package com.bunsaned3thinking.mogu.chat.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.chat.entity.MessageRead;

public interface MessageReadRepository extends JpaRepository<MessageRead, Long> {
}