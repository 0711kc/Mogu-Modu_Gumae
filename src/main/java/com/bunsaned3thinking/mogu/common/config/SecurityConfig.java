package com.bunsaned3thinking.mogu.common.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bunsaned3thinking.mogu.common.jwt.CustomUserDetailsService;
import com.bunsaned3thinking.mogu.common.jwt.filter.ExceptionHandlerFilter;
import com.bunsaned3thinking.mogu.common.jwt.filter.JwtFilter;
import com.bunsaned3thinking.mogu.common.jwt.filter.LoginFilter;
import com.bunsaned3thinking.mogu.common.oauth.CustomOAuth2UserService;
import com.bunsaned3thinking.mogu.common.oauth.OauthFailureHandler;
import com.bunsaned3thinking.mogu.common.oauth.OauthSuccessHandler;
import com.bunsaned3thinking.mogu.common.util.JwtUtil;

import io.micrometer.common.lang.NonNullApi;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@NonNullApi
public class SecurityConfig implements WebMvcConfigurer {
	public static final Long TOKEN_PERIOD_MS = 60 * 60 * 1000L;
	private final CustomOAuth2UserService oAuth2UserService;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;
	private final OauthSuccessHandler successHandler;
	private final OauthFailureHandler failureHandler;

	private static final String[] WHITE_LIST_POST = {
		"/login",
		"/user"
	};

	private static final String[] ADMIN_POST = {
		"/notice"
	};

	private static final String[] ADMIN_GET = {
		"/post/reports",
		"/user/{userId}",
		"/user/all"
	};

	private static final String[] ADMIN_PATCH = {
		"/notice/{id}",
		"/complaint/{id}",
		"/user/block/{userId}"
	};

	private static final String[] ADMIN_DELETE = {
		"/notice/{id}",
		"/user/{userId}"
	};

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
			.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {

				CorsConfiguration configuration = new CorsConfiguration();

				configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
				configuration.setAllowedMethods(Collections.singletonList("*"));
				configuration.setAllowCredentials(true);
				configuration.setAllowedHeaders(Collections.singletonList("*"));
				configuration.setMaxAge(3600L);

				configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
				configuration.setExposedHeaders(Collections.singletonList("Authorization"));

				return configuration;
			}));

		http
			.formLogin(AbstractHttpConfigurer::disable);

		http
			.httpBasic(AbstractHttpConfigurer::disable);

		http
			.oauth2Login((oauth2) -> oauth2
				.userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
					.userService(oAuth2UserService))
				.successHandler(successHandler)
				.failureHandler(failureHandler));

		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/error").permitAll()
				.requestMatchers(HttpMethod.POST, WHITE_LIST_POST).permitAll()
				.requestMatchers(HttpMethod.POST, ADMIN_POST).hasAnyRole("ADMIN")
				.requestMatchers(HttpMethod.GET, ADMIN_GET).hasAnyRole("ADMIN")
				.requestMatchers(HttpMethod.PATCH, ADMIN_PATCH).hasAnyRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, ADMIN_DELETE).hasAnyRole("ADMIN")
				.anyRequest().authenticated());

		http
			.addFilterAfter(new ExceptionHandlerFilter(), LogoutFilter.class);

		http
			.addFilterAfter(new JwtFilter(jwtUtil, customUserDetailsService), ExceptionHandlerFilter.class);

		http
			.addFilterAfter(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
				JwtFilter.class);
		// http
		// 	.addFilterAfter(new ExceptionHandlerFilter(), OAuth2LoginAuthenticationFilter.class);
		//
		// http
		// 	.addFilterAfter(new JwtFilter(jwtUtil, customUserDetailsService), ExceptionHandlerFilter.class);
		//
		// http
		// 	.addFilterAfter(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
		// 		JwtFilter.class);

		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
