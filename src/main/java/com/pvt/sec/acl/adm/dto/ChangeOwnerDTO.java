/**
 * 
 */
package com.pvt.sec.acl.adm.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author Sanjeev
 *
 */
public class ChangeOwnerDTO extends DomainDTO{

	@NotEmpty(message = "<br>SidType is required.")
	private String sidtype;
	
	@NotEmpty(message = "Role is required.")
	private String roleId;
		
	@NotEmpty(message = "Name is required.")
	private String oldUserName;	
	
	@NotEmpty(message = "User is required.")
	private String userId;
	
	@NotEmpty(message = "Name is required.")
	private String newUserName;

	/**
	 * @return the sidtype
	 */
	public String getSidtype() {
		return sidtype;
	}

	/**
	 * @param sidtype the sidtype to set
	 */
	public void setSidtype(String sidtype) {
		this.sidtype = sidtype;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the oldUserName
	 */
	public String getOldUserName() {
		return oldUserName;
	}

	/**
	 * @param oldUserName the oldUserName to set
	 */
	public void setOldUserName(String oldUserName) {
		this.oldUserName = oldUserName;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the newUserName
	 */
	public String getNewUserName() {
		return newUserName;
	}

	/**
	 * @param newUserName the newUserName to set
	 */
	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}
	
	
	
}
