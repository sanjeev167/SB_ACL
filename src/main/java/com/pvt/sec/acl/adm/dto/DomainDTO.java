/**
 * 
 */
package com.pvt.sec.acl.adm.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author Sanjeev
 *
 */
public class DomainDTO {

	@NotEmpty(message = "Domain-Id is required.")
	private String id;
	
	@NotEmpty(message = "Domain-Name is required.")
	private String domainClassName;

	private String actionMode="add";
	
	
	
	
	
	
	
	/**
	 * @return the actionMode
	 */
	public String getActionMode() {
		return actionMode;
	}

	/**
	 * @param actionMode the actionMode to set
	 */
	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the domainClassName
	 */
	public String getDomainClassName() {
		return domainClassName;
	}

	/**
	 * @param domainClassName the domainClassName to set
	 */
	public void setDomainClassName(String domainClassName) {
		this.domainClassName = domainClassName;
	}
	
	
	
}
