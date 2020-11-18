/**
 * 
 */
package com.pvt.sec.acl.adm.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author Sanjeev
 *
 */
public class SharedRolePermissionsDTO extends DomainDTO{

	@NotEmpty(message = "<br>Sid-Type is required.")
	private String sidType;

	@NotEmpty(message = "Role is required.")
	private String roleId;	
	
	private String roleNameSelected;
	private String ifRoleChanged;
	
	@NotEmpty(message = "<br>No permissions selected.")
	private String roleSharedPermissions;

	Integer comingContextId;
	
	
	
	/**
	 * @return the comingContextId
	 */
	public Integer getComingContextId() {
		return comingContextId;
	}

	/**
	 * @param comingContextId the comingContextId to set
	 */
	public void setComingContextId(Integer comingContextId) {
		this.comingContextId = comingContextId;
	}

	/**
	 * @return the ifRoleChanged
	 */
	public String getIfRoleChanged() {
		return ifRoleChanged;
	}

	/**
	 * @param ifRoleChanged the ifRoleChanged to set
	 */
	public void setIfRoleChanged(String ifRoleChanged) {
		this.ifRoleChanged = ifRoleChanged;
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
	 * @return the roleNameSelected
	 */
	public String getRoleNameSelected() {
		return roleNameSelected;
	}

	/**
	 * @param roleNameSelected the roleNameSelected to set
	 */
	public void setRoleNameSelected(String roleNameSelected) {
		this.roleNameSelected = roleNameSelected;
	}

	/**
	 * @return the roleSharedPermissions
	 */
	public String getRoleSharedPermissions() {
		return roleSharedPermissions;
	}

	/**
	 * @param roleSharedPermissions the roleSharedPermissions to set
	 */
	public void setRoleSharedPermissions(String roleSharedPermissions) {
		this.roleSharedPermissions = roleSharedPermissions;
	}
}
