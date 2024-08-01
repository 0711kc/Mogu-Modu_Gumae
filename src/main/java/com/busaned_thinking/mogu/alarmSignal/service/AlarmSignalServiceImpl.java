package com.busaned_thinking.mogu.alarmSignal.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busaned_thinking.mogu.alarmSignal.controller.dto.request.AlarmSignalRequest;
import com.busaned_thinking.mogu.alarmSignal.controller.dto.response.AlarmSignalResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmSignalServiceImpl implements AlarmSignalService {

	@Override
	public ResponseEntity<AlarmSignalResponse> createAlarmSignal(AlarmSignalRequest alarmSignalRequest) {
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteAlarmSignal(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<AlarmSignalResponse> findAlarmSignal(Long id) {
		return null;
	}
}
