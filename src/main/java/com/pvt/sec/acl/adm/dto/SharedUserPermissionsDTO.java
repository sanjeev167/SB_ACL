/**
 * 
 */
package com.pvt.sec.acl.adm.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author Sanjeev
 *
 */
public class SharedUserPermissionsDTO extends DomainDTO{

	@NotEmpty(message = "<br>Sid-Type is required.")
	private String sidType;
	
	@NotEmpty(message = "User is required.")
	private String userId;
	
	
	private String userNameSelected;
	private String ifUserChanged;
	
	private int comingContextId;
    
	@NotEmpty(message = "<br>Please select a permission.")
	private String userSharedPermissions;
	
	
	
	/**
	 * @return the userSharedPermissions
	 */
	public String getUserSharedPermissions() {
		return userSharedPermissions;
	}

	/**
	 * @param userSharedPermissions the userSharedPermissions to set
	 */
	public void setUserSharedPermissions(String userSharedPermissions) {
		this.userSharedPermissions = userSharedPermissions;
	}

	/**
	 * @return the sidType
	 */
	public String getSidType() {
		return sidType;
	}

	/**
	 * @param sidType the sidType to set
	 */
	public void setSidType(String sidType) {
		this.sidType = sidType;
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
	 * @return the userNameSelected
	 */
	public String getUserNameSelected() {
		return userNameSelected;
	}

	/**
	 * @param userNameSelected the userNameSelected to set
	 */
	public void setUserNameSelected(String userNameSelected) {
		this.userNameSelected = userNameSelected;
	}

	/**
	 * @return the ifUserChanged
	 */
	public String getIfUserChanged() {
		return ifUserChanged;
	}

	/**
	 * @param ifUserChanged the ifUserChanged to set
	 */
	public void setIfUserChanged(String ifUserChanged) {
		this.ifUserChanged = ifUserChanged;
	}

	/**
	 * @return the comingContextId
	 */
	public int getComingContextId() {
		return comingContextId;
	}

	/**
	 * @param comingContextId the comingContextId to set
	 */
	public void setComingContextId(int comingContextId) {
		this.comingContextId = comingContextId;
	}
	
	
		
}
