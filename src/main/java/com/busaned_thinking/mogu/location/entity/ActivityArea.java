package com.busaned_thinking.mogu.location.entity;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ActivityArea {
	//primary key?
	//외래키 어떻게 표현함?

	@Column()
	private short distanceMeters;

	@Column()
	private int emdAreaId;

	@OneToMany
	private ArrayList<EmdArea> emdAreas = new ArrayList<>();




}
