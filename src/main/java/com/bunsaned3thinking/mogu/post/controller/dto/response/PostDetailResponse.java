package com.bunsaned3thinking.mogu.post.controller.dto.response;

import java.util.List;

import com.bunsaned3thinking.mogu.post.entity.PostDetail;
import com.bunsaned3thinking.mogu.post.entity.PostImage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailResponse {
	private final String content;

	private final Boolean purchaseState;

	private final Boolean shareCondition;

	private final List<String> postImages;

	public static PostDetailResponse from(final PostDetail postDetail) {
		if (postDetail == null) {
			return null;
		}
		return PostDetailResponse.builder()
			.content(postDetail.getContent())
			.purchaseState(postDetail.getPurchaseState())
			.shareCondition(postDetail.getShareCondition())
			.postImages(postDetail.getPostImages().stream()
				.map(PostImage::getImage)
				.toList())
			.build();
	}
}
