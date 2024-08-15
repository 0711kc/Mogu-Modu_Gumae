package com.bunsaned3thinking.mogu.review.entity;

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
public class ReviewId {
	private Long sender;
	private Long receiver;
	private Long post;

	public static ReviewId of(Long senderId, Long receiverId, Long postId) {
		return ReviewId.builder()
			.sender(senderId)
			.receiver(receiverId)
			.post(postId)
			.build();
	}
}
