package com.busaned_thinking.mogu.alarmSignal.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.busaned_thinking.mogu.alarmSignal.controller.dto.response.AlarmSignalResponse;

public interface AlarmSignalService {

	ResponseEntity<AlarmSignalResponse> createAlarmSignal(String userId, Long postId);

	ResponseEntity<Void> deleteAlarmSignal(Long id);

	ResponseEntity<List<AlarmSignalResponse>> findAlarmSignal(String userId);
}
