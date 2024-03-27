package com.rays.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rays.service.JwtUserDetailsService;

import org.springframework.security.core.AuthenticationException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author Harsh Patidar
 *
 */

//thes class for cheking token is working properliy at every first request pr. es class ko operate hamne webSecurity class me se kiya he.

//ak tarike se forntControler he ye--
//--return header ke sath request aati he forntend se tb check krta token ko SAHI HE AND THEN EXECES KARNE DETA HE next request ko

//hamara token key and value paeare me set hota he

//HEADER(header me set hota he)=(Key=Authorization , value -> bearer token..) 
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		try {
			String path = request.getServletPath();

			final String withCre = request.getHeader("name");

			final String requestTokenHeader = request.getHeader("Authorization");

			String username = null;
			String jwtToken = null;
			// JWT Token is in the form "Bearer token". Remove Bearer word and get only the
			// Token

			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);

				username = jwtTokenUtil.getUsernameFromToken(jwtToken);

			}


            // yha token ko set krte Application me authentication me yhi bata ri application ko ki user authenticate he. 
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

				// if token is valid configure Spring Security to manually set authentication
				if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

					// userDetails.getAuthorities()-->userClass ki constructor ki help se authirity
					// attribute me set karaya taha array vali authorities jo ki
					// userDetailService me de he.
UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
							

					// details me kon si servlet ki request aayi usko add krta deatails me
	usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
							
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the Spring Security
					// Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

				}

			}
   
			chain.doFilter(request, response);
		} catch (JwtException | AuthenticationException e) {
		 //this catch block handel only-validate exception-becouse-jwtException-handl only validate ex.
		//you can set whatever in the setStatus this change only code name (like 401 to 403) -note working.	
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// 401//token is invalid
          // PrintWriter print= response.getWriter();
          // print.write();
			response.getWriter().write("Token is invalid... plz login again..!!");
			response.getWriter().flush();
			response.getWriter().close();
		
		}
//		catch(WebServerException e) {
//			response.setStatus(HttpServletResponse.SC_FORBIDDEN);// -->403//token null
//			response.getWriter().write("your session Expierd plez login agen..!!!");
//			 response.getWriter().flush();
//			response.getWriter().close();
//		}
	}

}
