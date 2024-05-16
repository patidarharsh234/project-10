package com.rays.jwtblacklistuse;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;


public class TokenBlacklist1 {

	private static Set<String> blacklist = new HashSet<>();

	public static void addToBlacklist(String token) {
		blacklist.add(token);
	}

	public static boolean isBlacklisted(String token) {
		return blacklist.contains(token);

	}
	
	   public static Set<String> getBlacklistedTokens() {
	        return blacklist;
	    }

}
