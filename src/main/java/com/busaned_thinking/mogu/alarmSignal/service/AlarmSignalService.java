package com.busaned_thinking.mogu.alarmSignal.service;

import org.springframework.http.ResponseEntity;

import com.busaned_thinking.mogu.alarmSignal.controller.dto.request.AlarmSignalRequest;
import com.busaned_thinking.mogu.alarmSignal.controller.dto.response.AlarmSignalResponse;

public interface AlarmSignalService {
	ResponseEntity<AlarmSignalResponse> createAlarmSignal(AlarmSignalRequest alarmSignalRequest);

	ResponseEntity<Void> deleteAlarmSignal(Long id);

	ResponseEntity<AlarmSignalResponse> findAlarmSignal(Long id);
}
