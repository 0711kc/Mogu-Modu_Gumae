package com.bunsaned3thinking.mogu.chat.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany
	private List<ChatMessage> chatMessages = new ArrayList<>();

	@OneToMany
	private List<ChatUser> chatUsers = new ArrayList<>();

	@Size(max = 1000)
	@Column(length = 1000)
	private String lastMsg;

	private LocalDateTime lastTime;

	@Size(max = 12)
	@Column(length = 12)
	private String lastUser;
}
