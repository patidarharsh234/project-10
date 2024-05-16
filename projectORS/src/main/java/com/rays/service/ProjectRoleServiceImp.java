package com.rays.service;

import org.springframework.stereotype.Service;

import com.rays.common.BaseServiceImpl;
import com.rays.dao.ProjectRoleDAOInt;
import com.rays.dto.ProjectRoleDTO;

@Service
public class ProjectRoleServiceImp extends BaseServiceImpl<ProjectRoleDTO, ProjectRoleDAOInt> implements ProjectRoleServiceInt{

}
