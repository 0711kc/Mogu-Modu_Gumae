package com.busaned_thinking.mogu.location.entity;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "activity_area")
public class ActivityArea {
	private static final short DEFAULT_DISTANCE = 500;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "point")
	private Point referencePoint;

	@Column()
	private Short distanceMeters;

	public static ActivityArea from(Point referencePoint) {
		return ActivityArea.builder()
			.referencePoint(referencePoint)
			.distanceMeters(DEFAULT_DISTANCE)
			.build();
	}

	public void updateDistance() {

	}
}
