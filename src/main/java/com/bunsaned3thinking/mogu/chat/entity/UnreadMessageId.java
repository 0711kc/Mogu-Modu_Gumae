package com.bunsaned3thinking.mogu.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class UnreadMessageId {
	private Long message;
	private Long user;

	public static UnreadMessageId of(Long message, Long user) {
		return UnreadMessageId.builder()
			.message(message)
			.user(user)
			.build();
	}
}
