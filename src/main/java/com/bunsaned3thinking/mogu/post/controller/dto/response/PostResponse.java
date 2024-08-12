package com.bunsaned3thinking.mogu.post.controller.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.bunsaned3thinking.mogu.common.util.S3Util;
import com.bunsaned3thinking.mogu.post.entity.Post;
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
	private final Integer userCount;
	private final Double longitude;
	private final Double latitude;
	private final Integer heartCount;
	private final Integer viewCount;
	private final Integer reportCount;
	private final String thumbnail;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime postDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private final LocalDate purchaseDate;

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
			.longitude(post.getLocation().getX())
			.latitude(post.getLocation().getY())
			.heartCount(post.getHearts().size())
			.viewCount(post.getViewCount())
			.reportCount(post.getReports().size())
			.thumbnail(S3Util.toS3ImageUrl(post.getThumbnail().getImage()))
			.build();
	}
}
