/**
 * 
 */
package com.pvt.sec.rbac.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import com.pvt.sec.rbac.service.UserAccountService;
import com.config.validation.classLevelValidator.MatchPassword;
import com.config.validation.classLevelValidator.Unique;
import com.config.validation.fieldLevelValidator.ValidPassword;
/*
 * @Size(min=5, max=10, message="Your name should be between 5 - 10 characters.")
    private String name;
 
    @Min(value=5, message="Please insert at least 5 characters")
    private String lastname;
 
    @NotNull(message="Please select a password")
    @Length(min=5, max=10, message="Password should be between 5 - 10 charactes")
    private String password;
     
    @Pattern(regexp="[0-9]+", message="Wrong zip!")
    private String zip;
     
    @Pattern(regexp=".+@.+\\..+", message="Wrong email!")
    private String email;
     
    @Range(min=18, message="You cannot subscribe if you are under 18 years old.")
    private String age;
 * 
 * */


/**
 * @author Sanjeev
 *
 */
@Unique(message = "{email.unique.value.violation}", fieldName = "userLoginId", id = "id", service = UserAccountService.class)
@MatchPassword(message = "Passwords don't match.", password = "password", passwordConf = "passwordConf")

public class AppAdminUserDTO {
	
	private Integer id;
	private String departmentName;
	private String categoryName;	
	private String emailUniqueness="Yes";
	
	@NotEmpty(message = "Select App. Context")
	private String departmentNameId;
	
	
	@NotEmpty(message = "User Category is required.")	
	private String categoryNameId;
	
	@NotEmpty(message = "User name is required.")	
	private String name;
	
	@NotEmpty(message = "User login is required.")
	@Email(message = "Invalid email")
	private String userLoginId;
	
	@ValidPassword	
	//@NotEmpty(message = "Password is required.")	
	private String password;
	
	//@NotEmpty(message = "Password confirm is required.")	
	private String passwordConf;

	private Integer totalrecords;

	//Will be used by grid
	public AppAdminUserDTO(Integer id, String departmentName,String categoryName,String name,String userLoginId, Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.categoryName = categoryName;
		this.name=name;
		this.userLoginId=userLoginId;
		this.totalrecords = totalrecords;
	}

	//Will be used for fetching a single record
	public AppAdminUserDTO(Integer id, String departmentNameId, String categoryNameId,String name,String userLoginId,String password) {
		this.id = id;
		this.departmentNameId = departmentNameId;		
		this.categoryNameId = categoryNameId;
		this.name=name;
		this.userLoginId=userLoginId;
		this.password=password;		
	}

	
	
	//Will be utilized by @Valid
	public AppAdminUserDTO() {}

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
