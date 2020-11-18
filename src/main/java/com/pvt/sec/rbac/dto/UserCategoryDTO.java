/**
 * 
 */
package com.pvt.sec.rbac.dto;

import javax.validation.constraints.NotEmpty;

import com.config.validation.classLevelValidator.FirstChildUniqueWithParentId;
import com.pvt.sec.rbac.service.UserCategoryService;

/**
 * @author Sanjeev
 *
 */
@FirstChildUniqueWithParentId(parentId="departmentNameId", fieldName = "categoryName", service = UserCategoryService.class, message = "{userCategory.unique.value.violation}",
id="id")
public class UserCategoryDTO {
	private Integer id;
	private String departmentName;
	private String userCategoryNameUniqueness;
	@NotEmpty(message = "Select App. Context")
	private String departmentNameId;
	
	@NotEmpty(message = "User Category is required.")		
	private String categoryName;

	private Integer totalrecords;

	//Will be used by grid
	public UserCategoryDTO(Integer id, String departmentName,String categoryName, Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.categoryName = categoryName;
		this.totalrecords = totalrecords;
	}

	//Will be used while fetching recordId based role
	public UserCategoryDTO(Integer id, String departmentName, Integer departmentNameId, String categoryName) {
		this.id = id;
		this.departmentName = departmentName;		
		this.departmentNameId = departmentNameId+"";
		this.categoryName = categoryName;
	}

	public UserCategoryDTO(Integer id, String departmentNameId, String categoryName) {
		this.id = id;			
		this.departmentNameId = departmentNameId;
		this.categoryName = categoryName;
	}
	
	//Will be utilized by @Valid while validating form
	public UserCategoryDTO() {}

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
	 * @return the userCategoryNameUniqueness
	 */
	public String getUserCategoryNameUniqueness() {
		return userCategoryNameUniqueness;
	}

	/**
	 * @param userCategoryNameUniqueness the userCategoryNameUniqueness to set
	 */
	public void setUserCategoryNameUniqueness(String userCategoryNameUniqueness) {
		this.userCategoryNameUniqueness = userCategoryNameUniqueness;
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
