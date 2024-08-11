package com.bunsaned3thinking.mogu.heart.entity;

import com.bunsaned3thinking.mogu.post.entity.Post;
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
@IdClass(HeartId.class)
public class Heart {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public static Heart of(Post post, User user) {
		return Heart.builder()
			.post(post)
			.user(user)
			.build();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Heart heart)) {
			return false;
		}
		if (!heart.getPost().getId().equals(this.post.getId())) {
			return false;
		}
		return heart.getUser().getUid().equals(this.user.getUid());
	}
}
