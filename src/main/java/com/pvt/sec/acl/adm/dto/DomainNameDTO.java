/**
 * 
 */
package com.pvt.sec.acl.adm.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author Sanjeev
 *
 */
public class DomainNameDTO {

	
	
	private String domainClassNameForEdit;
	
	@NotEmpty(message ="Domain Name is required.")
	private String domainName;
	
	
	
	
	
	
	/**
	 * @return the domainClassNameForEdit
	 */
	public String getDomainClassNameForEdit() {
		return domainClassNameForEdit;
	}
	/**
	 * @param domainClassNameForEdit the domainClassNameForEdit to set
	 */
	public void setDomainClassNameForEdit(String domainClassNameForEdit) {
		this.domainClassNameForEdit = domainClassNameForEdit;
	}
	/**
	 * @return the domainName
	 */
	public String getDomainName() {
		return domainName;
	}
	/**
	 * @param domainName the domainName to set
	 */
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	
	
		
}
