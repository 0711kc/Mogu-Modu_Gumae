package com.bunsaned3thinking.mogu.common.util;

import static com.bunsaned3thinking.mogu.user.entity.LevelRange.*;

import com.bunsaned3thinking.mogu.user.entity.LevelRange;

public class LevelUtil {

	public static int calculateLevel(int currentLevel, int purchasedCount) {
		if (calculatePurchaseCountToLevelUp(currentLevel) <= purchasedCount) {
			return currentLevel + 1;
		}
		return currentLevel;
	}

	public static int calculatePurchaseCountToLevelUp(int level) {
		LevelRange levelRange = LevelRange.getLevelRange(level);
		int needPurchaseCount = levelRange.getPurchaseCount() * (level - levelRange.getMin());
		return switch (levelRange) {
			case LEVEL_RANGE_1 -> needPurchaseCount;
			case LEVEL_RANGE_10 -> LEVEL_RANGE_1.getMax() * LEVEL_RANGE_1.getPurchaseCount()
				+ needPurchaseCount;
			case LEVEL_RANGE_30 -> LEVEL_RANGE_1.getMax() * LEVEL_RANGE_1.getPurchaseCount()
				+ LEVEL_RANGE_10.getMax() * LEVEL_RANGE_10.getPurchaseCount()
				+ needPurchaseCount;
			case LEVEL_RANGE_50 -> LEVEL_RANGE_1.getMax() * LEVEL_RANGE_1.getPurchaseCount()
				+ LEVEL_RANGE_10.getMax() * LEVEL_RANGE_10.getPurchaseCount()
				+ LEVEL_RANGE_30.getMax() * LEVEL_RANGE_30.getPurchaseCount()
				+ needPurchaseCount;
			case LEVEL_RANGE_100 -> LEVEL_RANGE_1.getMax() * LEVEL_RANGE_1.getPurchaseCount()
				+ LEVEL_RANGE_10.getMax() * LEVEL_RANGE_10.getPurchaseCount()
				+ LEVEL_RANGE_30.getMax() * LEVEL_RANGE_30.getPurchaseCount()
				+ LEVEL_RANGE_50.getMax() * LEVEL_RANGE_50.getPurchaseCount()
				+ needPurchaseCount;
		};
	}
}
