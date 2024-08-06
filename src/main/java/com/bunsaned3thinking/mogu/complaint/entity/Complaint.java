package com.bunsaned3thinking.mogu.complaint.entity;

import java.util.ArrayList;
import java.util.List;

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
public class Complaint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 500)
	@Column(length = 500)
	private String answer;

	@OneToMany(mappedBy = "complaint", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<ComplaintImage> complaintImages = new ArrayList<>();

	@Size(max = 500)
	@Column(length = 500)
	private String content;

	@Column
	@Builder.Default
	private ComplaintState state = ComplaintState.DEFAULT;

	@Size(max = 50)
	@Column(length = 50)
	private String title;

	@Column
	private ComplaintType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_uid")
	private User user;

	public void update(String answer, ComplaintState state) {
		this.answer = answer;
		this.state = state;
	}

	public void updateComplaintImages(List<ComplaintImage> complaintImages) {
		this.complaintImages = complaintImages;
	}
}
