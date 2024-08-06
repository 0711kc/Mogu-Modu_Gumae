package com.bunsaned3thinking.mogu.complaint.entity;

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
public class ComplaintImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 100)
	@Column(length = 100)
	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "complaint_id")
	private Complaint complaint;

	public static ComplaintImage of(Complaint complaint, String imageLink) {
		return ComplaintImage.builder()
			.image(imageLink)
			.complaint(complaint)
			.build();
	}
}
