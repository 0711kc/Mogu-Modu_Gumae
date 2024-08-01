package com.busaned_thinking.mogu.alarmSignal.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busaned_thinking.mogu.alarmSignal.controller.dto.response.AlarmSignalResponse;
import com.busaned_thinking.mogu.alarmSignal.entity.AlarmSignal;
import com.busaned_thinking.mogu.alarmSignal.repository.component.AlarmSignalComponentRepository;
import com.busaned_thinking.mogu.ask.entity.Ask;
import com.busaned_thinking.mogu.ask.entity.AskState;
import com.busaned_thinking.mogu.user.entity.User;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmSignalServiceImpl implements AlarmSignalService {
	private final AlarmSignalComponentRepository alarmSignalComponentRepository;

	@Override
	public ResponseEntity<AlarmSignalResponse> createAlarmSignal(String userId, Long postId) {
		User user = alarmSignalComponentRepository.findUserById(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		Ask ask = alarmSignalComponentRepository.findAskByUserUidAndPostId(user.getUid(), postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 신청 정보를 찾을 수 없습니다."));
		AskState askState = AskState.findByIndex(ask.getState());
		User toUser = switch (askState) {
			case WAITING -> ask.getPost().getUser();
			case REJECTED, APPROVED -> ask.getUser();
		};
		AlarmSignal alarmSignal = AlarmSignal.of(ask, askState.getAlarmMessage(), toUser);
		AlarmSignal savedAlarmSignal = alarmSignalComponentRepository.saveAlarmSignal(alarmSignal);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(AlarmSignalResponse.from(savedAlarmSignal));
	}

	@Override
	public ResponseEntity<Void> deleteAlarmSignal(Long id) {
		AlarmSignal alarmSignal = alarmSignalComponentRepository.findAlarmSignalById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 알림을 찾을 수 없습니다."));
		alarmSignalComponentRepository.deleteAlarmSignalById(alarmSignal.getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<List<AlarmSignalResponse>> findAlarmSignal(String userId) {
		User user = alarmSignalComponentRepository.findUserById(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		List<AlarmSignal> alarmSignals = alarmSignalComponentRepository.findAlarmSignalByUserUid(user.getUid());
		List<AlarmSignalResponse> alarmSignalResponses = alarmSignals.stream()
			.map(AlarmSignalResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(alarmSignalResponses);
	}

}
