package com.bunsaned3thinking.mogu.alarm.repository.module;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.alarm.entity.AlarmSignal;

public interface AlarmSignalRepository extends JpaRepository<AlarmSignal, Long> {
	List<AlarmSignal> findByUserUid(Long uid);
}
