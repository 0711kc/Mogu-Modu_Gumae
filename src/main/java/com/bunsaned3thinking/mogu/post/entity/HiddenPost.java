package com.bunsaned3thinking.mogu.post.entity;

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
@IdClass(HiddenPostId.class)
public class HiddenPost {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_uid")
	private User user;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	public static HiddenPost of(User user, Post post) {
		return HiddenPost.builder()
			.user(user)
			.post(post)
			.build();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof HiddenPost hiddenPost)) {
			return false;
		}
		if (!user.getUid().equals(hiddenPost.getUser().getUid())) {
			return false;
		}
		return post.getId().equals(hiddenPost.getPost().getId());
	}
}
