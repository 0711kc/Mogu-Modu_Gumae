package com.bunsaned3thinking.mogu.jwt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bunsaned3thinking.mogu.user.entity.Role;
import com.bunsaned3thinking.mogu.user.entity.User;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomUserDetails implements UserDetails {
	private final User user;

	public static CustomUserDetails from(User user) {
		return new CustomUserDetails(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add((GrantedAuthority)() -> Role.findByIndex(user.getRole()).getJwt());

		return collection;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
