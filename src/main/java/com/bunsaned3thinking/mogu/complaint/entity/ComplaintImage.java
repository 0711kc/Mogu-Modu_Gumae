package com.bunsaned3thinking.mogu.complaint.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ComplaintImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 100)
	@Column(length = 100)
	private String image;

	// TODO Complaint, ComplaintImage 매핑 관계 단방향에서 양방향으로 수정 - 중간 테이블 발생 방지를 위해서

	public static ComplaintImage from(String imageLink) {
		return ComplaintImage.builder()
			.image(imageLink)
			.build();
	}
}
