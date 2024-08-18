package com.bunsaned3thinking.mogu.common.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.bunsaned3thinking.mogu.common.oauth.dto.LoginUserDto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
	private final LoginUserDto userDto;

	public static CustomOAuth2User from(LoginUserDto userDto) {
		return CustomOAuth2User.builder()
			.userDto(userDto)
			.build();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();

		collection.add((GrantedAuthority)userDto::getRole);

		return collection;
	}

	@Override
	public String getName() {
		return userDto.getName();
	}

	public String getUsername() {
		return userDto.getUsername();
	}
}
