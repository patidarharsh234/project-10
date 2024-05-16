package com.rays.dto;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.rays.common.BaseDTO;

public class EmployeeDto extends BaseDTO{

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public Long getWorkTiem() {
		return workTiem;
	}

	public void setWorkTiem(Long workTiem) {
		this.workTiem = workTiem;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getWorkProgress() {
		return workProgress;
	}

	public void setWorkProgress(String workProgress) {
		this.workProgress = workProgress;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	@Column(name = "FIRSTNAME",length=500)
	private String firstName;

	
	
	@Column(name = "LASTNAME",length=500)
	private String lastName;
	
	@Column(name = "Employee_ID",length=500)
	private String employeeId;

	@Column(name = "salary",length=500)
	private String salary;
	

	@Column(name = "workTiem")
	private Long workTiem;
	

	@Column(name = "joiningDate")
	private Date joiningDate;
	

	@Column(name = "workProgress",length=500)
	private String workProgress;
	

	@Column(name = "imageId")
	private Long imageId;
	
	
	@Column(name = "POSITION_NAME")
	private Long positionName;
	
	
	@Column(name = "POSITION_ID")
	private Long positionId;
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return firstName;
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
		return employeeId;
	}

	@Override
	public LinkedHashMap<String, String> orderBY() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<String, Object> uniqueKeys() {
		// TODO Auto-generated method stub
		return null;
	}



}
