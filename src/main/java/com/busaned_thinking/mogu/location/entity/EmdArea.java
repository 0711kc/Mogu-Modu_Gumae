package com.busaned_thinking.mogu.location.entity;

import java.time.LocalDateTime;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
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
public class EmdArea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "geometry(MultiPolygon,4326)")
	private Geometry geom;

	@Column(columnDefinition = "geometry(Point, 4326)")
	private Geometry location;

	@Size(max= 50)
	@Column(length = 50)
	private String name;

	@ManyToOne
	private SiggArea siggArea = new SiggArea();

	@Column
	private LocalDateTime version;

}
