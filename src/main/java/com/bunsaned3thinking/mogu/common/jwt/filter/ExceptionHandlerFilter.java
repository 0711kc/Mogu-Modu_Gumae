package com.bunsaned3thinking.mogu.common.jwt.filter;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
			setErrorResponse(response, "[Error] 유효하지 않은 토큰값입니다.");
		} catch (DisabledException e) {
			setErrorResponse(response, "[Error] 비활성화된 계정입니다.");
		} catch (UsernameNotFoundException e) {
			setErrorResponse(response, "[Error] 로그인에 실패했습니다.");
		}
	}

	private void setErrorResponse(HttpServletResponse response, String message)
		throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("text/plain; charset=UTF-8");
		response.getWriter().write(message);
	}
}
