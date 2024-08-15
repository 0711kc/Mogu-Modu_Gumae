package com.bunsaned3thinking.mogu.post.controller.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.bunsaned3thinking.mogu.common.util.LocationUtil;
import com.bunsaned3thinking.mogu.post.entity.Category;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.PostDetail;
import com.bunsaned3thinking.mogu.post.entity.PostImage;
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
	private Integer discountPrice;

	@NotNull(message = "기존 가격을 입력해주세요")
	private Integer originalPrice;

	@NotNull(message = "구매 예정 날짜를 입력해주세요")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate purchaseDate;

	@NotNull(message = "구매 여부를 입력해주세요")
	private Boolean purchaseState;

	@NotNull(message = "균등 배분 여부를 입력해주세요")
	private Boolean shareCondition;

	private Integer pricePerCount;

	@NotNull(message = "구매 인원을 입력해주세요")
	private Integer userCount;

	@NotNull(message = "경도를 입력해주세요")
	@DecimalMin(value = "-90.0", message = "-90.0 ~ 90.0 사이의 숫자를 입력해주세요")
	@DecimalMax(value = "90.0", message = "-90.0 ~ 90.0 사이의 숫자를 입력해주세요")
	private Double longitude;

	@NotNull(message = "위도를 입력해주세요")
	@DecimalMin(value = "-90.0", message = "-90.0 ~ 90.0 사이의 숫자를 입력해주세요")
	@DecimalMax(value = "90.0", message = "-90.0 ~ 90.0 사이의 숫자를 입력해주세요")
	private Double latitude;

	public Post toEntity(User user, PostDetail postDetail, PostImage thumbnail, int pricePerCount) {
		return Post.builder()
			.title(title)
			.category(category)
			.discountPrice(discountPrice)
			.originalPrice(originalPrice)
			.shareCondition(shareCondition)
			.pricePerCount(pricePerCount)
			.purchaseDate(purchaseDate)
			.recruitState(RecruitState.RECRUITING)
			.location(LocationUtil.createPoint(longitude, latitude))
			.user(user)
			.postDetail(postDetail)
			.userCount(userCount)
			.thumbnail(thumbnail)
			.build();
	}

	public PostDetail toDetailEntity() {
		return PostDetail.builder()
			.content(content)
			.purchaseState(purchaseState)
			.build();
	}
}
