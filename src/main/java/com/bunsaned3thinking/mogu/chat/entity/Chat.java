package com.bunsaned3thinking.mogu.chat.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bunsaned3thinking.mogu.post.entity.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
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
public class Chat {
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<ChatMessage> chatMessages = new ArrayList<>();

	@OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<ChatUser> chatUsers = new ArrayList<>();

	@Size(max = 1000)
	@Column(length = 1000)
	@Builder.Default
	private String lastMsg = "";

	@Column
	@Builder.Default
	private LocalDateTime lastTime = LocalDateTime.now();

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "post_id")
	private Post post;

	public static Chat from(Post post) {
		return Chat.builder()
			.post(post)
			.build();
	}

	public void addChatUser(ChatUser chatUser) {
		this.chatUsers.add(chatUser);
	}
}
