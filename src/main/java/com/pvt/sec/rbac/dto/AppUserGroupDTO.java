/**
 * 
 */
package com.pvt.sec.rbac.dto;

import javax.validation.constraints.NotEmpty;

import com.pvt.sec.rbac.service.AssignGroupToUserService;
import com.config.validation.classLevelValidator.FirstChildUniqueWithParentId;

/**
 * @author Sanjeev
 *
 */
@FirstChildUniqueWithParentId(parentId="groupNameId", fieldName = "userNameId", service = AssignGroupToUserService.class,
                    message = "{groupUser.unique.value.violation}",id="id")
public class AppUserGroupDTO {
	private Integer id;
	private String departmentName;
	private String groupName;
	private String categoryName;
	private String userName;
	private String appUserGroupNameUniqueness;
	
	
	@NotEmpty(message = "Select App. Context")
	private String departmentNameId;
	
	@NotEmpty(message = "Select a group")
	private String groupNameId;
	@NotEmpty(message = "Select user category")
	private String categoryNameId;
	
	@NotEmpty(message = "Select a user")
	private String userNameId;
	
	
	private Integer totalrecords;

	//Will be utilized by @Valid
	public AppUserGroupDTO() {}
	
	//Will be utilized by grid
	public AppUserGroupDTO(Integer id, String departmentName, String groupName,String categoryName, String userName, Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.groupName = groupName;
		this.categoryName=categoryName;
		this.userName = userName;
		this.totalrecords = totalrecords;
	}
	
	//Will be utilized by grid
		public AppUserGroupDTO(Integer id, String departmentNameId, String groupNameId, String categoryNameId,String userNameId) {
			this.id = id;
			this.departmentNameId = departmentNameId;
			this.groupNameId = groupNameId;
			this.categoryNameId=categoryNameId;
			this.userNameId = userNameId;
			
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
		 * @return the userName
		 */
		public String getUserName() {
			return userName;
		}

		/**
		 * @param userName the userName to set
		 */
		public void setUserName(String userName) {
			this.userName = userName;
		}

		

		/**
		 * @return the appUserGroupNameUniqueness
		 */
		public String getAppUserGroupNameUniqueness() {
			return appUserGroupNameUniqueness;
		}

		/**
		 * @param appUserGroupNameUniqueness the appUserGroupNameUniqueness to set
		 */
		public void setAppUserGroupNameUniqueness(String appUserGroupNameUniqueness) {
			this.appUserGroupNameUniqueness = appUserGroupNameUniqueness;
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
		 * @return the categoryName
		 */
		public String getCategoryName() {
			return categoryName;
		}

		/**
		 * @param categoryName the categoryName to set
		 */
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		/**
		 * @return the categoryNameId
		 */
		public String getCategoryNameId() {
			return categoryNameId;
		}

		/**
		 * @param categoryNameId the categoryNameId to set
		 */
		public void setCategoryNameId(String categoryNameId) {
			this.categoryNameId = categoryNameId;
		}

		/**
		 * @return the userNameId
		 */
		public String getUserNameId() {
			return userNameId;
		}

		/**
		 * @param userNameId the userNameId to set
		 */
		public void setUserNameId(String userNameId) {
			this.userNameId = userNameId;
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
