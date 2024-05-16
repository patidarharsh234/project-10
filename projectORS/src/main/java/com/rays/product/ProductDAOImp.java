package com.rays.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rays.common.BaseDAOImpl;
import com.rays.common.UserContext;
import com.rays.common.projectattachment.ProjectAttachmentDTO;
import com.rays.dto.ProjectRoleDTO;
import com.rays.productattachment.ProductAttachmentDAOInt;
import com.rays.productattachment.ProductAttachmentDTO;
import com.rays.productpreload.PaymentModePreloadDAOInt;
import com.rays.productpreload.PaymentModePreloadDTO;
import com.rays.project.ProjectDAOInt;
import com.rays.project.ProjectDTO;

@Repository
public class ProductDAOImp extends BaseDAOImpl<ProductDTO> implements ProductDAOint {

	@Autowired
	protected PaymentModePreloadDAOInt paymentModePreload; // change

	@Autowired
	protected ProductAttachmentDAOInt productAttchmentDao; // change

	@Override
	public Class<ProductDTO> getDTOClass() {
		// TODO Auto-generated method stub
		return ProductDTO.class; // change
	}

	@Override
	protected List<Predicate> getWhereClause(ProductDTO dto, CriteriaBuilder builder, Root<ProductDTO> qRoot) {
		List<Predicate> whrereCondition = new ArrayList<Predicate>();
		return whrereCondition;
	}

	@Override
	protected void populate(ProductDTO dto, UserContext userContext) { // change

		if (dto.getPaymentModeID() > 0 && dto.getPaymentModeID() != null) {
			PaymentModePreloadDTO roleNamne = paymentModePreload.findByPK(dto.getPaymentModeID(), userContext);
			dto.setPaymentModeName(roleNamne.getPaymentMode());
		}

		if (dto.getId() != null && dto.getId() > 0) {
			ProductDTO updateImageDto = findByPK(dto.getId(), userContext);
			dto.setImageId(updateImageDto.getImageId());
		}

	}

	@Override
	public void delete(ProductDTO dto, UserContext userContext) { // change
		if (dto.getImageId() > 0 && dto.getImageId() != null) {
			ProductAttachmentDTO image = productAttchmentDao.findByPK(dto.getImageId(), userContext);
			productAttchmentDao.delete(image, userContext);
		}
		super.delete(dto, userContext);
	}

}
