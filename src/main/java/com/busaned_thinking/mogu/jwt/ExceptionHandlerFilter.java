package com.busaned_thinking.mogu.jwt;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
		FilterChain filterChain)
		throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (JwtException | IllegalArgumentException | NoSuchElementException e) {
			setErrorResponse(request, response);
		}
	}

	private void setErrorResponse(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("text/plain; charset=UTF-8");
		response.getWriter().write(
			"[Error] 유효하지 않은 토큰값입니다."
		);
	}
}
