package com.bunsaned3thinking.mogu.complaint.entity.converter;

import com.bunsaned3thinking.mogu.complaint.entity.ComplaintType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ComplaintTypeConverter implements AttributeConverter<ComplaintType, Short> {
	@Override
	public Short convertToDatabaseColumn(ComplaintType complaintType) {
		if (complaintType == null) {
			return null;
		}
		return complaintType.getIndex();
	}

	@Override
	public ComplaintType convertToEntityAttribute(Short index) {
		if (index == null) {
			return null;
		}
		return ComplaintType.findByIndex(index);
	}
}
