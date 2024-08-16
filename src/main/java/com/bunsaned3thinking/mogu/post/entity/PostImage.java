package com.bunsaned3thinking.mogu.post.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
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
public class PostImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 100)
	@Column(length = 100)
	private String image;

	@OneToMany(mappedBy = "postImage", fetch = FetchType.LAZY)
	@Builder.Default
	private List<PostDetailImage> postDetail = new ArrayList<>();

	public static PostImage from(String imageLink) {
		return PostImage.builder()
			.image(imageLink)
			.build();
	}
}
