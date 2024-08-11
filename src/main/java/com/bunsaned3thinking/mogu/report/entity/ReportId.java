package com.bunsaned3thinking.mogu.report.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class ReportId {
	private Long post;
	private Long user;

	public static ReportId of(Long post, Long user) {
		return ReportId.builder()
			.post(post)
			.user(user)
			.build();
	}
}
