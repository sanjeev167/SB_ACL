/**
 * 
 */
package com.pvt.sec.acl.adm.dto;

import java.util.ArrayList;

import com.share.NameValue;

/**
 * @author Sanjeev
 *
 */
public class PermissionGridDto {

	private String domainPolicyId;
	private String domainId;
	private String domainName;
	private String ownerOrRole;
	
	private String editLink="<span class='permissionEditClass'><a  href='#'>Edit</a></span>";
	private String deleteLink="<a  class='permissionDeleteClass' href='#'>Delete</a>";
	
	private ArrayList<NameValue> permissionList=new ArrayList<NameValue>();

	
	
	
	
	/**
	 * @return the domainPolicyId
	 */
	public String getDomainPolicyId() {
		return domainPolicyId;
	}

	/**
	 * @param domainPolicyId the domainPolicyId to set
	 */
	public void setDomainPolicyId(String domainPolicyId) {
		this.domainPolicyId = domainPolicyId;
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

	/**
	 * @return the ownerOrRole
	 */
	public String getOwnerOrRole() {
		return ownerOrRole;
	}

	/**
	 * @param ownerOrRole the ownerOrRole to set
	 */
	public void setOwnerOrRole(String ownerOrRole) {
		this.ownerOrRole = ownerOrRole;
	}

	/**
	 * @return the editLink
	 */
	public String getEditLink() {
		return editLink;
	}

	/**
	 * @param editLink the editLink to set
	 */
	public void setEditLink(String editLink) {
		this.editLink = "<a class='permissionEditClass' href='id="+5+"'>Edit</a>";
	}

	/**
	 * @return the deleteLink
	 */
	public String getDeleteLink() {
		return deleteLink;
	}

	/**
	 * @param deleteLink the deleteLink to set
	 */
	public void setDeleteLink(String deleteLink) {
		
		this.deleteLink = "<a class='permissionDeleteClass' href='id="+5+"'>Delete</a>";
	}

	/**
	 * @return the permissionList
	 */
	public ArrayList<NameValue> getPermissionList() {
		return permissionList;
	}

	/**
	 * @param permissionList the permissionList to set
	 */
	public void setPermissionList(ArrayList<NameValue> permissionList) {
		this.permissionList = permissionList;
	}
	
	
    
}
