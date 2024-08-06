package com.bunsaned3thinking.mogu.user.entity.converter;

import com.bunsaned3thinking.mogu.user.entity.Manner;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MannerConverter implements AttributeConverter<Manner, Short> {
	@Override
	public Short convertToDatabaseColumn(Manner manner) {
		if (manner == null) {
			return null;
		}
		return manner.getScore();
	}

	@Override
	public Manner convertToEntityAttribute(Short score) {
		if (score == null) {
			return null;
		}
		return Manner.findByScore(score);
	}
}
