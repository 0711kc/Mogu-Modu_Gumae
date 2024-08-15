package com.bunsaned3thinking.mogu.post.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "posts")
public class PostDoc {
	@Field(type = FieldType.Long)
	private Long id;

	@Field(type = FieldType.Text)
	private String title;

	@Field(type = FieldType.Text)
	private String content;

	@Field(type = FieldType.Text)
	private String name;

	public static PostDoc from(Post post) {
		return PostDoc.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getPostDetail().getContent())
			.name(post.getUser().getNickname())
			.build();
	}
}
