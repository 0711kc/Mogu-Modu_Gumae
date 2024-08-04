package com.bunsaned3thinking.mogu.chat.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.bunsaned3thinking.mogu.chat.entity.Chat;
import com.bunsaned3thinking.mogu.chat.entity.ChatUser;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatResponse {
	private final Long id;
	private final String lastMsg;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime lastTime;

	private final String postThumbnail;
	private final List<String> userImage;
	private final List<String> userUid;

	public static ChatResponse from(Chat chat) {
		return ChatResponse.builder()
			.id(chat.getId())
			.lastMsg(chat.getLastMsg())
			.lastTime(chat.getLastTime())
			// TODO POST 안에 Thumbnail (대표 이미지 링크) String Type 저장해두기
			// .postThumbnail(chat.getPost().getThumbnail())
			.postThumbnail("tempLink")
			.userImage(chat.getChatUsers().stream()
				.map(ChatUser::getUser)
				.map(User::getProfileImage)
				.toList())
			.userUid(chat.getChatUsers().stream()
				.map(ChatUser::getUser)
				.map(User::getUid)
				.map(Objects::toString)
				.toList())
			.build();
	}
}
