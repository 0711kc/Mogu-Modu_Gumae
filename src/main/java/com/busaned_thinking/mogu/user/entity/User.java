package com.busaned_thinking.mogu.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.busaned_thinking.mogu.chat.entity.Chat;
import com.busaned_thinking.mogu.complaint.entity.Complaint;
import com.busaned_thinking.mogu.location.entity.ActivityArea;
import com.busaned_thinking.mogu.post.entity.HiddenPost;
import com.busaned_thinking.mogu.search.entity.Search;
import com.busaned_thinking.mogu.signal.entity.AlarmSignal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	@Column(length = 12, unique = true)
	private String registration;

	@Size(max = 12)
	@Column(length = 12)
	private String name;

	@Size(max = 12)
	@Column(length = 12)
	private String nickname;

	@Size(max = 11)
	@Column(length = 11, unique = true)
	private String phone;

	@Size(max = 30)
	@Column(length = 30, unique = true)
	private String email;

	@Column()
	private short role;

	@Column()
	private boolean isBlock;

	@Column()
	private LocalDateTime blockDate;

	@Size(max = 80)
	@Column(length = 80)
	private String profileImage;

	@Column()
	private int level;

	@Size(max = 10)
	@Column(length = 10)
	private String manner;

	@Column()
	private LocalDateTime registerDate;

	@OneToOne
	private ActivityArea activityArea;

	@OneToMany
	private ArrayList<Chat> chats = new ArrayList<>();

	@OneToMany
	private ArrayList<Complaint> complaints = new ArrayList<>();

	@OneToMany
	private ArrayList<HiddenPost> hiddenPosts = new ArrayList<>();

	@OneToMany
	private ArrayList<Search> searches = new ArrayList<>();

	@OneToMany
	private ArrayList<AlarmSignal> alarmSignals = new ArrayList<>();

	public void update(String name) {
		this.name = name;
	}
}
