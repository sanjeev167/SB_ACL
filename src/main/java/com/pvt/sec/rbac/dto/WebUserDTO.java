/**
 * 
 */
package com.pvt.sec.rbac.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.pvt.sec.rbac.service.UserAccountService;
import com.config.validation.classLevelValidator.MatchPassword;
import com.config.validation.classLevelValidator.Unique;
/**
 * @author Sanjeev
 *
 */


@Unique(message = "{email.unique.value.violation}", fieldName = "userLoginId", id = "id", service = UserAccountService.class)
@MatchPassword(message = "Passwords don't match.", password = "password", passwordConf = "passwordConf")
public class WebUserDTO {
	private Long id;
	private String departmentName;
	private String categoryName;	
	private String emailUniqueness="Yes";
	
	private String authprovider="Application";
	
	private Character activeOrNot;
	//@NotEmpty(message = "Select App. Context")
	private String departmentNameId=3+"";
	
	
	//@NotEmpty(message = "User Category is required.")	
	private String categoryNameId=2+"";
	
	@NotEmpty(message = "User name is required.")	
	private String name;
	
	@NotEmpty(message = "User login is required.")
	@Email(message = "Invalid email")
	private String userLoginId;
	
	//@ValidPassword	
	@NotEmpty(message = "Password is required.")	
	private String password;
	
	//@NotEmpty(message = "Password confirm is required.")	
	private String passwordConf;

	private Integer totalrecords;

	//Will be used by grid
	public WebUserDTO(Long id, String departmentName,String categoryName,String name,String userLoginId,String authprovider,Character activeOrNot, Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.categoryName = categoryName;
		this.name=name;
		this.userLoginId=userLoginId;
		
		this.authprovider=authprovider;
		this.activeOrNot=activeOrNot;
		
		this.totalrecords = totalrecords;
	}

	//Will be used for fetching a single record
	public WebUserDTO(Long id, String departmentNameId, String categoryNameId,String name,String userLoginId,String password) {
		this.id = id;
		this.departmentNameId = departmentNameId;		
		this.categoryNameId = categoryNameId;
		this.name=name;
		this.userLoginId=userLoginId;
		this.password=password;		
	}

	
	
	//Will be utilized by @Valid
	public WebUserDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDepartmentNameId() {
		return departmentNameId;
	}

	public void setDepartmentNameId(String departmentNameId) {
		this.departmentNameId = departmentNameId;
	}

	public String getCategoryNameId() {
		return categoryNameId;
	}

	public void setCategoryNameId(String categoryNameId) {
		this.categoryNameId = categoryNameId;
	}

	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConf() {
		return passwordConf;
	}

	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}
	

	

	/**
	 * @return the authprovider
	 */
	public String getAuthprovider() {
		return authprovider;
	}

	/**
	 * @param authprovider the authprovider to set
	 */
	public void setAuthprovider(String authprovider) {
		this.authprovider = authprovider;
	}

	/**
	 * @return the activeOrNot
	 */
	public Character getActiveOrNot() {
		return activeOrNot;
	}

	/**
	 * @param activeOrNot the activeOrNot to set
	 */
	public void setActiveOrNot(Character activeOrNot) {
		this.activeOrNot = activeOrNot;
	}

	/**
	 * @return the emailUniqueness
	 */
	public String getEmailUniqueness() {
		return emailUniqueness;
	}

	/**
	 * @param emailUniqueness the emailUniqueness to set
	 */
	public void setEmailUniqueness(String emailUniqueness) {
		this.emailUniqueness = emailUniqueness;
	}

	public Integer getTotalrecords() {
		return totalrecords;
	}

	public void setTotalrecords(Integer totalrecords) {
		this.totalrecords = totalrecords;
	}
	

}
