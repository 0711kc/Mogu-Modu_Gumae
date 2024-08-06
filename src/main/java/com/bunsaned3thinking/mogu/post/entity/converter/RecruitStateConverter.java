package com.bunsaned3thinking.mogu.post.entity.converter;

import com.bunsaned3thinking.mogu.post.entity.RecruitState;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RecruitStateConverter implements AttributeConverter<RecruitState, Short> {
	@Override
	public Short convertToDatabaseColumn(RecruitState recruitState) {
		if (recruitState == null) {
			return null;
		}
		return recruitState.getIndex();
	}

	@Override
	public RecruitState convertToEntityAttribute(Short index) {
		if (index == null) {
			return null;
		}
		return RecruitState.findByIndex(index);
	}
}
