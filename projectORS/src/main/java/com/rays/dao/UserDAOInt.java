package com.rays.dao;

import com.rays.common.BaseDAOInt;
import com.rays.common.UserContext;
import com.rays.dto.UserDTO;

/**
 * User DAO interface.
 * @authorHarsh Patidar
 */
public interface UserDAOInt extends BaseDAOInt<UserDTO> {

	public UserDTO findByEmail(String attribute, String val, UserContext userContext);
	
	
}
