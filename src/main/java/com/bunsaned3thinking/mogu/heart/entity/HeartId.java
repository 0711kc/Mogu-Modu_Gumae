package com.bunsaned3thinking.mogu.heart.entity;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.user.entity.User;

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
public class HeartId {
	private Long user;
	private Long post;

	public static HeartId of(Post post, User user) {
		return HeartId.builder()
			.post(post.getId())
			.user(user.getUid())
			.build();
	}
}
