package com.busaned_thinking.mogu.alarmSignal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busaned_thinking.mogu.alarmSignal.entity.AlarmSignal;

public interface AlarmSignalRepository extends JpaRepository<AlarmSignal, Long> {
	List<AlarmSignal> findByUserUid(Long uid);
}
