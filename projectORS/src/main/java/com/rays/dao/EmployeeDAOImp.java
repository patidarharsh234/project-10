package com.rays.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.rays.common.BaseDAOImpl;
import com.rays.dto.EmployeeDto;

@Repository
public class EmployeeDAOImp extends BaseDAOImpl<EmployeeDto> implements EmployeeDAOInt{

	@Override
	public Class<EmployeeDto> getDTOClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Predicate> getWhereClause(EmployeeDto dto, CriteriaBuilder builder, Root<EmployeeDto> qRoot) {
		// TODO Auto-generated method stub
		return null;
	}

}
