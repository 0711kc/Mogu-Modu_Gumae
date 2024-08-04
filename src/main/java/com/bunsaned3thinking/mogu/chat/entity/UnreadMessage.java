package com.bunsaned3thinking.mogu.chat.entity;

import com.bunsaned3thinking.mogu.user.entity.User;

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
@IdClass(UnreadMessageId.class)
public class UnreadMessage {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id")
	private ChatMessage message;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_uid")
	private User user;

	public static UnreadMessage of(ChatMessage message, User user) {
		return UnreadMessage.builder()
			.message(message)
			.user(user)
			.build();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof UnreadMessage unreadMessage)) {
			return false;
		}
		if (!unreadMessage.getMessage().getId().equals(this.message.getId())) {
			return false;
		}
		return unreadMessage.getUser().getUid().equals(this.user.getUid());
	}
}
