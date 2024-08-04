package com.bunsaned3thinking.mogu.post.controller.dto.response;

import com.bunsaned3thinking.mogu.searchhistory.entity.SearchHistory;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchHistoryResponse {
	private final String content;

	public static SearchHistoryResponse from(SearchHistory searchHistory) {
		return SearchHistoryResponse.builder()
			.content(searchHistory.getContent())
			.build();
	}
}
