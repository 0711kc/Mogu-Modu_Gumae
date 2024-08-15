package com.bunsaned3thinking.mogu.post.controller.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.bunsaned3thinking.mogu.post.entity.Category;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.PostDetail;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UpdatePostRequest {
	@Size(max = 50)
	private String title;

	private Category category;

	@Size(max = 500)
	private String content;

	private Integer discountPrice;

	private Integer originalPrice;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate purchaseDate;

	private Boolean shareCondition;

	private Integer pricePerCount;

	private Integer userCount;

	private Double longitude;

	private Double latitude;

	public static UpdatePostRequest from(Post post) {
		if (post.getPostDetail() == null) {
			throw new IllegalArgumentException("[Error] 삭제된 게시글은 수정할 수 없습니다.");
		}
		PostDetail postDetail = post.getPostDetail();
		return UpdatePostRequest.builder()
			.title(post.getTitle())
			.category(post.getCategory())
			.content(postDetail.getContent())
			.discountPrice(post.getDiscountPrice())
			.originalPrice(post.getOriginalPrice())
			.shareCondition(post.getShareCondition())
			.pricePerCount(post.getPricePerCount())
			.purchaseDate(post.getPurchaseDate())
			.userCount(post.getUserCount())
			.longitude(post.getLocation().getX())
			.latitude(post.getLocation().getY())
			.build();
	}
}
