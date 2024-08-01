package com.busaned_thinking.mogu.alarmSignal.controller.dto.request;

import com.busaned_thinking.mogu.alarmSignal.entity.AlarmSignal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AlarmSignalRequest {
	
	@NotBlank(message = "내용을 입력해주세요.")
	@Size(max = 50)
	private String content;

	public AlarmSignal toEntity() {
		return AlarmSignal.builder()
			.content(content)
			.build();
	}
}
