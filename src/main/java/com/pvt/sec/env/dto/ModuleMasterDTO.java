/**
 * 
 */
package com.pvt.sec.env.dto;

import javax.validation.constraints.NotEmpty;

import com.config.validation.classLevelValidator.UniqueWithParentId;
import com.pvt.sec.env.service.ModuleService;;


/**
 * @author Sanjeev
 *
 */
@UniqueWithParentId(parentId="departmentId", fieldName = "moduleName", service = ModuleService.class, message = "{module.unique.value.violation}",
id="id")
public class ModuleMasterDTO {
	
	private Integer id;
	private String departmentName;
	
	private String moduleNameUniqueness;
	
	@NotEmpty(message = "Select Department")
	private String departmentId;
	
	@NotEmpty(message = "Module is required.")		
	private String moduleName;
	
	private String actionMode;

	public String getActionMode() {
		return actionMode;
	}

	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	private Integer totalrecords;

	public ModuleMasterDTO(Integer id, String departmentName,String moduleName, Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.moduleName = moduleName;
		this.totalrecords = totalrecords;
	}

	public ModuleMasterDTO(Integer id, String departmentName, Integer departmentId, String moduleName) {
		this.id = id;
		this.departmentName = departmentName;		
		this.departmentId = departmentId+"";
		this.moduleName = moduleName;
	}

	public ModuleMasterDTO(Integer id, String departmentId, String moduleName) {
		this.id = id;			
		this.departmentId = departmentId;
		this.moduleName = moduleName;
	}
	
	//Will be utilized by @Valid
	public ModuleMasterDTO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	

	/**
	 * @return the moduleNameUniqueness
	 */
	public String getModuleNameUniqueness() {
		return moduleNameUniqueness;
	}

	/**
	 * @param moduleNameUniqueness the moduleNameUniqueness to set
	 */
	public void setModuleNameUniqueness(String moduleNameUniqueness) {
		this.moduleNameUniqueness = moduleNameUniqueness;
	}

	public Integer getTotalrecords() {
		return totalrecords;
	}

	public void setTotalrecords(Integer totalrecords) {
		this.totalrecords = totalrecords;
	}
	
	
	
}
