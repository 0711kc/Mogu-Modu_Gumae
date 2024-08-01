package com.bunsaned3thinking.mogu.post.controller.dto.response;

import java.awt.geom.Point2D;

import com.bunsaned3thinking.mogu.location.entity.Location;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationResponse {
	private Point2D.Double referencePoint;

	public static LocationResponse from(Location location) {
		return LocationResponse.builder()
			.referencePoint(location.getReferencePoint())
			.build();
	}
}
