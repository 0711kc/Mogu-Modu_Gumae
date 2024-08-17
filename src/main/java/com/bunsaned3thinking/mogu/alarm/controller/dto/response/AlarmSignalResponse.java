package com.bunsaned3thinking.mogu.alarm.controller.dto.response;

import java.time.LocalDateTime;

import com.bunsaned3thinking.mogu.alarm.entity.AlarmSignal;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlarmSignalResponse {
	private final String content;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime createdAt;

	public static AlarmSignalResponse from(AlarmSignal alarmSignal) {
		return AlarmSignalResponse.builder()
			.content(alarmSignal.getContent())
			.createdAt(alarmSignal.getCreatedAt())
			.build();
	}
}
