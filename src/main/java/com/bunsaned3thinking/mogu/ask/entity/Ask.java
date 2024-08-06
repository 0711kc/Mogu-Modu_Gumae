package com.bunsaned3thinking.mogu.ask.entity;

import java.util.ArrayList;
import java.util.List;

import com.bunsaned3thinking.mogu.alarm.entity.AlarmSignal;
import com.bunsaned3thinking.mogu.post.entity.Post;
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
public class Ask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@Builder.Default
	private AskState state = AskState.DEFAULT;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_uid")
	private User user;

	@OneToMany(mappedBy = "ask", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<AlarmSignal> alarmSignals = new ArrayList<>();

	public static Ask from(Post post, User user) {
		return Ask.builder()
			.post(post)
			.user(user)
			.build();
	}

	public void update(AskState askState) {
		this.state = askState;
	}
}
