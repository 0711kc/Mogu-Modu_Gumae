package com.busaned_thinking.mogu.signal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busaned_thinking.mogu.signal.entity.AlarmSignal;

public interface AlarmSignalRepository extends JpaRepository<AlarmSignal, Long> {
}
