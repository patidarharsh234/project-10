package com.rays.common.projectattachment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rays.common.BaseServiceImpl;
import com.rays.common.UserContext;

/**
 * Session facade of Role Service. It is transactional, apply declarative
 * transactions with help of Spring AOP.
 * 
 * If unchecked exception is propagated from a method then transaction is rolled
 * back.
 * 
 * Default propagation value is Propagation.REQUIRED and readOnly = false
 * @authorHarsh Patidar
 */
@Service
@Transactional
public class ProjectAttachmentServiceImpl extends BaseServiceImpl<ProjectAttachmentDTO, ProjectAttachmentDAOInt>
		implements ProjectAttachmentServiceInt {

	private static Logger log = LoggerFactory.getLogger(ProjectAttachmentServiceImpl.class);

	@Transactional(readOnly = true)
	public ProjectAttachmentDTO findByName(String name, UserContext userContext) {
		return baseDao.findByUniqueKey("name", name, userContext);
		
	}


}
