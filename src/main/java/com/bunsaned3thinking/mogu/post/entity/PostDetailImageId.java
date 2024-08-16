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
public class PostDetailImageId {
	private Long postDetail;
	private Long postImage;

	public static PostDetailImageId of(Long postDetailId, Long postImageId) {
		return PostDetailImageId.builder()
			.postDetail(postDetailId)
			.postImage(postImageId)
			.build();
	}
}
