package com.bunsaned3thinking.mogu.location.service;

import org.springframework.stereotype.Service;

import com.bunsaned3thinking.mogu.location.entity.ActivityArea;
import com.bunsaned3thinking.mogu.location.repository.ActivityAreaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityAreaServiceImpl implements ActivityAreaService {
	private final ActivityAreaRepository activityAreaRepository;

	@Override
	public ActivityArea create(Double longitude, Double latitude) {
		ActivityArea activityArea = ActivityArea.from(longitude, latitude);
		return activityAreaRepository.save(activityArea);
	}
}
