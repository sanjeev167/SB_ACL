/**
 * 
 */
package com.pvt.sec.rbac.dto;

import javax.validation.constraints.NotEmpty;

import com.pvt.sec.rbac.service.AppGroupService;
import com.config.validation.classLevelValidator.FirstChildUniqueWithParentId;

/**
 * @author Sanjeev
 *
 */
@FirstChildUniqueWithParentId(parentId="departmentNameId", fieldName = "groupName", service = AppGroupService.class, message = "{group.unique.value.violation}",
id="id")
public class AppGroupDTO {
	private Integer id;
	private String departmentName;
	private String appGroupNameUniqueness;
	
	
	@NotEmpty(message = "Select App. Context")
	private String departmentNameId;
	
	@NotEmpty(message = "Group name is required.")	
	private String groupName;

	private Integer totalrecords;

	//Will be used by grid
	public AppGroupDTO(Integer id, String departmentName,String groupName, Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.groupName = groupName;
		this.totalrecords = totalrecords;
	}

	//Will be used while fetching recordId based Group
	public AppGroupDTO(Integer id, String departmentName, Integer departmentNameId, String groupName) {
		this.id = id;
		this.departmentName = departmentName;		
		this.departmentNameId = departmentNameId+"";
		this.groupName = groupName;
	}

	public AppGroupDTO(Integer id, String departmentNameId, String groupName) {
		this.id = id;			
		this.departmentNameId = departmentNameId;
		this.groupName = groupName;
	}
	
	//Will be utilized by @Valid while validating form
	public AppGroupDTO() {}

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
	 * @return the GroupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param GroupName the GroupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	

	/**
	 * @return the appGroupNameUniqueness
	 */
	public String getAppGroupNameUniqueness() {
		return appGroupNameUniqueness;
	}

	/**
	 * @param appGroupNameUniqueness the appGroupNameUniqueness to set
	 */
	public void setAppGroupNameUniqueness(String appGroupNameUniqueness) {
		this.appGroupNameUniqueness = appGroupNameUniqueness;
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
