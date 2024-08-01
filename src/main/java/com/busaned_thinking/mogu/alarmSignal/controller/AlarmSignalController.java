package com.busaned_thinking.mogu.alarmSignal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busaned_thinking.mogu.alarmSignal.controller.dto.response.AlarmSignalResponse;
import com.busaned_thinking.mogu.alarmSignal.service.AlarmSignalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmSignalController {
	private final AlarmSignalService alarmSignalService;

	@GetMapping("/{userId}")
	public ResponseEntity<List<AlarmSignalResponse>> findAlarmSignal(@PathVariable String userId) {
		return alarmSignalService.findAlarmSignal(userId);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAlarmSignal(@PathVariable Long id) {
		return alarmSignalService.deleteAlarmSignal(id);
	}

}
