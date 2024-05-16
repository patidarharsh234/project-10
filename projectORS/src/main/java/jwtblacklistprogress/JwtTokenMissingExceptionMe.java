package jwtblacklistprogress;

import javax.naming.AuthenticationException;

public class JwtTokenMissingExceptionMe extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public JwtTokenMissingExceptionMe(String msg) {
		super(msg);
	}

}

