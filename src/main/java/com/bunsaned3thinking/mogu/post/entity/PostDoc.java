package com.bunsaned3thinking.mogu.post.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import jakarta.persistence.Id;
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
	@Id
	private Long id;

	@Field(name = "title", type = FieldType.Text)
	private String title;

	@Field(type = FieldType.Text)
	private String content;

	@Field(type = FieldType.Text)
	private String name;

	public static PostDoc of(Long id, String title, String content, String name) {
		return PostDoc.builder()
			.id(id)
			.title(title)
			.content(content)
			.name(name)
			.build();
	}
}
