package com.bunsaned3thinking.mogu.alarm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.alarm.controller.dto.request.FcmMessage;
import com.bunsaned3thinking.mogu.alarm.controller.dto.response.AlarmSignalResponse;

public interface AlarmSignalService {

	ResponseEntity<AlarmSignalResponse> createAlarmSignal(String userId, Long postId);

	ResponseEntity<Void> deleteAlarmSignal(Long id);

	ResponseEntity<List<AlarmSignalResponse>> findAlarmSignal(String userId);

	ResponseEntity<Boolean> sendMessageTo(FcmMessage fcmMessage);
}
