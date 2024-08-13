package com.bunsaned3thinking.mogu.user.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LevelResponse {
	private final Long userUid;
	private final Integer level;
	private final Integer currentPurchaseCount;
	private final Integer needPurchaseCount;

	public static LevelResponse of(long userUid, int level, int currentPurchaseCount, int needPurchaseCount) {
		return LevelResponse.builder()
			.userUid(userUid)
			.currentPurchaseCount(currentPurchaseCount)
			.needPurchaseCount(needPurchaseCount)
			.level(level)
			.build();
	}
}
