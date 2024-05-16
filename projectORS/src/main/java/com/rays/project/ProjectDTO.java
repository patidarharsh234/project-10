package com.rays.project;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.rays.common.BaseDTO;

@Entity
@Table(name = "ST_PROJECT")
public class ProjectDTO extends BaseDTO {

	@Column(name = "PROJECT_WORKING_EMPLOYEE", length = 500)
	private String WorkingEmployee;

	@Column(name = "PROJECT_LEAD", length = 500)
	private String projectLead;
	
	
	@Column(name = "EMPLOYEE_NAME", length = 500)
	private String employeeName;
	
	@Column(name = "EMPLOYEE_ID", length = 500)
	private String employeeId;
	
	
	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "SPECIAL_ID")
	private String SpecialId;

	@Column(name = "PROJECT_FINISH_DATE")
	private Date projectFinishDate;

	@Column(name = "PGENDER",length = 500)
	private String pgender;
	
	@Column(name = "PROJECT_ROLE_ID")
	private Long projectRoleId;
	
	@Column(name = "PROJECT_ROLE_NAME", length = 500)
	private String projectRoleName;
	
	@Column(name = "Image_id")
	private Long image;

	
	public String getValue() {
		// TODO Auto-generated method stub
		return employeeName;
	}

	@Override
	public String getUniqueKey() {
		// TODO Auto-generated method stub
		return "employeeId";
	}

	@Override
	public String getUniqueValue() {
		// TODO Auto-generated method stub
		return employeeId;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "employeeId Id";
	}

	@Override
	public LinkedHashMap<String, String> orderBY() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("employeeName", "asc");
		return map;
	}

	@Override
	public LinkedHashMap<String, Object> uniqueKeys() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("SpecialId", SpecialId);
		return map;
	}

	
	


	public String getPgender() {
		return pgender;
	}

	public void setPgender(String pgender) {
		this.pgender = pgender;
	}

	public String getWorkingEmployee() {
		return WorkingEmployee;
	}

	public void setWorkingEmployee(String workingEmployee) {
		WorkingEmployee = workingEmployee;
	}

	public String getProjectLead() {
		return projectLead;
	}

	public void setProjectLead(String projectLead) {
		this.projectLead = projectLead;
	}


	public String getSpecialId() {
		return SpecialId;
	}

	public void setSpecialId(String specialId) {
		SpecialId = specialId;
	}

	public Long getProjectRoleId() {
		return projectRoleId;
	}

	public void setProjectRoleId(Long projectRoleId) {
		this.projectRoleId = projectRoleId;
	}

	public String getProjectRoleName() {
		return projectRoleName;
	}

	public void setProjectRoleName(String projectRoleName) {
		this.projectRoleName = projectRoleName;
	}

	public Date getProjectFinishDate() {
		return projectFinishDate;
	}

	public void setProjectFinishDate(Date projectFinishDate) {
		this.projectFinishDate = projectFinishDate;
	}

	public Long getImage() {
		return image;
	}

	public void setImage(Long image) {
		this.image = image;
	}
}

