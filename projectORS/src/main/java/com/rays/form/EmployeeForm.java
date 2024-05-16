package com.rays.form;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.rays.common.BaseDTO;
import com.rays.common.BaseForm;
import com.rays.dto.EmployeeDto;
import com.rays.dto.UserDTO;

public class EmployeeForm extends BaseForm {

	@NotEmpty(message = "firstName please enter first name")
	private String firstName;

	@NotEmpty(message = "lastName please enter first name")
	private String lastName;

	@NotEmpty(message = "salary please enter first name")
	private String salary;

	@NotNull(message = "workTiem please enter first name")
	private Long workTiem;

	public Long getWorkTiem() {
		return workTiem;
	}

	public void setWorkTiem(Long workTiem) {
		this.workTiem = workTiem;
	}

	@NotNull(message = "joiningDate please enter first name")
	private Date joiningDate;

	@NotEmpty(message = "workProgress please enter first name")
	private String workProgress;
	
	
	@NotEmpty(message = "workProgress please enter first name")
	private String positionName;
	
	@NotEmpty(message = "workProgress please enter first name")
	private String positionId;

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

	private Long imageId;
	
	
	@Override
	public BaseDTO getDto() {

		EmployeeDto dto = initDTO(new EmployeeDto());
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
	    dto.setSalary(salary);
		dto.setWorkTiem(workTiem);
		dto.setWorkProgress(workProgress);
       dto.setJoiningDate(joiningDate);
       dto.setImageId(imageId);
	    return dto;
	}


}
