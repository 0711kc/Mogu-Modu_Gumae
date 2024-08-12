package com.bunsaned3thinking.mogu.common.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amazonaws.services.kms.model.DisabledException;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.bunsaned3thinking.mogu.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserId(username)
			.orElseThrow(EntityNotFoundException::new);

		if (user.getIsBlock()) {
			throw new DisabledException("[Error] 비활성화된 계정입니다.");
		}

		return CustomUserDetails.from(user);
	}
}
