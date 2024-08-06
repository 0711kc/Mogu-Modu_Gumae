package com.bunsaned3thinking.mogu.complaint.entity.converter;

import com.bunsaned3thinking.mogu.complaint.entity.ComplaintState;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ComplaintStateConverter implements AttributeConverter<ComplaintState, Short> {
	@Override
	public Short convertToDatabaseColumn(ComplaintState complaintState) {
		if (complaintState == null) {
			return null;
		}
		return complaintState.getIndex();
	}

	@Override
	public ComplaintState convertToEntityAttribute(Short index) {
		if (index == null) {
			return null;
		}
		return ComplaintState.findByIndex(index);
	}
}
