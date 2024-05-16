package com.rays.productpreload;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.rays.common.BaseDAOImpl;


@Repository
public class PaymentModePreloadDAOImp extends BaseDAOImpl<PaymentModePreloadDTO> implements PaymentModePreloadDAOInt{

	@Override
	public Class<PaymentModePreloadDTO> getDTOClass() {
		// TODO Auto-generated method stub
		return PaymentModePreloadDTO.class;
	}

	@Override
	protected List<Predicate> getWhereClause(PaymentModePreloadDTO dto, CriteriaBuilder builder,
			Root<PaymentModePreloadDTO> qRoot) {
		List<Predicate> whereCondition=new ArrayList<Predicate>();
		return whereCondition;
	}

}
