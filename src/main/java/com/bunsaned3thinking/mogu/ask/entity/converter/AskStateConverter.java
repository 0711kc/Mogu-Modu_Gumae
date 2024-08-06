package com.bunsaned3thinking.mogu.ask.entity.converter;

import com.bunsaned3thinking.mogu.ask.entity.AskState;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AskStateConverter implements AttributeConverter<AskState, Short> {
	@Override
	public Short convertToDatabaseColumn(AskState askState) {
		if (askState == null) {
			return null;
		}
		return askState.getIndex();
	}

	@Override
	public AskState convertToEntityAttribute(Short index) {
		if (index == null) {
			return null;
		}
		return AskState.findByIndex(index);
	}
}
