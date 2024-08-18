package com.bunsaned3thinking.mogu.common.oauth;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.bunsaned3thinking.mogu.common.oauth.dto.LoginUserDto;
import com.bunsaned3thinking.mogu.common.oauth.dto.response.GoogleResponse;
import com.bunsaned3thinking.mogu.common.oauth.dto.response.OAuth2Response;
import com.bunsaned3thinking.mogu.user.entity.Role;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.bunsaned3thinking.mogu.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println("--------------");
		System.out.println(oAuth2User.toString());
		System.out.println("--------------");
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2Response oAuth2Response;
		if (registrationId.equals("google")) {
			oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
		} else {
			return null;
		}
		String email = oAuth2Response.getEmail();
		Optional<User> existData = userRepository.findByUserId(email);
		User user = existData.orElse(
			User.of(email, "password", oAuth2Response.getName(), oAuth2Response.getName(),
				"01012345678", Role.user, null));
		if (existData.isEmpty()) {
			userRepository.save(user);
		}

		LoginUserDto loginUserDto = LoginUserDto.of(user.getRole().getJwt(), oAuth2Response.getName(), email);

		return CustomOAuth2User.from(loginUserDto);
	}
}
