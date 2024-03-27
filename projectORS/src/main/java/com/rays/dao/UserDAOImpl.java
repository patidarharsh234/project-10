package com.rays.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rays.common.BaseDAOImpl;
import com.rays.common.UserContext;
import com.rays.common.attachment.AttachmentDAOInt;
import com.rays.common.attachment.AttachmentDTO;
import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;

/**
 * Contains User CRUD operations
 * 
 * @authorHarsh Patidar
 *
 */
@Repository
public class UserDAOImpl extends BaseDAOImpl<UserDTO> implements UserDAOInt {

	@Override
	public Class<UserDTO> getDTOClass() {
		return UserDTO.class;
	}

	@Override
	protected List<Predicate> getWhereClause(UserDTO dto, CriteriaBuilder builder, Root<UserDTO> qRoot) {
                  //critera.Predicate
		// Create where conditions
		List<Predicate> whereCondition = new ArrayList<Predicate>();

		if (!isEmptyString(dto.getFirstName())) {

			whereCondition.add(builder.like(qRoot.get("firstName"), dto.getFirstName() + "%"));
		}
		
		if(!isEmptyString(dto.getGender())) {
			whereCondition.add(builder.equal(qRoot.get("gender"), dto.getGender()));
		}

		if (!isEmptyString(dto.getRoleName())) {

			whereCondition.add(builder.like(qRoot.get("roleName"), dto.getRoleName() + "%"));
		}
		if (!isEmptyString(dto.getLoginId())) {

			whereCondition.add(builder.equal(qRoot.get("loginId"), dto.getLoginId()));
		}
		if (!isEmptyString(dto.getPassword())) {

			whereCondition.add(builder.equal(qRoot.get("password"), dto.getPassword()));
		}

		if (!isEmptyString(dto.getStatus())) {

			whereCondition.add(builder.equal(qRoot.get("status"), dto.getStatus()));
		}

		if (!isZeroNumber(dto.getRoleId())) {

			whereCondition.add(builder.equal(qRoot.get("roleId"), dto.getRoleId()));
		}

		if (isNotNull(dto.getDob())) {

			whereCondition.add(builder.equal(qRoot.get("dob"), dto.getDob()));
		}

		return whereCondition;
	}

	@Autowired
	RoleDAOInt roleDao;

	@Autowired
	AttachmentDAOInt attachmentDaoInt;

	// roleName_add_update &&_User_Ke_Update_Time_pr_ImageId_Upadte_KrvaRa.
	@Override
	protected void populate(UserDTO dto, UserContext userContext) {
		if (dto.getRoleId() != null && dto.getRoleId() > 0) {
			RoleDTO roleDto = roleDao.findByPK(dto.getRoleId(), userContext);
			dto.setRoleName(roleDto.getName());
		}
		if (dto.getId() != null && dto.getId() > 0) {
			UserDTO imageSetDto = findByPK(dto.getId(), userContext);
			dto.setImageId(imageSetDto.getImageId());
		}

	}

	// delete Dto + image delete
	@Override
	public void delete(UserDTO dto, UserContext userContext) {
		if (dto.getImageId() != null && dto.getImageId() > 0) {
			AttachmentDTO attachmentDto = attachmentDaoInt.findByPK(dto.getImageId(), userContext);
			attachmentDaoInt.delete(attachmentDto, userContext);
		}
		super.delete(dto, userContext);
	}

	
	
	//use in jwtUserDeatailsService
	@Override
	public UserDTO findByEmail(String attribute, String val, UserContext userContext) {
		UserDTO dto = findByUniqueKey(attribute, val, userContext);
		return dto;
	}

}
