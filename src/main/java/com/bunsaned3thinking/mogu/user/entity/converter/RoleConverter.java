package com.bunsaned3thinking.mogu.user.entity.converter;

import com.bunsaned3thinking.mogu.user.entity.Role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Short> {
	@Override
	public Short convertToDatabaseColumn(Role role) {
		if (role == null) {
			return null;
		}
		return role.getIndex();
	}

	@Override
	public Role convertToEntityAttribute(Short index) {
		if (index == null) {
			return null;
		}
		return Role.findByIndex(index);
	}
}
