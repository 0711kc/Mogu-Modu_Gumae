package com.bunsaned3thinking.mogu.common.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Component;

@Component
public class LocationUtil {
	private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

	public static Point getPoint(double longitude, double latitude) {
		return geometryFactory.createPoint(new Coordinate(longitude, latitude));
	}
}
