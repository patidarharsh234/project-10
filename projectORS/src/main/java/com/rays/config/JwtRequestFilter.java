package com.rays.config;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rays.exception.ForbiddenException;
import com.rays.jwtblacklistuse.TokenBlacklist1;
import com.rays.service.JwtUserDetailsService;

import org.springframework.security.core.AuthenticationException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
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
			String path = request.getRequestURI();
			System.out.println(".........pathIn JwtRequestFilter" + path);

			final String withCre = request.getHeader("name");

			final String requestTokenHeader = request.getHeader("Authorization");

			String username = null;
			String jwtToken = null;
			
			
			if (path.equals("/Auth/login")) {
				//user Authenticated nhi hota to do filter response me 403 Le kr jata he Authenticated Apis pe.
				chain.doFilter(request, response);
			} else {
				
				

				if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
					jwtToken = requestTokenHeader.substring(7);

					// yhi pr application ko pata chl jayega ki Token Invalidate he.
					// if conditon ke ander ki condition ko chod kisi bhi lien pr Exception rays
					// hoti to catch
					// block handel krta.
					System.out.println("token validate run......!!!!!!!");
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
					System.out.println("token validate pass......!!!!!!!");

					// Me created-> Token Logout//Force fully Token ko Expire Logout ke bad/Fullly
					// Token Expierd not only chang in character.
					// catch block ager application me if ke ander ksis lien pr exception aaye
					// (runTiemExcetion).
//					try {
//						if (TokenBlacklist1.isBlacklisted(jwtToken)) {
//							response.setStatus(HttpStatus.UNAUTHORIZED.value());
//							response.getWriter()
//									.write("Token  Is BlackListed.....!!!===" + HttpStatus.UNAUTHORIZED.value());
//							return;
//						}
//					} catch (Exception e) {
//						response.getWriter().write("TokenBlackListException:" + e.getMessage());
//					} // TheEnd.

				} else {

					// throw new ForbiddenException("Your Session has been Expired! Please
					// Re-Login");
					// response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					// response.getWriter().write("Your Session has been Expired! Please
					// Re-Login...!!!");

				}

				// Application ka(Token) Authentication bhi check kr re ki khali he na.
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

					UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

					if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());

						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

						// Application me yha Token set kiya ja raha he.
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
						System.out.println("Token Set==>" + SecurityContextHolder.getContext().getAuthentication());

					}

				}

				super.doFilter(request, response, chain);
			}//end else condition.
			
			
			
			
			
		} catch (JwtException | AuthenticationException e) {
			// this catch block handel only-validate exception-becouse-jwtException-handl
			// only validate ex.
			// you can set whatever in the setStatus this change only code name (like 401 to
			// 403) -note working.

			// token me kuch characte hata dene pr..or actual Expier hone pr 5 ghante bad .
			// Application Throw kar ri es Exception ko--> Es liye cath box me catch kr ri
			// --> kab to glt token pr or kha pata chl ra application ko.
			// getUserNameFromToken pr
			// --Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			// application or jvm se related code me kisis lien pr exception rays hoti to
			// catch Block handel krta
			// na ki if condition me true or false hone pr
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// 401//token is invalid
			response.getWriter().write("Token is invalid... plz login again..!!");
			response.getWriter().flush();
			response.getWriter().close();

		} catch (ForbiddenException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().write(e.getMessage());
			response.getWriter().flush();
			response.getWriter().close();
		}

	}

}
