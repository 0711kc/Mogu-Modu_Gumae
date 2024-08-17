package com.bunsaned3thinking.mogu.user.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavingCostResponse {
	private final String userId;
	private final Integer savingCost;
	private final Integer purchaseCount;

	public static SavingCostResponse of(String userId, Integer savingCost, Integer purchaseCount) {
		return SavingCostResponse.builder()
			.userId(userId)
			.savingCost(savingCost)
			.purchaseCount(purchaseCount)
			.build();
	}
}
