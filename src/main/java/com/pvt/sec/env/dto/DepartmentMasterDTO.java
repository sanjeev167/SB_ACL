package com.pvt.sec.env.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;


import com.config.validation.classLevelValidator.Unique;
import com.pvt.sec.env.service.DepartmentService;

@Unique(fieldName = "departmentName", service = DepartmentService.class, message = "{department.unique.value.violation}",id="id")
public class DepartmentMasterDTO {
	private Integer id;
	
	
	@NotEmpty(message ="Department is required.")
	private String departmentName;	
	
	private Integer totalrecords;
	private String status = "ErrorFree";
	private String errMsg = "No record found";	
	List<Integer> recordIdArray;	
	

	public DepartmentMasterDTO() {};
	//This will be used for finding single record which will not be used in the grid
	public DepartmentMasterDTO(Integer id, String departmentName) {
			this.id=id;
			this.departmentName=departmentName;			
			
			}

	public DepartmentMasterDTO(Integer id, String departmentName, Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;		
		this.totalrecords = totalrecords;
	}
	
	
	
	public List<Integer> getRecordIdArray() {
		return recordIdArray;
	}

	public void setRecordIdArray(List<Integer> recordIdArray) {
		this.recordIdArray = recordIdArray;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	public Integer getTotalrecords() {
		return this.totalrecords;
	}

	public void setTotalrecords(Integer totalrecords) {
		this.totalrecords = totalrecords;
	}

}