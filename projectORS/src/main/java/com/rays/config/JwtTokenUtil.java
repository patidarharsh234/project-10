package com.rays.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.rays.service.JwtUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsMutator;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.impl.JwtMap;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;

/**
 * @authorHarsh Patidar
 *
 */

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	// public static final long JWT_TOKEN_VALIDITY = 5*60*60;
	@Autowired
	private JwtUserDetailsService jwtService;
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private long jwtExpiration;

	/*
	 * ES CLASS AND ESKI PARENT CLASS ME VO METHODS HE JISME ANT TK ME SAB POINTS
	 * SET HUE HE like-setSubject/getSubject etc. public class DefaultClaims extends
	 * JwtMap implements Claims {
	 * 
	 * public DefaultClaims() { super(); }
	 * 
	 * public DefaultClaims(Map<String, Object> map) { super(map); }
	 * 
	 * 
	 * @Override public String getSubject() { return getString(SUBJECT); }
	 * 
	 * @Override public Claims setSubject(String sub) { setValue(SUBJECT, sub);
	 * return this; }
	 */
//login id he subject me-->jo ki --doGenerateToken-- me set ki gyi he
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);

	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	// name se toke return kr ra indirectly like suject key map me set ki usne name
	// nikal use attach tke apply method ki help se de ra
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * @param token
	 * Token Validate Na hone Pr Bhi Ye Work Krti he.
	 * @return
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	/*
	 * .SIGNWITH(SIGNATUREALGORITHM.HS512, SECRET)-->
	 * 
	 * JWA algorithm name for {@code HMAC using SHA-512} HS512("HS512",
	 * "HMAC using SHA-512", "HMAC", "HmacSHA512", true),
	 */

	// CLAIMS me SUBJECT set karaya gya he--> defaoultJwt class->> ke ander

	// setClaims -map ka object de ra AND-setsubject ks data us claims me put kara
	// diya he

	// 1-jwts->(jwts class ka object return)

	/*
	 * 2-builder()-METHODE->jwts class me
	 * 
	 * public static JwtBuilder builder() { return new DefaultJwtBuilder(); }
	 * 
	 * ye class ka object--> public class defaultjwtbuilder implements jwtbuilder
	 * 
	 */

	/*
	 * public INTERFACE JwtBuilder EXTENDS ClaimsMutator<JwtBuilder>
	 * CLASS--me--METHOD-> JWTBUILDER SETCLAIMS(MAP<STRING, OBJECT> CLAIMS);
	 */

	// 3- SETCLAIMS-METHOD
	/*
	 * public class defaultjwtbuilder IMPLEMENTS jwtbuilder
	 * 
	 * public JwtBuilder setClaims(Map<String, Object> claims) { this.claims =
	 * Jwts.claims(claims); return this; }
	 */

	/*
	 * CLAIMS-METHOD---->jwts class me public static Claims claims(Map<String,
	 * Object> claims) { return new DefaultClaims(claims);<-counstructor }
	 */

	/*
	 * DEFAULTCLAIMS(counstructor)-->public class DefaultClaims extends JwtMap
	 * implements Claims__CLASS ME METHOD HE. public DefaultClaims(Map<String,
	 * Object> map) { super(map); }
	 */

	/*
	 * super(map)--> public class JwtMap implements Map<String,Object> {
	 * 
	 * private final Map<String, Object> map; //ab ye Map<String, Object> map=new
	 * HashMap(); bn gya
	 * 
	 * public JwtMap(Map<String, Object> map) {
	 * Assert.notNull(map,"Map argument cannot be null."); this.map = map; }
	 */

	/*
	 * 4-SETSUBJECT(subject)-method->DefaultJwtBuilder class me
	 * 
	 * public JwtBuilder setSubject(String sub) { if (Strings.hasText(sub)) {
	 * ensureClaims().setSubject(sub); } else { if (this.claims != null) {
	 * claims.setSubject(sub); } } return this; }
	 */

	// ensureClaims() --> this.claims = new DefaultClaims();
	// CLASS DefaultClaims--setSubject(sub)--> setSubject(String sub){
	// setValue(SUBJECT, sub);}

	// class JwtMap -->setValue(String name, Object v)--> map.put(name, v);

	// finaly -->map.put(subject ,login);

	// setClaims(claims)--map diya ant me AND setSubject(subject)--esne SUBJECT
	// VALUE ko MAP.PUT methd me set kiya ant me
	// Map<String, Object> map=new HashMap();
	// map.put(subject ,login);

	public String generateToken(String LoginId) {
		Map<String, Object> claims = new HashMap<>();
		final UserDetails userDetails = jwtService.loadUserByUsername(LoginId);

		Date now = new Date();
		Date expiration = new Date(now.getTime() + jwtExpiration * 1000);

		
		
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(now)
				.setExpiration(expiration).signWith(SignatureAlgorithm.HS512, secret).compact();

	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

	}
}
