package com.busaned_thinking.mogu.post.entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
	FOOD((short)0, "식료품", "food"),
	DISPOSABLE((short)1, "일회용품", "disposable"),
	CLEANING((short)2, "청소용품", "cleaning"),
	BEAUTY((short)3, "뷰티/미용", "beauty"),
	HOBBY((short)4, "취미/게임", "hobby"),
	LIFE((short)5, "생활/주방", "life"),
	CHILD((short)6, "육아용품", "child"),
	OTHER((short)7, "기타", "other"),
	FREE((short)8, "무료 나눔", "free"),
	;

	private final short index;
	private final String response;
	private final String request;

	@JsonValue
	public String getRequest() {
		return request;
	}

	@JsonCreator
	public static Category from(String request) {
		return Arrays.stream(Category.values())
			.filter(category -> category.getRequest().equals(request))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}

	public static Category findByIndex(short index) {
		return Arrays.stream(Category.values())
			.filter(category -> category.getIndex() == index)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
