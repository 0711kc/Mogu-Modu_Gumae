package com.bunsaned3thinking.mogu.alarm.controller.dto.response;

import com.bunsaned3thinking.mogu.alarm.entity.AlarmSignal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlarmSignalResponse {
	private final String content;

	public static AlarmSignalResponse from(AlarmSignal alarmSignal) {
		return AlarmSignalResponse.builder()
			.content(alarmSignal.getContent())
			.build();
	}
}
