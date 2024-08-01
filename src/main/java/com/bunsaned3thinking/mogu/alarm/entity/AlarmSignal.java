package com.bunsaned3thinking.mogu.alarm.entity;

import com.bunsaned3thinking.mogu.ask.entity.Ask;
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
public class AlarmSignal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ask_id")
	private Ask ask;

	@Size(max = 50)
	@Column(length = 50)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public static AlarmSignal of(Ask ask, String content, User user) {
		return AlarmSignal.builder()
			.ask(ask)
			.content(content)
			.user(user)
			.build();
	}
}
