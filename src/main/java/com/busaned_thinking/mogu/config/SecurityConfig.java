package com.busaned_thinking.mogu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.busaned_thinking.mogu.jwt.ExceptionHandlerFilter;
import com.busaned_thinking.mogu.jwt.JWTFilter;
import com.busaned_thinking.mogu.jwt.JWTUtil;
import com.busaned_thinking.mogu.jwt.LoginFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;

	// private static final String[] ADMIN_POST = {
	// 	"/notice/new",
	// };
	//
	// private static final String[] ADMIN_PATCH = {
	// 	"/notice/{id}",
	// 	"/complaint/{id}"
	// };
	//
	// private static final String[] ADMIN_DELETE = {
	// 	"/notice/{id}",
	// };
	//
	// private static final String[] AUTH_POST = {
	// 	"/complaint/{id}"
	// };
	//
	// private static final String[] AUTH_GET = {
	// 	"/notice/{id}",
	// 	"/complaint/{id}"
	// };

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
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

		http
			.authorizeHttpRequests((auth) -> auth
				// .requestMatchers(HttpMethod.POST, ADMIN_POST).hasAnyRole("ADMIN_ROLE")
				// .requestMatchers(HttpMethod.PATCH, ADMIN_PATCH).hasAnyRole("ADMIN_ROLE")
				// .requestMatchers(HttpMethod.DELETE, ADMIN_DELETE).hasAnyRole("ADMIN_ROLE")
				// .requestMatchers(HttpMethod.GET, AUTH_POST).authenticated()
				// .requestMatchers(HttpMethod.GET, AUTH_GET).authenticated()

				// .requestMatchers(HttpMethod.POST, WHITE_LIST_POST).permitAll()
				// .requestMatchers(HttpMethod.GET, WHITE_LIST_GET).permitAll()
				// .requestMatchers(HttpMethod.GET, TEMP_WHITE_LIST_GET).permitAll()
				// .requestMatchers(HttpMethod.DELETE, TEMP_WHITE_LIST_DELETE).permitAll()
				// .requestMatchers(HttpMethod.PATCH, TEMP_WHITE_LIST_PATCH).permitAll()
				// .requestMatchers(WHITE_LIST_SWAGGER).permitAll()
				// .anyRequest().authenticated());
				.anyRequest().permitAll());

		http
			.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

		http
			.addFilterBefore(new ExceptionHandlerFilter(), JWTFilter.class);

		http
			.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
				UsernamePasswordAuthenticationFilter.class);

		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
