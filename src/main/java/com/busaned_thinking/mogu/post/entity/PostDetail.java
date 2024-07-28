package com.busaned_thinking.mogu.post.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class PostDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany
	private ArrayList<PostImage> postImages = new ArrayList<>();

	@Size(max = 500)
	@Column(length = 500)
	private String content;

	@Column()
	private int discountCost;

	@Column()
	private int originalCost;

	@Column()
	private LocalDateTime postDate;

	@Column()
	private LocalDateTime purchaseDate;

	@Size(max = 10)
	@Column(length = 10)
	private String purchaseStatus;

	@Size(max = 10)
	@Column(length = 10)
	private String recruitStatus;

	@Size(max = 10)
	@Column(length = 10)
	private String shareCondition;

	@Column()
	private int userCount;
}
