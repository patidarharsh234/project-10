package com.rays.product;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.rays.common.BaseDTO;


@Entity
@Table(name = "ST_PRODUCT")//cahnge
public class ProductDTO extends BaseDTO{

@Column(name = "PRODUCTNAME" ,length = 50)
private String productName;

@Column(name = "PRODUCTID" ,length = 50)
private String productId;

@Column(name = "DATEMODIFIED" ,length = 50)
private Date dateModified;

@Column(name = "PRICE" ,length = 50)
private Long price;

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

@Column(name = "IMAGEID" ,length = 50)
private Long imageId;

@Column(name = "PAYMENTMODEID" ,length = 50)
private Long paymentModeID;


@Column(name = "PAYMENTMODENAME" ,length = 50)
private String paymentModeName;


	
	
	
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return "productName";
	}

	@Override
	public String getUniqueKey() {
		// TODO Auto-generated method stub
		return "productId";
	}

	@Override
	public String getUniqueValue() {
		// TODO Auto-generated method stub
		return productId;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "product Id is";
	}

	@Override
	public LinkedHashMap<String, String> orderBY() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("productName", "asc");
		return map;
		
	}

	@Override
	public LinkedHashMap<String, Object> uniqueKeys() {
		// TODO Auto-generated method stub
		return null;
	}

}
