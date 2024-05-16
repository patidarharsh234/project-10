package jwtblacklistprogress;


import javax.naming.AuthenticationException;

public class JwtTokenMalformedExceptioMe extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public JwtTokenMalformedExceptioMe(String msg) {
		super(msg);
	}

}
