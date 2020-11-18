/**
 * 
 */
package com.pvt.sec.rbac.dto;

import javax.validation.constraints.NotEmpty;

import com.pvt.sec.rbac.service.AppRoleService;
import com.config.validation.classLevelValidator.FirstChildUniqueWithParentId;

/**
 * @author Sanjeev
 *
 */
@FirstChildUniqueWithParentId(parentId="departmentNameId", fieldName = "roleName", service = AppRoleService.class, message = "{role.unique.value.violation}",
id="id")
public class AppRoleDTO {
	private Integer id;
	private String departmentName;
	private String appRoleNameUniqueness;
	
	
	
	@NotEmpty(message = "Select App. Context")
	private String departmentNameId;
	
	@NotEmpty(message = "Role name is required.")		
	private String roleName;

	private Integer totalrecords;

	//Will be used by grid
	public AppRoleDTO(Integer id, String departmentName,String roleName, Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.roleName = roleName;
		this.totalrecords = totalrecords;
	}

	//Will be used while fetching recordId based role
	public AppRoleDTO(Integer id, String departmentName, Integer departmentNameId, String roleName) {
		this.id = id;
		this.departmentName = departmentName;		
		this.departmentNameId = departmentNameId+"";
		this.roleName = roleName;
	}

	public AppRoleDTO(Integer id, String departmentNameId, String roleName) {
		this.id = id;			
		this.departmentNameId = departmentNameId;
		this.roleName = roleName;
	}
	
	//Will be utilized by @Valid while validating form
	public AppRoleDTO() {}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the departmentId
	 */
	public String getDepartmentNameId() {
		return departmentNameId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentNameId(String departmentNameId) {
		this.departmentNameId = departmentNameId;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	

	/**
	 * @return the appRoleNameUniqueness
	 */
	public String getAppRoleNameUniqueness() {
		return appRoleNameUniqueness;
	}

	/**
	 * @param appRoleNameUniqueness the appRoleNameUniqueness to set
	 */
	public void setAppRoleNameUniqueness(String appRoleNameUniqueness) {
		this.appRoleNameUniqueness = appRoleNameUniqueness;
	}

	/**
	 * @return the totalrecords
	 */
	public Integer getTotalrecords() {
		return totalrecords;
	}

	/**
	 * @param totalrecords the totalrecords to set
	 */
	public void setTotalrecords(Integer totalrecords) {
		this.totalrecords = totalrecords;
	}
	
	

}
