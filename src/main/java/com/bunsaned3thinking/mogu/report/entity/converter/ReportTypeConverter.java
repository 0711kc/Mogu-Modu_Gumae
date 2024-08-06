package com.bunsaned3thinking.mogu.report.entity.converter;

import com.bunsaned3thinking.mogu.report.entity.ReportType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReportTypeConverter implements AttributeConverter<ReportType, Short> {
	@Override
	public Short convertToDatabaseColumn(ReportType reportType) {
		if (reportType == null) {
			return null;
		}
		return reportType.getIndex();
	}

	@Override
	public ReportType convertToEntityAttribute(Short index) {
		if (index == null) {
			return null;
		}
		return ReportType.findByIndex(index);
	}
}
