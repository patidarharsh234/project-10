package com.rays.common.projectattachment;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.rays.common.BaseDTO;

/**
 * Contains attached file information and data
 * 
 * @authorHarsh Patidar
 */

@MappedSuperclass
public class ProjectAttachmentBaseDTO extends BaseDTO {

	/**
	 * Contains name of file
	 */
	@Column(name = "NAME", length = 100)
	protected String name = null;

	/**
	 * Contains MIME type of file
	 */
	@Column(name = "TYPE", length = 100)
	protected String type = null;

	/**
	 * Contains file description
	 */
	@Column(name = "DESCRIPTION", length = 500)
	protected String description = null;

	/**
	 * Contains use id who uploaded this file
	 */
	@Column(name = "PROJECT_ID")
	protected Long projectId = null;





	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getValue() {
		return name + "(" + type + ")";
	}




	//sayd list late us vakt esko use kara hoga.
	@Override
	public LinkedHashMap<String, String> orderBY() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("name", "asc");
		return map;
	}

	@Override
	public LinkedHashMap<String, Object> uniqueKeys() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		// map.put("name", name);
		return map;
	}

	@Override
	public String getUniqueKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUniqueValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}