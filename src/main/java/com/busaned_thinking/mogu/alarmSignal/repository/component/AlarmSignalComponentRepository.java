package com.busaned_thinking.mogu.alarmSignal.repository.component;

import java.util.List;
import java.util.Optional;

import com.busaned_thinking.mogu.alarmSignal.entity.AlarmSignal;
import com.busaned_thinking.mogu.user.entity.User;

public interface AlarmSignalComponentRepository {
	Optional<AlarmSignal> findAlarmSignalById(Long id);

	void deleteAlarmSignalById(Long id);

	Optional<User> findUserById(String userId);

	List<AlarmSignal> findAlarmSignalByUserUid(Long uid);
}
