package com.bunsaned3thinking.mogu.post.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	private Long id;

	@Size(max = 500)
	@Column(length = 500)
	private String content;

	@Column
	private Boolean purchaseState;

	@OneToMany(mappedBy = "postDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<PostDetailImage> postImages = new ArrayList<>();

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	public void update(String content) {
		this.content = content;
	}

	public void updatePostImages(List<PostDetailImage> postDetailImages) {
		this.postImages = postDetailImages;
	}
}
