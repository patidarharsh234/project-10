package com.rays.common;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Base class extended by all DTOs.
 * @authorHarsh Patidar
 */
@MappedSuperclass//like parent and not create table alg se
public abstract class BaseDTO implements Serializable, DropdownList, Comparable<BaseDTO> {

	/**
	 * Non Business primary key
	 */
	@Id
	@GeneratedValue(generator = "ncsPk")
	@GenericGenerator(name = "ncsPk", strategy = "native")
	@Column(name = "ID", unique = true, nullable = false)
	protected Long id;
	/**
	 * Contains USER ID who created this database record
	 */
	@Column(name = "CREATED_BY", length = 50)
	protected String createdBy;
	/**
	 * Contains USER ID who modified this database record
	 */
	@Column(name = "MODIFIED_BY", length = 50)
	protected String modifiedBy;
	/**
	 * Contains Created Timestamp of database record
	 */
	@CreatedDate
	@Column(name = "CREATED_DATETIME")
	protected Timestamp createdDatetime;
	/**
	 * Contains Modified Timestamp of database record
	 */
	@LastModifiedDate
	@Column(name = "MODIFIED_DATETIME")
	protected Timestamp modifiedDatetime;

	@Column(name = "ORG_ID")
	protected Long orgId = 0L;

	@Column(name = "ORG_NAME", length = 50)
	private String orgName;


	/**
	 * accessor
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(Timestamp l) {
		this.createdDatetime = l;
	}

	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

	
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	
//Extra->DropDownList	

	public String getKey() {
		return String.valueOf(id);
	}
	
	public int compareTo(BaseDTO next) {
		return getValue().compareTo(next.getValue());
	}

	
//abstract Methoed
	public abstract String getUniqueKey();
	
	public abstract String getUniqueValue();
	
	public abstract String getLabel();
	
	
	
	

	/**
	 * Order by attributes
	 * 
	 * @return
	 */
	public abstract LinkedHashMap<String, String> orderBY();

	/**
	 * Unique attributes
	 * 
	 * @return
	 */
	public abstract LinkedHashMap<String, Object> uniqueKeys();

	/**
	 * Apply organization filter
	 * 
	 * @return
	 */
	public boolean isGroupFilter() {
		return true;
	}

	
	
	
	@Override
	public String toString() {
		System.out.println("tostring method in base DAO");
		StringBuffer buffer = new StringBuffer();
		Method[] ms = this.getClass().getMethods();

		String mName = null;
		for (int i = 0; i < ms.length; i++) {
			mName = ms[i].getName();
			if (mName.startsWith("get")) {
				try {
					buffer.append("\n\t" + mName + " = " + ms[i].invoke(this, (Object[]) null));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return buffer.toString();
	}

}