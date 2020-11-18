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
public class PermissionDefinitionDTO {	
	
	private int recordId;
	@Min(value=1, message ="Please select a permission context.")
	private int permissionContextId;
	@NotEmpty(message ="Please input permission label.")
	private String permissionLabel;	
	
	/**
	 * @return the recordId
	 */
	public int getRecordId() {
		return recordId;
	}
	/**
	 * @param recordId the recordId to set
	 */
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	/**
	 * @return the permissionContextId
	 */
	public int getPermissionContextId() {
		return permissionContextId;
	}
	/**
	 * @param permissionContextId the permissionContextId to set
	 */
	public void setPermissionContextId(int permissionContextId) {
		this.permissionContextId = permissionContextId;
	}
	/**
	 * @return the permissionLabel
	 */
	public String getPermissionLabel() {
		return permissionLabel;
	}
	/**
	 * @param permissionLabel the permissionLabel to set
	 */
	public void setPermissionLabel(String permissionLabel) {
		this.permissionLabel = permissionLabel;
	}
	
	
	
}
