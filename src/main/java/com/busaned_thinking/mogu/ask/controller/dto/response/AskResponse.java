package com.busaned_thinking.mogu.ask.controller.dto.response;

import com.busaned_thinking.mogu.ask.entity.Ask;
import com.busaned_thinking.mogu.ask.entity.AskState;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AskResponse {
	private Long postId;
	private String userId;
	private String state;

	public static AskResponse from(Ask ask) {
		return AskResponse.builder()
			.postId(ask.getId())
			.userId(ask.getUser().getUserId())
			.state(AskState.findByIndex(ask.getState()).getResponse())
			.build();
	}
}
