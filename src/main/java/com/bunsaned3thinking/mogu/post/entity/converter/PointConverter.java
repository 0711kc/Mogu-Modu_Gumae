package com.bunsaned3thinking.mogu.post.entity.converter;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PointConverter implements AttributeConverter<Point, String> {
	@Override
	public String convertToDatabaseColumn(Point attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.toText();
	}

	@Override
	public Point convertToEntityAttribute(String dbData) {
		try {
			if (dbData == null) {
				return null;
			}
			return (Point)new WKTReader().read(dbData);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
