package com.bunsaned3thinking.mogu.post.controller.dto.response;

import java.time.LocalDateTime;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostWithDetailResponse {
	private final Long id;
	private final String category;
	private final Boolean isHidden;
	private final String recruitState;
	private final String title;
	private final String userNickname;
	private final Long userId;
	private final Integer discountCost;
	private final Integer originalCost;
	private final Integer userCount;
	private final PostDetailResponse postDetailResponse;
	private final Double longitude;
	private final Double latitude;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime postDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime purchaseDate;

	public static PostWithDetailResponse from(final Post post) {
		return PostWithDetailResponse.builder()
			.id(post.getId())
			.category(post.getCategory().getResponse())
			.isHidden(post.getIsHidden())
			.recruitState(post.getRecruitState().getResponse())
			.title(post.getTitle())
			.userNickname(post.getUser().getNickname())
			.userId(post.getUser().getUid())
			.discountCost(post.getDiscountCost())
			.originalCost(post.getOriginalCost())
			.postDate(post.getPostDate())
			.purchaseDate(post.getPurchaseDate())
			.userCount(post.getUserCount())
			.longitude(post.getLocation().getX())
			.latitude(post.getLocation().getY())
			.postDetailResponse(PostDetailResponse.from(post.getPostDetail()))
			.build();
	}
}
