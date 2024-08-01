package com.busaned_thinking.mogu.alarmSignal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busaned_thinking.mogu.alarmSignal.service.AlarmSignalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/AlarmSignal")
@RequiredArgsConstructor
public class AlarmSignalController {
	private final AlarmSignalService alarmSignalService;
}
