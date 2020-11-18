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
public class OwnerPermissionDTO {
	
	@Min(value=1, message ="Please select a domain.")
	private String domainId;	
		
	@NotEmpty(message ="[ Please select a permission. ]")
	private String ownerPermissions;
	
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
	 * @return the ownerPermissions
	 */
	public String getOwnerPermissions() {
		return ownerPermissions;
	}

	/**
	 * @param ownerPermissions the ownerPermissions to set
	 */
	public void setOwnerPermissions(String ownerPermissions) {
		this.ownerPermissions = ownerPermissions;
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
