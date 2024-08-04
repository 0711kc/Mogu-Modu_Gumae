package com.bunsaned3thinking.mogu.chat.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class ChatMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 1000)
	private String content;

	@OneToMany(mappedBy = "message", fetch = FetchType.LAZY)
	@Builder.Default
	private List<UnreadMessage> unreadUsers = new ArrayList<>();

	@Column
	@Builder.Default
	private LocalDateTime time = LocalDateTime.now();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_uid")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_id")
	private Chat chat;

	public static ChatMessage of(String content, ChatUser senderChatUser) {
		return ChatMessage.builder()
			.content(content)
			.user(senderChatUser.getUser())
			.chat(senderChatUser.getChat())
			.build();
	}

	public void readMessage(Long userUid) {
		unreadUsers.removeIf(unreadUser -> unreadUser.getUser().getUid().equals(userUid));
	}

	public void updateUnreadMessages(List<UnreadMessage> unreadUsers) {
		this.unreadUsers = unreadUsers;
	}
}
