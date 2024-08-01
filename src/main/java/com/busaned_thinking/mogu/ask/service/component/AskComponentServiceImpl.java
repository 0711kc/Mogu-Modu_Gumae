package com.busaned_thinking.mogu.ask.service.component;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busaned_thinking.mogu.alarmSignal.service.AlarmSignalService;
import com.busaned_thinking.mogu.ask.controller.dto.response.AskResponse;
import com.busaned_thinking.mogu.ask.service.AskService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AskComponentServiceImpl implements AskComponentService {
	private final AskService askService;
	private final AlarmSignalService alarmSignalService;

	@Override
	public ResponseEntity<AskResponse> createAsk(String userId, Long postId) {
		ResponseEntity<AskResponse> response = askService.createAsk(userId, postId);
		alarmSignalService.createAlarmSignal(userId, postId);
		return response;
	}

	@Override
	public ResponseEntity<List<AskResponse>> findAskByUser(String userId) {
		return askService.findAskByUser(userId);
	}

	@Override
	public ResponseEntity<List<AskResponse>> findAskByPost(Long postId) {
		return askService.findAskByPost(postId);
	}

	@Override
	public ResponseEntity<AskResponse> updateAsk(String userId, Long postId, boolean state) {
		ResponseEntity<AskResponse> response = askService.updateAskState(userId, postId, state);
		alarmSignalService.createAlarmSignal(userId, postId);
		return response;
	}
}
