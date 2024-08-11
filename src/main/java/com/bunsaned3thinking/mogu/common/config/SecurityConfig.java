package com.bunsaned3thinking.mogu.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bunsaned3thinking.mogu.common.jwt.filter.ExceptionHandlerFilter;
import com.bunsaned3thinking.mogu.common.jwt.filter.JwtFilter;
import com.bunsaned3thinking.mogu.common.jwt.filter.LoginFilter;
import com.bunsaned3thinking.mogu.common.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
	// private final CustomOAuth2UserService oAuth2UserService;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtUtil jwtUtil;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable);

		http
			.formLogin(AbstractHttpConfigurer::disable);

		http
			.httpBasic(AbstractHttpConfigurer::disable);

		// http
		// 	.oauth2Login((oauth2) -> oauth2
		// 		.userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
		// 			.userService(oAuth2UserService)));

		http
			.authorizeHttpRequests((auth) -> auth
				.anyRequest().permitAll());

		http
			.addFilterAfter(new ExceptionHandlerFilter(), OAuth2LoginAuthenticationFilter.class);

		http
			.addFilterAfter(new JwtFilter(jwtUtil), ExceptionHandlerFilter.class);

		http
			.addFilterAfter(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
				JwtFilter.class);

		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
