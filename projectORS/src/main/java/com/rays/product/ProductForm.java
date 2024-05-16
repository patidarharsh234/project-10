package com.rays.product;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.UniqueElements;

import com.rays.common.BaseDTO;
import com.rays.common.BaseForm;

public class ProductForm extends BaseForm {
	
	
	@NotEmpty(message = "pls enter productName")
	private String productName;

	@NotEmpty(message = "pls enter productId")
	private String productId;

	@NotNull(message = "pls enter dateModified")
	private Date dateModified;

	@NotNull(message = "pls enter price")
	private Long price;


	@NotNull(message = "pls enter paymentModeID")
	@Min(1)
	private Long paymentModeID;


	private Long imageId;
	
	private String paymentModeName;


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public Date getDateModified() {
		return dateModified;
	}


	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}


	public Long getPrice() {
		return price;
	}


	public void setPrice(Long price) {
		this.price = price;
	}


	public Long getImageId() {
		return imageId;
	}


	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}


	public Long getPaymentModeID() {
		return paymentModeID;
	}


	public void setPaymentModeID(Long paymentModeID) {
		this.paymentModeID = paymentModeID;
	}


	public String getPaymentModeName() {
		return paymentModeName;
	}


	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}

	
	@Override
	public BaseDTO getDto() {
		
		ProductDTO dto=initDTO(new ProductDTO());
		dto.setProductName(productName);
		dto.setProductId(productId);
		dto.setDateModified(dateModified);
		dto.setPrice(price);
		dto.setImageId(imageId);
		dto.setPaymentModeID(paymentModeID);
		dto.setPaymentModeName(paymentModeName);
		return dto;
	}
}
