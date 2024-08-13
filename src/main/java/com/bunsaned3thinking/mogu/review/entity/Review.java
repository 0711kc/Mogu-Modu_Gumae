package com.bunsaned3thinking.mogu.review.entity;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.user.entity.Manner;
import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.persistence.Column;
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
@IdClass(ReviewId.class)
public class Review {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_uid")
	private User receiver;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_uid")
	private User sender;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@Column
	private Manner manner;

	public static Review of(User sender, User receiver, Manner manner, Post post) {
		return Review.builder()
			.sender(sender)
			.receiver(receiver)
			.manner(manner)
			.post(post)
			.build();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Review review)) {
			return false;
		}
		if (!review.getPost().getId().equals(post.getId())) {
			return false;
		}
		if (!review.getSender().getUid().equals(sender.getUid())) {
			return false;
		}
		return review.getReceiver().getUid().equals(receiver.getUid());
	}
}
