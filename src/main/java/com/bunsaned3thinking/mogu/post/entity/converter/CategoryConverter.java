package com.bunsaned3thinking.mogu.post.entity.converter;

import com.bunsaned3thinking.mogu.post.entity.Category;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, Short> {
	@Override
	public Short convertToDatabaseColumn(Category category) {
		if (category == null) {
			return null;
		}
		return category.getIndex();
	}

	@Override
	public Category convertToEntityAttribute(Short index) {
		if (index == null) {
			return null;
		}
		return Category.findByIndex(index);
	}
}
