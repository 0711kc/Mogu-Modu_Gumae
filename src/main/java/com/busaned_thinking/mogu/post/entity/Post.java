package com.busaned_thinking.mogu.post.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.busaned_thinking.mogu.ask.entity.Ask;
import com.busaned_thinking.mogu.location.entity.Location;
import com.busaned_thinking.mogu.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Category category;

	@Column
	@Builder.Default
	private Boolean isHidden = false;

	@Column
	@Builder.Default
	private LocalDateTime postDate = LocalDateTime.now();

	@Column
	private LocalDateTime purchaseDate;

	@Column
	private Integer userCount;

	@Column
	@Builder.Default
	private RecruitState recruitState = RecruitState.DEFAULT;

	@Size(max = 50)
	@Column(length = 50)
	private String title;

	@Column
	private Integer discountCost;

	@Column
	private Integer originalCost;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Ask> asks = new ArrayList<>();

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Report> reports = new ArrayList<>();

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Heart> hearts = new ArrayList<>();

	@OneToOne(mappedBy = "post", fetch = FetchType.LAZY)
	private PostDetail postDetail;

	@OneToOne(fetch = FetchType.LAZY)
	private Location location;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public void update(Category category, LocalDateTime purchaseDate, int userCount, String title, int discountCost,
		int originalCost, Location location) {
		this.category = category;
		this.purchaseDate = purchaseDate;
		this.userCount = userCount;
		this.title = title;
		this.discountCost = discountCost;
		this.originalCost = originalCost;
		this.location = location;
	}

	public void updateRecruitState(RecruitState recruitState) {
		this.recruitState = recruitState;
	}

	public void updateHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
}
