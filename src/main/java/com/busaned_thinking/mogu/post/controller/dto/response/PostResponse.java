package com.busaned_thinking.mogu.post.controller.dto.response;

import java.time.LocalDateTime;

import com.busaned_thinking.mogu.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {
	private final Long id;

	private final String category;

	private final Boolean isHidden;

	private final String recruitState;

	private final String title;

	private final String userNickname;

	private final Long userId;

	private final Integer discountCost;

	private final Integer originalCost;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime postDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime purchaseDate;

	private final Integer userCount;

	private final PostDetailResponse postDetailResponse;

	private final LocationResponse location;

	public static PostResponse from(final Post post) {
		return PostResponse.builder()
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
			.location(LocationResponse.from(post.getLocation()))
			.postDetailResponse(PostDetailResponse.from(post.getPostDetail()))
			.build();
	}
}
