package com.rays.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rays.common.BaseDAOImpl;
import com.rays.common.UserContext;
import com.rays.common.projectattachment.ProjectAttachmentDAOInt;
import com.rays.common.projectattachment.ProjectAttachmentDTO;
import com.rays.dao.ProjectRoleDAOInt;
import com.rays.dto.ProjectRoleDTO;

@Repository
public class ProjectDAOImp extends BaseDAOImpl<ProjectDTO> implements ProjectDAOInt {

	@Autowired
	protected ProjectRoleDAOInt projectRoleDao;

	@Autowired
	protected ProjectAttachmentDAOInt projectAttachmentDao;

	@Override
	public Class<ProjectDTO> getDTOClass() {
		// TODO Auto-generated method stub
		return ProjectDTO.class;
	}

	@Override
	protected List<Predicate> getWhereClause(ProjectDTO dto, CriteriaBuilder builder, Root<ProjectDTO> qRoot) {

		List<Predicate> whereCondition =new ArrayList<Predicate>();
		
		if(isNotNull(dto.getProjectRoleId())) {
			whereCondition.add(builder.equal(qRoot.get("projectRoleId"), dto.getProjectRoleId()));
		}
		return whereCondition;
	}

	@Override
	protected void populate(ProjectDTO dto, UserContext userContext) {

		if (dto.getProjectRoleId() > 0 && dto.getProjectRoleId() != null) {
			ProjectRoleDTO roleNamne = projectRoleDao.findByPK(dto.getProjectRoleId(), userContext);
			dto.setProjectRoleName(roleNamne.getName());
		}
		
		

	}

	@Override
	public void delete(ProjectDTO dto, UserContext userContext) {
		if (dto.getImage() > 0 && dto.getImage() != null) {
			ProjectAttachmentDTO image = projectAttachmentDao.findByPK(dto.getImage(), userContext);
			projectAttachmentDao.delete(image, userContext);
		}
		super.delete(dto, userContext);
	}

}
