package com.busaned_thinking.mogu.alarmSignal.controller.dto.response;

import com.busaned_thinking.mogu.alarmSignal.entity.AlarmSignal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlarmSignalResponse {
	private final String content;

	public static AlarmSignalResponse of(AlarmSignal alarmSignal) {
		return AlarmSignalResponse.builder()
			.content(alarmSignal.getContent())
			.build();
	}
}
