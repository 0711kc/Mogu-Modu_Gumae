package com.bunsaned3thinking.mogu.post.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Point;

import com.bunsaned3thinking.mogu.ask.entity.Ask;
import com.bunsaned3thinking.mogu.chat.entity.Chat;
import com.bunsaned3thinking.mogu.heart.entity.Heart;
import com.bunsaned3thinking.mogu.report.entity.Report;
import com.bunsaned3thinking.mogu.user.entity.User;

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
import jakarta.persistence.PrimaryKeyJoinColumn;
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

	@Column
	private Category category;

	@Column
	private LocalDate purchaseDate;

	@Column
	private Integer userCount;

	@Column
	@Builder.Default
	private Boolean isHidden = false;

	@Column
	@Builder.Default
	private LocalDateTime postDate = LocalDateTime.now();

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

	@Column
	@Builder.Default
	private Integer viewCount = 0;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	@Builder.Default
	private List<HiddenPost> hiddenPosts = new ArrayList<>();

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

	@Column(columnDefinition = "POINT SRID 4326")
	private Point location;

	@OneToOne(mappedBy = "post", fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Chat chat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "thumbnail_id")
	private PostImage thumbnail;

	public void addViewCount() {
		this.viewCount++;
	}

	public void update(Category category, LocalDate purchaseDate, int userCount, String title, int discountCost,
		int originalCost, Point location) {
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
