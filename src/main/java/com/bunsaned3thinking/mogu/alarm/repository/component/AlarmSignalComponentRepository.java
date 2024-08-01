package com.bunsaned3thinking.mogu.alarm.repository.component;

import java.util.List;
import java.util.Optional;

import com.bunsaned3thinking.mogu.alarm.entity.AlarmSignal;
import com.bunsaned3thinking.mogu.ask.entity.Ask;
import com.bunsaned3thinking.mogu.user.entity.User;

public interface AlarmSignalComponentRepository {
	Optional<AlarmSignal> findAlarmSignalById(Long id);

	void deleteAlarmSignalById(Long id);

	Optional<User> findUserById(String userId);

	List<AlarmSignal> findAlarmSignalByUserUid(Long uid);

	Optional<Ask> findAskByUserUidAndPostId(Long uid, Long postId);

	AlarmSignal saveAlarmSignal(AlarmSignal alarmSignal);
}
