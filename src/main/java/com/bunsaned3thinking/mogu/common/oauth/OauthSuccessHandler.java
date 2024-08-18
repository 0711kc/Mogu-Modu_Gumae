package com.bunsaned3thinking.mogu.common.oauth;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.bunsaned3thinking.mogu.common.config.SecurityConfig;
import com.bunsaned3thinking.mogu.common.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JwtUtil jwtUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		//OAuth2User
		CustomOAuth2User customUserDetails = (CustomOAuth2User)authentication.getPrincipal();

		String username = customUserDetails.getUsername();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String role = auth.getAuthority();

		String token = jwtUtil.createJwt(username, role, SecurityConfig.TOKEN_PERIOD_MS);

		response.addCookie(jwtUtil.createCookie("Authorization", token));
		response.sendRedirect("http://localhost:3000/");
	}
}
