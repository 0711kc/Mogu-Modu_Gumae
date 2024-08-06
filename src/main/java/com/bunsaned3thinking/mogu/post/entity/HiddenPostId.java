package com.bunsaned3thinking.mogu.post.entity;

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
public class HiddenPostId {
	private Long user;
	private Long post;

	public static HiddenPostId of(Long userUid, Long postId) {
		return HiddenPostId.builder()
			.user(userUid)
			.post(postId)
			.build();
	}
}
