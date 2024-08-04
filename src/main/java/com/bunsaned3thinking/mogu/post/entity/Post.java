package com.bunsaned3thinking.mogu.post.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bunsaned3thinking.mogu.ask.entity.Ask;
import com.bunsaned3thinking.mogu.chat.entity.Chat;
import com.bunsaned3thinking.mogu.location.entity.Location;
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
// @Document(indexName = "posts", createIndex = false)
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Category category;

	@Column
	@Builder.Default
	// @Field(type = FieldType.Boolean)
	private Boolean isHidden = false;

	@Column
	@Builder.Default
	// @Field(type = FieldType.Date)
	private LocalDateTime postDate = LocalDateTime.now();

	@Column
	// @Field(type = FieldType.Date)
	private LocalDateTime purchaseDate;

	@Column
	// @Field(type = FieldType.Integer)
	private Integer userCount;

	@Column
	@Builder.Default
	// @Field(type = FieldType.Keyword)
	private RecruitState recruitState = RecruitState.DEFAULT;

	@Size(max = 50)
	@Column(length = 50)
	// @Field(type = FieldType.Text)
	private String title;

	@Column
	// @Field(type = FieldType.Integer)
	private Integer discountCost;

	@Column
	// @Field(type = FieldType.Integer)
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

	@OneToOne(mappedBy = "post", fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Chat chat;

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
