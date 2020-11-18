/**
 * 
 */
package com.pvt.sec.env.dto;

/**
 * @author Sanjeev
 *
 */
public class MenuDTO {	
	 String id;
	 String name;
	 String parent_id;
	 String leafUrl;
	 String nodeType;
	 String nodeImgUrl;
	 
	public MenuDTO(String id, String name, String parent_id, String leafUrl, String nodeType, String nodeImgUrl) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.parent_id = parent_id;
		this.leafUrl = leafUrl;
		this.nodeType = nodeType;
		this.nodeImgUrl = nodeImgUrl;
	}
	public MenuDTO() {
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the parent_id
	 */
	public String getParent_id() {
		return parent_id;
	}
	/**
	 * @param parent_id the parent_id to set
	 */
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	/**
	 * @return the leafUrl
	 */
	public String getLeafUrl() {
		return leafUrl;
	}
	/**
	 * @param leafUrl the leafUrl to set
	 */
	public void setLeafUrl(String leafUrl) {
		this.leafUrl = leafUrl;
	}
	/**
	 * @return the nodeType
	 */
	public String getNodeType() {
		return nodeType;
	}
	/**
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	/**
	 * @return the nodeImgUrl
	 */
	public String getNodeImgUrl() {
		return nodeImgUrl;
	}
	/**
	 * @param nodeImgUrl the nodeImgUrl to set
	 */
	public void setNodeImgUrl(String nodeImgUrl) {
		this.nodeImgUrl = nodeImgUrl;
	}
	 
	 
	 
}//End of MenuDTO
