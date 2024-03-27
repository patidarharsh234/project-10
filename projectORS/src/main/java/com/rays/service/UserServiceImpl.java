package com.rays.service;

import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rays.common.BaseServiceImpl;
import com.rays.common.UserContext;
import com.rays.common.mail.EmailDTO;
import com.rays.common.mail.EmailServiceImpl;
import com.rays.dao.UserDAOInt;
import com.rays.dto.CollegeDTO;
import com.rays.dto.UserDTO;

/**
 * Session facade of Role Service. It is transactional, apply declarative
 * transactions with help of Spring AOP.
 * 
 * If unchecked exception is propagated from a method then transaction is rolled
 * back.
 * 
 * Default propagation value is Propagation.REQUIRED and readOnly = false
 * 
 * @authorHarsh Patidar
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<UserDTO, UserDAOInt> implements UserServiceInt {

	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	EmailServiceImpl emailService = null;

	@Autowired
	UserDAOInt userDAO;

	/**
	 * Find user by login id
	 */
	@Transactional(readOnly = true)
                                 	
	public UserDTO findByLoginId(String login, UserContext userContext) {
		                               //login    =?
		return baseDao.findByUniqueKey("loginId", login, userContext);
	}

	/**
	 * Authenticate a user
	 */
	@Override
	public UserDTO authenticate(String loginId, String password) {

		UserDTO dto = findByLoginId(loginId, null);
		if (dto != null) {
			UserContext userContext = new UserContext(dto);
			if (password.equals(dto.getPassword())) {
				dto.setLastLogin(new Timestamp((new Date()).getTime()));
				dto.setUnsucessfullLoginAttempt(0);
				update(dto, userContext);
				return dto;
			} else {
				dto.setUnsucessfullLoginAttempt(1 + dto.getUnsucessfullLoginAttempt());
				update(dto, userContext);
			}
		}
		return null;
	}

	/**
	 * Changes password of logged-in user
	 */
	@Override
	public UserDTO changePassword(String loginId, String oldPassword, String newPassword, UserContext userContext) {

		UserDTO dto = findByLoginId(loginId, null);

		if (oldPassword.equals(dto.getPassword())) {

			dto.setPassword(newPassword);
			update(dto, userContext);

			EmailDTO emailDTO = new EmailDTO();
			emailDTO.addTo(dto.getLoginId());

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("user", dto.getFirstName() + " " + dto.getLastName());
			params.put("password", dto.getPassword());
			emailDTO.setMessageCode("U-CP", params);

			emailService.send(emailDTO, null);

			return dto;
		} else {
			return null;
		}
	}

	/**
	 * Forgot password
	 */
	@Override
	public UserDTO forgotPassword(String loginId) {
		UserDTO dto = findByLoginId(loginId, null);

	
		if (dto == null) {
			return null;
		}

		EmailDTO emailDTO = new EmailDTO();
		emailDTO.addTo(dto.getLoginId());

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user",dto.getLoginId());
		params.put("password", dto.getPassword());
		
		
		emailDTO.setMessageCode("U-FP", params);

		emailService.send(emailDTO, null);

		return dto;
	}

	/**
	 * Register new user
	 */
	@Override
	public UserDTO register(UserDTO dto) {

		UserContext userContext = new UserContext();
		userContext.setLoginId("super@nenosystems.com");
		userContext.setOrgId(0L);
		userContext.setOrgName("root");

		Long id = add(dto, userContext);

		dto.setId(id);
		
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.addTo(dto.getLoginId());
		
		/* project 4
		 * *HashMap<String, String> map = new HashMap<String, String>();
		 * map.put("login", beanExist.getLogIn());
		 * map.put("password", beanExist.getPassword());
		 * map.put("firstName", beanExist.getFirstName());
		 * map.put("lastName", beanExist.getLastName()); 
	    */

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user", dto.getFirstName() + " " + dto.getLastName());
		params.put("login", dto.getLoginId());
		params.put("password", dto.getPassword());
		
		
	/* project 4
	  	 *String message = EmailBuilder.getChangePasswordMessage(map);
		 * EmailMassage msg = new EmailMassage();
		 * 
		 * msg.setTo(beanExist.getLogIn());
		 * msg.setSubject("SUNRAYS ORS Password has been changed successfully");
		 * msg.setMessage(message); msg.setMassageType(EmailMassage.HTML_MSG);
	*/	
	//project 4
	//EmailUtility.sendMail(msg);
		emailDTO.setMessageCode("U-REG", params);
        emailService.send(emailDTO, userContext);

		return dto;
	}

	@Override
	public UserDTO findByEmail(String email, UserContext userContext) {
		return userDAO.findByEmail("email", email, userContext);

	}

}
