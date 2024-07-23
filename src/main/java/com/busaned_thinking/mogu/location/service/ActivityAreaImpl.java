package com.busaned_thinking.mogu.location.service;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import com.busaned_thinking.mogu.location.entity.ActivityArea;
import com.busaned_thinking.mogu.location.repository.ActivityAreaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityAreaImpl implements ActivityAreaService {
	private final ActivityAreaRepository activityAreaRepository;

	@Override
	public ActivityArea create(Point referencePoint) {
		ActivityArea activityArea = ActivityArea.from(referencePoint);
		return activityAreaRepository.save(activityArea);
	}
}
