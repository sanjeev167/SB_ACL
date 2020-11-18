/**
 * 
 */
package com.pvt.sec.rbac.dto;

import javax.validation.constraints.NotEmpty;

import com.pvt.sec.rbac.service.AssignRoleToGroupService;

import com.config.validation.classLevelValidator.FirstChildUniqueWithParentId;

/**
 * @author Sanjeev
 *
 */
@FirstChildUniqueWithParentId(parentId="groupNameId", fieldName = "roleNameId", service = AssignRoleToGroupService.class,
                    message = "{groupRole.unique.value.violation}",id="id")
public class AppGroupRoleDTO {
	private Integer id;
	private String departmentName;
	private String groupName;
	private String roleName;
	private String appGroupRoleNameUniqueness;
	@NotEmpty(message = "Select App. Context")
	private String departmentNameId;
	
	@NotEmpty(message = "Select a group")
	private String groupNameId;
	
	@NotEmpty(message = "Select a role")
	private String roleNameId;
	
	
	private Integer totalrecords;

	//Will be utilized by @Valid
	public AppGroupRoleDTO() {}
	
	//Will be utilized by grid
	public AppGroupRoleDTO(Integer id, String departmentName, String groupName, String roleName, Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.groupName = groupName;
		this.roleName = roleName;
		this.totalrecords = totalrecords;
	}
	
	//Will be utilized by grid
		public AppGroupRoleDTO(Integer id, String departmentNameId, String groupNameId, String roleNameId) {
			this.id = id;
			this.departmentNameId = departmentNameId;
			this.groupNameId = groupNameId;
			this.roleNameId = roleNameId;
			
		}

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
		 * @return the groupName
		 */
		public String getGroupName() {
			return groupName;
		}

		/**
		 * @param groupName the groupName to set
		 */
		public void setGroupName(String groupName) {
			this.groupName = groupName;
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
		 * @return the departmentNameId
		 */
		public String getDepartmentNameId() {
			return departmentNameId;
		}

		/**
		 * @param departmentNameId the departmentNameId to set
		 */
		public void setDepartmentNameId(String departmentNameId) {
			this.departmentNameId = departmentNameId;
		}

		/**
		 * @return the groupNameId
		 */
		public String getGroupNameId() {
			return groupNameId;
		}

		/**
		 * @param groupNameId the groupNameId to set
		 */
		public void setGroupNameId(String groupNameId) {
			this.groupNameId = groupNameId;
		}

		/**
		 * @return the roleNameId
		 */
		public String getRoleNameId() {
			return roleNameId;
		}

		/**
		 * @param roleNameId the roleNameId to set
		 */
		public void setRoleNameId(String roleNameId) {
			this.roleNameId = roleNameId;
		}

		
		
		
		/**
		 * @return the appGroupRoleNameUniqueness
		 */
		public String getAppGroupRoleNameUniqueness() {
			return appGroupRoleNameUniqueness;
		}

		/**
		 * @param appGroupRoleNameUniqueness the appGroupRoleNameUniqueness to set
		 */
		public void setAppGroupRoleNameUniqueness(String appGroupRoleNameUniqueness) {
			this.appGroupRoleNameUniqueness = appGroupRoleNameUniqueness;
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
