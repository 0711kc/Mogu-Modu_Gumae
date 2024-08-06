package com.bunsaned3thinking.mogu.user.controller.dto.response;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;

import com.bunsaned3thinking.mogu.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
	private final String userId;
	private final String name;
	private String nickname;
	private String phone;
	private String email;
	private String role;
	private Boolean isBlock;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	@Builder.Default
	private LocalDateTime blockDate = null;
	private String profileImage;
	private Integer level;
	private String manner;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime registerDate;
	private Point2D.Double activityAreas;
	private Short distanceMeters;
	// private List<Chat> chats;
	// private List<Complaint> complaints;
	// private List<HiddenPost> hiddenPosts;
	// private List<Search> searches;
	// private List<AlarmSignal> alarmSignals;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.userId(user.getUserId())
			.name(user.getName())
			.nickname(user.getNickname())
			.phone(user.getPhone())
			.email(user.getEmail())
			.role(user.getRole().getResponse())
			.isBlock(user.getIsBlock())
			.blockDate(user.getBlockDate())
			.profileImage(user.getProfileImage())
			.level(user.getLevel())
			.manner(user.getManner().getDescription())
			.registerDate(user.getRegisterDate())
			.activityAreas(user.getActivityArea().getReferencePoint())
			.distanceMeters(user.getActivityArea().getDistanceMeters())
			// .chats(user.getChats())
			// .complaints(user.getComplaints())
			// .hiddenPosts(user.getHiddenPosts())
			// .searches(user.getSearches())
			// .alarmSignals(user.getAlarmSignals())
			.build();
	}
}
