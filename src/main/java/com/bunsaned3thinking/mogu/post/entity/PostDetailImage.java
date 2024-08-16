package com.bunsaned3thinking.mogu.post.entity;

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
@IdClass(PostDetailImageId.class)
public class PostDetailImage {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_detail_id")
	private PostDetail postDetail;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_image_id")
	private PostImage postImage;

	public static PostDetailImage of(PostDetail postDetail, PostImage postImage) {
		return PostDetailImage.builder()
			.postDetail(postDetail)
			.postImage(postImage)
			.build();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof PostDetailImage postDetailImage)) {
			return false;
		}
		if (!postDetailImage.getPostImage().getId().equals(this.postImage.getId())) {
			return false;
		}
		return postDetailImage.getPostDetail().getId().equals(this.postDetail.getId());
	}
}
