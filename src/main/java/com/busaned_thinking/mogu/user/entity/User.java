package com.busaned_thinking.mogu.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
	@Id
	private Long uid;
}