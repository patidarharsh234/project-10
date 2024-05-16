package com.rays.jwtblacklistuse;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rays.common.ORSResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class UpdateAndBuildNewToken {
	@Value("${jwt.secret}")
	private String secret;

	public ORSResponse RefreshToken(HttpServletRequest request, ORSResponse res) {

		final String requestTokenHeader = request.getHeader("Authorization");
		
		String modifiedToken = null;

		// token Decod then update then build a new Token>>>>>>------>>>
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			String jwtToken = requestTokenHeader.substring(7);

			// Get Token Detaiels
			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody();
			System.out.println("token get in claims......===>" + claims);

			// Expiration time Check
			long expirationTime = claims.getExpiration().getTime();
			System.out.println("Expiration Time: " + expirationTime);

			// Update the claim any thing you cane update Token Like-
			claims.setExpiration(new Date(System.currentTimeMillis() + 0)); // Example: Current time + 1 hour
			claims.setSubject(claims.getSubject());

			// Build a new token with the updated claims
			modifiedToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
			System.out.println("Modified Token: " + modifiedToken);

		}
		res.addResult("RefreshToken", modifiedToken);

		return res;

	}

}
