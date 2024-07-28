package com.busaned_thinking.mogu.complaint.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@OneToMany(fetch = FetchType.LAZY)
	@Builder.Default
	private List<ComplaintImage> complaintImages = new ArrayList<>();

	@Size(max = 500)
	@Column(length = 500)
	private String content;

	@Column
	@Builder.Default
	private Short state = ComplaintState.DEFAULT.getIndex();

	@Size(max = 50)
	@Column(length = 50)
	private String title;

	@Column
	private Short type;

	public void update(String answer, Short state) {
		this.answer = answer;
		this.state = state;
	}
}
