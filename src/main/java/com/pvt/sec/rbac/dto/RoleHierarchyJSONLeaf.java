/**
 * 
 */
package com.pvt.sec.rbac.dto;

/**
 * @author Sanjeev
 *
 */
public class RoleHierarchyJSONLeaf {
	private String pId;	
	private String id;	
	/**
	 * @return the pId
	 */
	public String getpId() {
		return pId;
	}


	/**
	 * @param pId the pId to set
	 */
	public void setpId(String pId) {
		this.pId = pId;
	}


	private String head;
	private String contents;
	


	public RoleHierarchyJSONLeaf() {
		// TODO Auto-generated constructor stub
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
	 * @return the headName
	 */
	public String getHead() {
		return head;
	}


	/**
	 * @param headName the headName to set
	 */
	public void setHead(String head) {
		this.head = head;
	}


	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}


	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

}
