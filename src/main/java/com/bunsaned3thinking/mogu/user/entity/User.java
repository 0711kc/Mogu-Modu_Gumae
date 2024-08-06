package com.bunsaned3thinking.mogu.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bunsaned3thinking.mogu.alarm.entity.AlarmSignal;
import com.bunsaned3thinking.mogu.ask.entity.Ask;
import com.bunsaned3thinking.mogu.chat.entity.ChatMessage;
import com.bunsaned3thinking.mogu.chat.entity.ChatUser;
import com.bunsaned3thinking.mogu.chat.entity.UnreadMessage;
import com.bunsaned3thinking.mogu.complaint.entity.Complaint;
import com.bunsaned3thinking.mogu.config.S3Config;
import com.bunsaned3thinking.mogu.location.entity.ActivityArea;
import com.bunsaned3thinking.mogu.post.entity.HiddenPost;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.report.entity.Report;
import com.bunsaned3thinking.mogu.searchhistory.entity.SearchHistory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

	@Column
	private Role role;

	@Column
	@Builder.Default
	private Manner manner = Manner.DEFAULT;

	@Column
	@Builder.Default
	private Boolean isBlock = false;

	@Column
	@Builder.Default
	private LocalDateTime blockDate = null;

	@Size(max = 80)
	@Column(length = 80)
	@Builder.Default
	private String profileImage = S3Config.basicUserImage();

	@Column
	@Builder.Default
	private Integer level = 0;

	@Column
	@Builder.Default
	private LocalDateTime registerDate = LocalDateTime.now();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ActivityArea activityArea;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Builder.Default
	private List<Post> posts = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<Ask> asks = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Builder.Default
	private List<ChatUser> chats = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Builder.Default
	private List<Complaint> complaints = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Builder.Default
	private List<HiddenPost> hiddenPosts = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Builder.Default
	private List<SearchHistory> searchHistories = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Builder.Default
	private List<Report> reports = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<AlarmSignal> alarmSignals = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<UnreadMessage> unreadMessages = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Builder.Default
	private List<ChatMessage> chatMessages = new ArrayList<>();

	public static User of(String userId, String password, String registration, String name, String nickname,
		String phone, String email, Role role, ActivityArea activityArea) {
		return User.builder()
			.userId(userId)
			.password(password)
			.registration(registration)
			.name(name)
			.nickname(nickname)
			.phone(phone)
			.email(email)
			.role(role)
			.activityArea(activityArea)
			.build();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof User user)) {
			return false;
		}
		return user.getUid().equals(this.uid);
	}

	public void update(String name) {
		this.name = name;
	}

	public void updateProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
}
