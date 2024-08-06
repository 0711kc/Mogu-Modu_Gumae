package com.bunsaned3thinking.mogu.post.controller.dto.request;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.bunsaned3thinking.mogu.post.entity.Category;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.PostDetail;
import com.bunsaned3thinking.mogu.post.entity.RecruitState;
import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostRequest {
	@NotBlank(message = "제목을 입력해주세요.")
	@Size(max = 50)
	private String title;

	@NotNull(message = "카테고리를 입력해주세요.")
	private Category category;

	@NotBlank(message = "내용을 입력해주세요.")
	@Size(max = 500)
	private String content;

	@NotNull(message = "할인 가격을 입력해주세요")
	private Integer discountCost;

	@NotNull(message = "기존 가격을 입력해주세요")
	private Integer originalCost;

	@NotNull(message = "구매 예정 날짜를 입력해주세요")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime purchaseDate;

	@NotNull(message = "구매 여부를 입력해주세요")
	private Boolean purchaseState;

	@NotNull(message = "균등 배분 여부를 입력해주세요")
	private Boolean shareCondition;

	@NotNull(message = "구매 인원을 입력해주세요")
	private Integer userCount;

	@NotNull(message = "경도를 입력해주세요")
	private Double longitude;

	@NotNull(message = "위도를 입력해주세요")
	private Double latitude;

	public Post toEntity(User user, PostDetail postDetail) {
		return Post.builder()
			.title(title)
			.category(category)
			.discountCost(discountCost)
			.originalCost(originalCost)
			.purchaseDate(purchaseDate)
			.recruitState(RecruitState.RECRUITING)
			.location(new Point2D.Double(longitude, latitude))
			.user(user)
			.postDetail(postDetail)
			.userCount(userCount)
			.build();
	}

	public PostDetail toDetailEntity() {
		return PostDetail.builder()
			.content(content)
			.shareCondition(shareCondition)
			.purchaseState(purchaseState)
			.build();
	}
}
