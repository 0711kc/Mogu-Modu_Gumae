package com.busaned_thinking.mogu.user.entity;

import com.busaned_thinking.mogu.user.controller.dto.request.UpdateUserRequest;

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
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;

	@Size(max = 12)
	@Column(length = 12, unique = true)
	private String userId;

	@Size(max = 80)
	@Column(length = 80)
	private String password;

	@Size(max = 12)
	@Column(length = 12)
	private String name;

	public void update(String name) {
		this.name = name;
	}
}
