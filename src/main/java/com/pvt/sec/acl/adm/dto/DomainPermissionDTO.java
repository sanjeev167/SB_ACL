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
public class DomainPermissionDTO {

	@Min(value=1, message ="Please select a domain.")
	private String domainId;
	
	@NotEmpty(message ="Please select at least one permission.")
	private String totalPermission;
	
	private String contextId;
	
	
	
	
	/**
	 * @return the contextId
	 */
	public String getContextId() {
		return contextId;
	}
	/**
	 * @param contextId the contextId to set
	 */
	public void setContextId(String contextId) {
		this.contextId = contextId;
	}
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
	 * @return the totalPermission
	 */
	public String getTotalPermission() {
		return totalPermission;
	}
	/**
	 * @param totalPermission the totalPermission to set
	 */
	public void setTotalPermission(String totalPermission) {
		this.totalPermission = totalPermission;
	}
	
	
	
}
