package com.rays.service;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rays.common.BaseServiceImpl;
import com.rays.dao.UserDAOInt;
import com.rays.dto.UserDTO;

@Service
public class JwtUserDetailsService extends BaseServiceImpl<UserDTO, UserDAOInt> implements UserDetailsService {

	@Autowired
	private UserDAOInt userDao;

	
	//loadUserByUsername(dao.loginId);->loginctl pr
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Inside JwtUserDetailsService loadUserByUsername()......" + username);
		
		UserDTO user = userDao.findByEmail("email", username, null);

		if (user == null) {
			throw new UsernameNotFoundException(
					"Inside JwtUserDetailsService loadUserByUsername()......User not found with username: " + username);
		}
		System.out.println("Inside JwtUserDetailsService loadUserByUsername()...... At return statement");
		return new org.springframework.security.core.userdetails.User(user.getLoginId(), user.getPassword(),
				new ArrayList<>());

	}
	
	//internaly kya ho ra
	//User Class he predefine -->token me kam aati he 
	
	//ak tarike se ye username and password set kr User Class ka object return kr rahi he
	
	//final UserDetails userDetails=new User(user.getLoginId(), user.getPassword(),new ArrayList<>());
	
	
	/*
	 * public User(String username, String password, Collection<? extends
	 * GrantedAuthority> authorities) { this(username, password, true, true, true,
	 * true, authorities); }
	 * 
	 * 	this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
		
	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
	 */

}