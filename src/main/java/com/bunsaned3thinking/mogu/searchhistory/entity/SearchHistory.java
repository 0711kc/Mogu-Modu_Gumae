package com.bunsaned3thinking.mogu.searchhistory.entity;

import java.time.LocalDateTime;

import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class SearchHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 20)
	@Column(length = 20)
	private String content;

	@Column()
	private LocalDateTime date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_uid")
	private User user;

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof SearchHistory searchHistory)) {
			return false;
		}
		return searchHistory.getId().equals(this.id);
	}

	public static SearchHistory of(String content, User user) {
		return SearchHistory.builder().content(content).date(LocalDateTime.now()).user(user).build();
	}
}
