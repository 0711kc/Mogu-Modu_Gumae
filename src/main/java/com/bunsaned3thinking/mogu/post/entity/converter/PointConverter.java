package com.bunsaned3thinking.mogu.post.entity.converter;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PointConverter implements AttributeConverter<Point, Geometry> {
	@Override
	public Geometry convertToDatabaseColumn(Point attribute) {
		return attribute;
	}

	@Override
	public Point convertToEntityAttribute(Geometry dbData) {
		if (dbData == null) {
			return null;
		}
		return (Point)dbData;
	}
}
