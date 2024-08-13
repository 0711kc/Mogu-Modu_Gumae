package com.bunsaned3thinking.mogu.user.entity;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LevelRange {
	LEVEL_RANGE_1(1, 9, 3),
	LEVEL_RANGE_10(10, 29, 5),
	LEVEL_RANGE_30(30, 49, 8),
	LEVEL_RANGE_50(50, 99, 10),
	LEVEL_RANGE_100(100, 9999, 20);

	public static final LevelRange DEFAULT = LEVEL_RANGE_1;
	public static final int MIN_LEVEL = LEVEL_RANGE_1.min;
	public static final int MAX_LEVEL = LEVEL_RANGE_100.max;

	private final int min;
	private final int max;
	private final int purchaseCount;

	public static LevelRange getLevelRange(int level) {
		return Arrays.stream(LevelRange.values())
			.filter(levelRange -> levelRange.min <= level & level <= levelRange.max)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("올바른 범위를 넘어간 레벨 값입니다."));
	}
}
