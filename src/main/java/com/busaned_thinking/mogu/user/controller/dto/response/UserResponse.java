package com.busaned_thinking.mogu.user.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.busaned_thinking.mogu.chat.entity.Chat;
import com.busaned_thinking.mogu.complaint.entity.Complaint;
import com.busaned_thinking.mogu.location.entity.ActivityArea;
import com.busaned_thinking.mogu.post.entity.HiddenPost;
import com.busaned_thinking.mogu.search.entity.Search;
import com.busaned_thinking.mogu.signal.entity.AlarmSignal;
import com.busaned_thinking.mogu.user.entity.Manner;
import com.busaned_thinking.mogu.user.entity.Role;
import com.busaned_thinking.mogu.user.entity.User;
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
	private ActivityArea activityAreas;
	private List<Chat> chats;
	private List<Complaint> complaints;
	private List<HiddenPost> hiddenPosts;
	private List<Search> searches;
	private List<AlarmSignal> alarmSignals;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.userId(user.getUserId())
			.name(user.getName())
			.nickname(user.getNickname())
			.phone(user.getPhone())
			.email(user.getEmail())
			.role(Role.findByIndex(user.getRole()).getResponse())
			.isBlock(user.getIsBlock())
			.blockDate(user.getBlockDate())
			.profileImage(user.getProfileImage())
			.level(user.getLevel())
			.manner(Manner.findByScore(user.getManner()).getDescription())
			.registerDate(user.getRegisterDate())
			.activityAreas(user.getActivityArea())
			.chats(user.getChats())
			.complaints(user.getComplaints())
			.hiddenPosts(user.getHiddenPosts())
			.searches(user.getSearches())
			.alarmSignals(user.getAlarmSignals())
			.build();
	}
}
