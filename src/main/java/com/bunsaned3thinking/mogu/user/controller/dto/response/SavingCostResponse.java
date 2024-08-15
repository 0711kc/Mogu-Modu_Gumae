package com.bunsaned3thinking.mogu.user.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavingCostResponse {
	private final Long userUid;
	private final Integer savingCost;
	private final Integer purchaseCount;

	public static SavingCostResponse of(Long userUid, Integer savingCost, Integer purchaseCount) {
		return SavingCostResponse.builder()
			.userUid(userUid)
			.savingCost(savingCost)
			.purchaseCount(purchaseCount)
			.build();
	}
}
