/**
 * 
 */
package com.pvt.sec.acl.adm.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * @author Sanjeev
 *
 */
public class RolePermissionDTO {

	@Min(value=1, message ="Please select a domain.")
	private String domainId;
	
	@Min(value=1, message ="Please select a role.")
	private int roleId;
	
	
	@NotEmpty(message ="Please select a permission.")
	private String rolePermissions;
	
	private String policyId;

	/**
	 * @return the domainId
	 */
	public String getDomainId() {
		return domainId;
	}

	/**
	 * @param domainId the domainId to set
	 */
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the rolePermissions
	 */
	public String getRolePermissions() {
		return rolePermissions;
	}

	/**
	 * @param rolePermissions the rolePermissions to set
	 */
	public void setRolePermissions(String rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	/**
	 * @return the policyId
	 */
	public String getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	
	
}
