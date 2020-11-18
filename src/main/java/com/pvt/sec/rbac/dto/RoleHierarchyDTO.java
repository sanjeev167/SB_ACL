/**
 * 
 */
package com.pvt.sec.rbac.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.config.validation.classLevelValidator.ParentRoleHierarchyNodeIsRequired;


/**
 * @author Sanjeev
 *
 */

@ParentRoleHierarchyNodeIsRequired(parentNodeCount = "headNodeCount", parentName = "parentName", parentId = "parentId",id="id", message = "Parent node is required.")

public class RoleHierarchyDTO {

	private Integer id;
	
	@NotNull()
	@NotBlank(message = "Application context is required.")
	private String contextId;	
	private String contextName;
	

	//@NotNull()
	//@NotBlank(message = "Select a parent node.")
	private String parentId;	
	private String parentName;

	@NotNull()
	@NotBlank(message = "Child name is required.")	 
	private String childName; 
	private String childNameId;
	
	
	@NotNull()
	@NotBlank(message = "Associated role is required.")
	private String roleNameId;
	private String roleName;
	@NotNull()
	@NotBlank(message = "Child details is required.")
	private String contents;
	
	
    private String activeC;
	private Integer totalrecords;

	private int headNodeCount;

	// Will be utilized by @Valid
	public RoleHierarchyDTO() {
	}

	// Will be utilized by grid
	public RoleHierarchyDTO(Integer id, String contextName,String parentName, String childName,String parentId,String contents, String activeC,int totalrecords) {
		this.id = id;
		this.contextName=contextName;		
		this.parentName=parentName;		
	    this.childName=childName;
	    this.parentId=parentId;
	    this.contents=contents;
		this.activeC=activeC;
		this.totalrecords=totalrecords;

	}
	
	// Will be utilized while fetching a record
		public RoleHierarchyDTO(Integer id,String contextId, String parentId,String childName,String roleName,String roleNameId,
				String contents,String activeC) {
			this.id=id;
			this.contextId=contextId;
			this.parentId=parentId;	
			this.childName=childName;
			
			this.roleName=roleName;
			this.roleNameId=roleNameId;
			
			this.contents=contents;
			this.activeC=activeC;
		}
	
			/**
			 * @return the id
			 */
			public Integer getId() {
				return id;
			}

			/**
			 * @param id the id to set
			 */
			public void setId(Integer id) {
				this.id = id;
			}

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
			 * @return the contextName
			 */
			public String getContextName() {
				return contextName;
			}

			/**
			 * @param contextName the contextName to set
			 */
			public void setContextName(String contextName) {
				this.contextName = contextName;
			}

			/**
			 * @return the parentId
			 */
			public String getParentId() {
				return parentId;
			}

			/**
			 * @param parentId the parentId to set
			 */
			public void setParentId(String parentId) {
				this.parentId = parentId;
			}

			/**
			 * @return the parentName
			 */
			public String getParentName() {
				return parentName;
			}

			/**
			 * @param parentName the parentName to set
			 */
			public void setParentName(String parentName) {
				this.parentName = parentName;
			}

			

			/**
			 * @return the childNameId
			 */
			public String getChildNameId() {
				return childNameId;
			}

			/**
			 * @param childNameId the childNameId to set
			 */
			public void setChildNameId(String childNameId) {
				this.childNameId = childNameId;
			}

			/**
			 * @return the childName
			 */
			public String getChildName() {
				return childName;
			}

			/**
			 * @param childName the childName to set
			 */
			public void setChildName(String childName) {
				this.childName = childName;
			}
			/**
			 * @return the roleName
			 */
			public String getRoleName() {
				return roleName;
			}

			/**
			 * @param roleName the roleName to set
			 */
			public void setRoleName(String roleName) {
				this.roleName = roleName;
			}

			/**
			 * @return the roleNameId
			 */
			public String getRoleNameId() {
				return roleNameId;
			}

			/**
			 * @param roleNameId the roleNameId to set
			 */
			public void setRoleNameId(String roleNameId) {
				this.roleNameId = roleNameId;
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

			/**
			 * @return the activeC
			 */
			public String getActiveC() {
				return activeC;
			}

			/**
			 * @param activeC the activeC to set
			 */
			public void setActiveC(String activeC) {
				this.activeC = activeC;
			}

			/**
			 * @return the totalrecords
			 */
			public Integer getTotalrecords() {
				return totalrecords;
			}

			/**
			 * @param totalrecords the totalrecords to set
			 */
			public void setTotalrecords(Integer totalrecords) {
				this.totalrecords = totalrecords;
			}

			/**
			 * @return the headNodeCount
			 */
			public int getHeadNodeCount() {
				return headNodeCount;
			}

			/**
			 * @param headNodeCount the headNodeCount to set
			 */
			public void setHeadNodeCount(int headNodeCount) {
				this.headNodeCount = headNodeCount;
			}
			
			
}//End of RoleHierarchyDTO
