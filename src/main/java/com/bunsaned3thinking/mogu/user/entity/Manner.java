package com.bunsaned3thinking.mogu.user.entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Manner {
	best((short)5, "최고예요!!"),
	good((short)4, "좋아요!"),
	common((short)3, "보통이에요"),
	bad((short)2, "별로예요."),
	worst((short)1, "최악이에요..");

	public static final Manner DEFAULT = good;

	private final short score;
	private final String description;

	@JsonValue
	public short getScore() {
		return score;
	}

	@JsonCreator
	public static Manner findByScore(short score) {
		return Arrays.stream(Manner.values())
			.filter(manner -> manner.getScore() == score)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
