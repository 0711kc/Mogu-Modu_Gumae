package com.bunsaned3thinking.mogu.chat.entity;

import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ChatUserId.class)
public class ChatUser {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_id")
	private Chat chat;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column
	@Builder.Default
	private boolean isExit = false;

	public static ChatUser of(Chat chat, User user) {
		return ChatUser.builder()
			.chat(chat)
			.user(user)
			.build();
	}

	public void updateIsExit(boolean isExit) {
		this.isExit = isExit;
	}
}
