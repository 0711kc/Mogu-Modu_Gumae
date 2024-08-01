package com.busaned_thinking.mogu.alarmSignal.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busaned_thinking.mogu.alarmSignal.controller.dto.response.AlarmSignalResponse;
import com.busaned_thinking.mogu.alarmSignal.entity.AlarmSignal;
import com.busaned_thinking.mogu.alarmSignal.repository.AlarmSignalRepository;
import com.busaned_thinking.mogu.ask.repository.AskRepository;
import com.busaned_thinking.mogu.user.entity.User;
import com.busaned_thinking.mogu.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmSignalServiceImpl implements AlarmSignalService {
	private final AlarmSignalRepository alarmSignalRepository;
	private final AskRepository askRepository;
	private final UserRepository userRepository;

	@Override
	public ResponseEntity<Void> deleteAlarmSignal(Long id) {
		AlarmSignal alarmSignal = alarmSignalRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 알림을 찾을 수 없습니다."));
		alarmSignalRepository.deleteById(alarmSignal.getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<List<AlarmSignalResponse>> findAlarmSignal(String userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		List<AlarmSignal> alarmSignals = alarmSignalRepository.findByUserUid(user.getUid());
		List<AlarmSignalResponse> alarmSignalResponses = alarmSignals.stream()
			.map(AlarmSignalResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(alarmSignalResponses);
	}

}
