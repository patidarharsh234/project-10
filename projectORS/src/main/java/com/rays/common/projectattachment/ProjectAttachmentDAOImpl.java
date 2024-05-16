package com.rays.common.projectattachment;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.rays.common.BaseDAOImpl;
import com.rays.common.UserContext;



/**
 * @authorHarsh Patidar
 *
 */
@Repository
public class ProjectAttachmentDAOImpl extends BaseDAOImpl<ProjectAttachmentDTO> implements ProjectAttachmentDAOInt {

	@Override
	public Class<ProjectAttachmentDTO> getDTOClass() {
		return ProjectAttachmentDTO.class;
	}


	@Override
	protected List<Predicate> getWhereClause(ProjectAttachmentDTO dto, CriteriaBuilder builder, Root<ProjectAttachmentDTO> qRoot) {

		List<Predicate> whereCondition = new ArrayList<Predicate>();

		if (dto == null) {
			return whereCondition;
		}

		if (!isEmptyString(dto.getName())) {

			whereCondition.add(builder.like(qRoot.get("name"), dto.getName() + "%"));
		}

		if (!isEmptyString(dto.getDescription())) {

			whereCondition.add(builder.like(qRoot.get("description"),dto.getDescription() + "%"));
		}
		if (!isZeroNumber(dto.getId())) {
			
			whereCondition.add(builder.equal(qRoot.get("id"), dto.getId()));
		}

		return whereCondition;

	}

}
