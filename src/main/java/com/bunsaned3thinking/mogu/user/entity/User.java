package com.bunsaned3thinking.mogu.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Point;

import com.bunsaned3thinking.mogu.alarm.entity.AlarmSignal;
import com.bunsaned3thinking.mogu.ask.entity.Ask;
import com.bunsaned3thinking.mogu.chat.entity.ChatMessage;
import com.bunsaned3thinking.mogu.chat.entity.ChatUser;
import com.bunsaned3thinking.mogu.chat.entity.UnreadMessage;
import com.bunsaned3thinking.mogu.common.config.S3Config;
import com.bunsaned3thinking.mogu.common.util.LocationUtil;
import com.bunsaned3thinking.mogu.complaint.entity.Complaint;
import com.bunsaned3thinking.mogu.heart.entity.Heart;
import com.bunsaned3thinking.mogu.post.entity.HiddenPost;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.converter.PointConverter;
import com.bunsaned3thinking.mogu.report.entity.Report;
import com.bunsaned3thinking.mogu.searchhistory.entity.SearchHistory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;

	@Size(max = 12)
	@Column(length = 12, unique = true, updatable = false)
	private String userId;

	@Size(max = 80)
	@Column(length = 80)
	private String password;

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

	@Column(updatable = false)
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
	private String profileImage = S3Config.UserImage;

	@Column
	@Builder.Default
	private Integer level = 0;

	@Column(updatable = false)
	@Builder.Default
	private LocalDateTime registerDate = LocalDateTime.now();

	@Column(columnDefinition = "POINT SRID 4326")
	@Convert(converter = PointConverter.class)
	private Point location;

	@Column
	@Builder.Default
	private Short distanceMeters = 500;

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

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<Heart> hearts = new ArrayList<>();

	public static User of(String userId, String password, String name, String nickname,
		String phone, String email, Role role, Point location) {
		return User.builder()
			.userId(userId)
			.password(password)
			.name(name)
			.nickname(nickname)
			.phone(phone)
			.email(email)
			.role(role)
			.location(location)
			.build();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof User user)) {
			return false;
		}
		return user.getUid().equals(this.uid);
	}

	public void update(String nickname, Double longitude, Double latitude, Short distanceMeters) {
		this.nickname = nickname;
		this.location = LocationUtil.createPoint(longitude, latitude);
		this.distanceMeters = distanceMeters;
	}

	public void updateProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateLoginInfo(String email, String name) {
		this.email = email;
		this.name = name;
	}
}
