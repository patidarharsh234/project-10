
package com.rays.project;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.rays.common.BaseDTO;
import com.rays.common.BaseForm;

public class ProjectForm extends BaseForm {

//	@NotEmpty(message = "please Enter WorkingEmployee")
	private String WorkingEmployee;

	public String getWorkingEmployee() {
		return WorkingEmployee;
	}

	public void setWorkingEmployee(String workingEmployee) {
		WorkingEmployee = workingEmployee;
	}

	
	@NotEmpty(message = "please Enter employeeName")
	private String employeeName;
	
	@NotEmpty(message = "please Enter employeeId")
	@Email
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


	@NotEmpty(message = "please Enter projectLead")
	private String projectLead;

//	@NotEmpty(message = "please Enter SpecialId")
//	@Email
	private String SpecialId;
	

	public String getSpecialId() {
		return SpecialId;
	}

	public void setSpecialId(String specialId) {
		SpecialId = specialId;
	}

	@NotNull(message = "please Enter projectFinishDate")
	private Date projectFinishDate;
	
	@NotEmpty(message = "please Enter pgender")
	private String pgender;
	
	@NotNull(message = "please Enter projectRoleId")
	@Min(1)
	private Long projectRoleId;

	private String projectRoleName;

	private Long image;



	public String getProjectLead() {
		return projectLead;
	}

	public void setProjectLead(String projectLead) {
		this.projectLead = projectLead;
	}



	public Long getProjectRoleId() {
		return projectRoleId;
	}

	public void setProjectRoleId(Long projectRoleId) {
		this.projectRoleId = projectRoleId;
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

	public String getPgender() {
		return pgender;
	}

	public void setPgender(String pgender) {
		this.pgender = pgender;
	}

	public String getProjectRoleName() {
		return projectRoleName;
	}

	public void setProjectRoleName(String projectRoleName) {
		this.projectRoleName = projectRoleName;
	}

	@Override
	public BaseDTO getDto() {

		ProjectDTO dto = initDTO(new ProjectDTO());
		dto.setWorkingEmployee(WorkingEmployee);
		dto.setEmployeeName(employeeName);
		dto.setEmployeeId(employeeId);
		dto.setProjectLead(projectLead);
		dto.setSpecialId(SpecialId);
		dto.setProjectFinishDate(projectFinishDate);
		dto.setPgender(pgender);
		dto.setProjectRoleId(projectRoleId);
		dto.setProjectRoleName(projectRoleName);// Update Replace Update Ki populate Methode.
		dto.setImage(image);//// Update Replace Update Ki populate Methode

		return dto;
	}
}
