package com.busaned_thinking.mogu.location.service;

import org.locationtech.jts.geom.Point;

import com.busaned_thinking.mogu.location.entity.ActivityArea;

public interface ActivityAreaService {
	public ActivityArea create(Point referencePoint);
}
