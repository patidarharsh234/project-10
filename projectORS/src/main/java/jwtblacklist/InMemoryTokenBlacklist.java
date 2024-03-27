package jwtblacklist;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class InMemoryTokenBlacklist implements TokenBlacklist {

	private Set<String> blacklist = new HashSet<>();

	public String setLogoutToken;

	@Override
	public void addToBlacklist(String token) {
		blacklist.add(token);
	}

	@Override
	public boolean isBlacklisted(String token) {
		blacklist.contains(token);
		return false;
	}

	@Override
	public String getLogoutToken() {
		return setLogoutToken;
	}

	@Override
	public void setLogoutToken(String setLogoutToken) {
		this.setLogoutToken = setLogoutToken;
	}

}
