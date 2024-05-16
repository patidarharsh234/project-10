package com.rays.common;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rays.config.JwtTokenUtil;
import com.rays.dto.UserDTO;
import com.rays.exception.ForbiddenException;
import com.rays.service.JwtUserDetailsService;

import io.jsonwebtoken.JwtException;

/**
 * Front controller verifies if user id logged in
 * 
 * @authorHarsh Patidar
 * 
 */
@Component
public class FrontCtl extends HandlerInterceptorAdapter {
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception, ForbiddenException {
		System.out.println("FrontCtl......");
		String path = request.getServletPath();
		String origin = request.getHeader("Origin");
		HttpSession session = request.getSession();
		//String sessionId = request.getHeader("jsessionid");
		//session.setAttribute("UserContext", sessionId);

		System.out.println("inside  condition====");
		
			if (session.getAttribute("userContext") == null) {
				System.out.println("inter the session null.....!!!!");
				response.setContentType("application/json");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				if (origin != null) {
					response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
					response.setHeader("Access-Control-Allow-Credentials", "true");
					response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT");
				}
				PrintWriter out = response.getWriter();
				// out.print("{\"success\":\"false\",\"error\":\"OOPS! Your session has been
				// expired\"}");
				out.write("OOPS! Your session has been expired");
				out.close();
				System.out.println("going to return false ");

				return false;
			}

		System.out.println("going to return true");
		return true;
	}

	// JWTtOKEN_SE
//		boolean pass = false;
//		final String requestTokenHeader = request.getHeader("Authorization");
//		String username = null;
//		String jwtToken = null;
//		try {
//			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//				System.out.println("Inside Forntctltoken != null");
//				jwtToken = requestTokenHeader.substring(7);
//
//				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//
//			} else {
//				throw new ForbiddenException("Your Token has benn Expierd.....!!!!");
////				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
////				response.getWriter().write("Your Token has been Expierd.......!!!!!!!!!!!");
//			}
//
//		} catch (JwtException e) {
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			response.getWriter().write("Your Token has been Invalidate.....!!!!");
//
//		}
//
//		catch (IllegalArgumentException e) {
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			response.getWriter().write("Your Token has Invalidate.....!!!!");
//
//		} catch (ForbiddenException e) {
//			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//			response.getWriter().write(e.getMessage());
//
//		}
//
//		if (username != null) {
//			System.out.println("inside user != null");
//			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
//
//			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
//
//				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//						userDetails, null, userDetails.getAuthorities());
//				usernamePasswordAuthenticationToken
//						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//				pass = true;
//			}
//
//		}
//
//		response.setHeader("Access-Control-Allow-Origin", "");
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
//		response.setHeader("Access-Control-Allow-Headers",
//				"set-cookie,Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
//		return pass;
//	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String requestUrl = request.getRequestURL().toString();
		String requestMethod = request.getMethod();
		int responseStatus = response.getStatus();
//		modelAndView.addObject("additionalAttribute", "value");
//		modelAndView.setViewName("customViewName");
//		response.addHeader("Custom-Header", "value");
//
		System.out.println(
				"Request URL: {}, Method: {}, Response Status: {}" + requestUrl + requestMethod + responseStatus);

	}
}
