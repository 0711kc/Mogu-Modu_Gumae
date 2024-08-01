package com.bunsaned3thinking.mogu.location.entity;

import java.awt.geom.Point2D;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private Point2D.Double referencePoint;

	public static Location of(double longitude, double latitude) {
		Point2D.Double referencePoint = new Point2D.Double(longitude, latitude);
		return Location.builder()
			.referencePoint(referencePoint)
			.build();
	}

	public Double getLongitude() {
		return referencePoint.getX();
	}

	public Double getLatitude() {
		return referencePoint.getY();
	}
}
