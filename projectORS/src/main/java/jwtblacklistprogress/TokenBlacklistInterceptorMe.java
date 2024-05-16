package jwtblacklistprogress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.rays.jwtblacklistuse.TokenBlacklist1;

//@Component
public class TokenBlacklistInterceptorMe implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);

			if (token != null && TokenBlacklist1.isBlacklisted(token)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return false;
			}

		}
		return true;

	}
}