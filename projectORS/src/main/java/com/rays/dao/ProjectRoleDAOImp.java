package com.rays.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.rays.common.BaseDAOImpl;
import com.rays.dto.ProjectRoleDTO;

@Repository
public class ProjectRoleDAOImp extends BaseDAOImpl<ProjectRoleDTO> implements ProjectRoleDAOInt{

	@Override
	public Class<ProjectRoleDTO> getDTOClass() {
		// TODO Auto-generated method stub
		return ProjectRoleDTO.class;
	}

	@Override
	protected List<Predicate> getWhereClause(ProjectRoleDTO dto, CriteriaBuilder builder, Root<ProjectRoleDTO> qRoot) {
		List<Predicate> whereClauseCondition=new ArrayList<Predicate>();
		return whereClauseCondition;
	}

}
