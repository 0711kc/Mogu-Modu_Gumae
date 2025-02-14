package com.bunsaned3thinking.mogu.alarm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunsaned3thinking.mogu.alarm.controller.dto.request.FcmMessage;
import com.bunsaned3thinking.mogu.alarm.controller.dto.response.AlarmSignalResponse;
import com.bunsaned3thinking.mogu.alarm.service.AlarmSignalService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "알림 API", description = "알림 관련 API 입니다.")
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmSignalController {
	private final AlarmSignalService alarmSignalService;

	@GetMapping("/{userId}")
	@Operation(summary = "알림 조회", description = "특정 사용자의 모든 알림 정보를 조회합니다.")
	public ResponseEntity<List<AlarmSignalResponse>> findAlarmSignal(
		@Parameter(name = "User ID", description = "사용자의 id", in = ParameterIn.PATH)
		@PathVariable String userId) {
		return alarmSignalService.findAlarmSignal(userId);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "알림 삭제", description = "알림 정보를 삭제합니다.")
	public ResponseEntity<Void> deleteAlarmSignal(
		@Parameter(name = "Alarm Signal ID", description = "알림의 id", in = ParameterIn.PATH)
		@PathVariable Long id) {
		return alarmSignalService.deleteAlarmSignal(id);
	}

	// 제대로 되는지 테스트용. 실제로 fcm 생성되는 것은 서비스단에서
	@Hidden
	@PostMapping("/send")
	public ResponseEntity<Boolean> pushMessage(@RequestBody @Validated FcmMessage fcmSendDto) {
		return alarmSignalService.sendMessageTo(fcmSendDto);
	}
}
