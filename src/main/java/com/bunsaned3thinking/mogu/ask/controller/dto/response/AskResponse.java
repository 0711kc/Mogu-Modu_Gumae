package com.bunsaned3thinking.mogu.ask.controller.dto.response;

import com.bunsaned3thinking.mogu.ask.entity.Ask;

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
			.state(ask.getState().getResponse())
			.build();
	}
}
