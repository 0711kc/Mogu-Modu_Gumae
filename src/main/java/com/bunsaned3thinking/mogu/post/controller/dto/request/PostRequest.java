package com.bunsaned3thinking.mogu.post.controller.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.bunsaned3thinking.mogu.common.util.LocationUtil;
import com.bunsaned3thinking.mogu.post.entity.Category;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.PostDetail;
import com.bunsaned3thinking.mogu.post.entity.RecruitState;
import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate purchaseDate;

	@NotNull(message = "구매 여부를 입력해주세요")
	private Boolean purchaseState;

	@NotNull(message = "균등 배분 여부를 입력해주세요")
	private Boolean shareCondition;

	@NotNull(message = "구매 인원을 입력해주세요")
	private Integer userCount;

	@NotNull(message = "경도를 입력해주세요")
	@DecimalMin("-90.0")
	@DecimalMax("90.0")
	private Double longitude;

	@NotNull(message = "위도를 입력해주세요")
	@DecimalMin("-90.0")
	@DecimalMax("90.0")
	private Double latitude;

	public Post toEntity(User user, PostDetail postDetail) {
		return Post.builder()
			.title(title)
			.category(category)
			.discountCost(discountCost)
			.originalCost(originalCost)
			.purchaseDate(purchaseDate)
			.recruitState(RecruitState.RECRUITING)
			// .location(new Point2D.Double(longitude, latitude))
			.location(LocationUtil.getPoint(longitude, latitude))
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
