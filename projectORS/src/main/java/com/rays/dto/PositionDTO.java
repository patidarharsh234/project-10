package com.rays.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

public class PositionDTO {
	
	
@Column(name = "name",length =500)
private String name;

@Column(name = "STATUS", length = 15)
private String status ;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

}
