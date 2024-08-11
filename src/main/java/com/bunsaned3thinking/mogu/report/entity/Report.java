package com.bunsaned3thinking.mogu.report.entity;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@IdClass(ReportId.class)
public class Report {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_uid")
	private User user;

	@Column
	private ReportType type;

	@Size(max = 500)
	@Column(length = 500)
	private String content;

	public static Report of(Post post, User user, ReportType type, String content) {
		return Report.builder()
			.post(post)
			.user(user)
			.type(type)
			.content(content)
			.build();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Report report)) {
			return false;
		}
		if (!report.getUser().getUid().equals(this.user.getUid())) {
			return false;
		}
		return report.getPost().getId().equals(this.post.getId());
	}
}
