/**
 * 
 */
package com.pvt.sec.env.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.config.validation.classLevelValidator.ModuleRequiredWithTreeNodeType;
import com.config.validation.classLevelValidator.PageAsALeafRequiredWithTreeNodeType;
import com.config.validation.classLevelValidator.ParentNodeRequiredWithTreeNodeType;

/**
 * @author Sanjeev
 *
 */
@ParentNodeRequiredWithTreeNodeType(nodeType = "nodeType", parentNodeCount = "parentNodeCount", menuManagerParentNodeName = "menuManagerParentNodeName", menuManagerParentId = "menuManagerParentId", id = "id", message = "Parent node is required.")

@ModuleRequiredWithTreeNodeType(nodeType = "nodeType", moduleId = "moduleId", message = "Module is required.")

@PageAsALeafRequiredWithTreeNodeType(nodeType = "nodeType", pageMasterId = "pageMasterId", message = "Leaf is required.")

public class MenuManagerDTO {

	private Integer id;
	@NotNull()
	@NotBlank(message = "Application context is required.")
	private String departmentMasterId;

	private String departmentMasterName;
	@NotNull()
	@NotBlank(message = "Tree menu type is required.")
	private String treeMenuTypeId;
	private String treeMenuTypeName;

	private String nodeType;

	// @NotNull()
	// @NotBlank(message = "Select a parent node.")
	private String menuManagerParentId;
	private String menuManagerParentNodeName;

	@NotNull()
	@NotBlank(message = "Name is required.")
	private String nodeName;

	@NotNull()
	@NotBlank(message = "Image is required.")
	private String imgUrl;

	private String moduleId;
	private String pageMasterId;
	private String pageBaseUrl;

	private Integer createdBy;
	private Timestamp createdOn;
	private Integer updatedBy;
	private Timestamp updatedOn;
	private String activeC;
	private Integer totalrecords;

	private int parentNodeCount;

	// Will be utilized by @Valid
	public MenuManagerDTO() {
	}

	// Will be utilized by grid
	public MenuManagerDTO(Integer id, String departmentMasterName, String treeMenuTypeName, String nodeType,
			String menuManagerParentNodeName, String nodeName, String imgUrl, String pageBaseUrl, String activeC,
			int totalrecords) {
		this.id = id;
		this.departmentMasterName = departmentMasterName;
		this.treeMenuTypeName = treeMenuTypeName;
		this.nodeType = nodeType;
		this.menuManagerParentNodeName = "<font color='black'><strong>" + menuManagerParentNodeName
				+ "</strong></font>";

		if (nodeType.equals("N")) {
			if (nodeName.equals("Home")) {
				this.nodeName = "<font color='red'><strong>" + nodeName + "</strong></font>";
			} else {
				this.nodeName = "<font color='black'><strong>" + nodeName + "</strong></font>";
			}

			if (menuManagerParentNodeName.equals("None"))
				this.menuManagerParentNodeName = "<font color='blue'><strong>" + menuManagerParentNodeName
						+ "</strong></font>";
			if (menuManagerParentNodeName.equals("Home"))
				this.menuManagerParentNodeName = "<font color='red'><strong>" + menuManagerParentNodeName
						+ "</strong></font>";

		} else {
			this.nodeName = "<font color='green'><strong>" + nodeName + "</strong></font>";
		}
		this.imgUrl = "<i class='fa " + imgUrl + "'></i>";
		this.pageBaseUrl = pageBaseUrl;
		this.activeC = activeC;
		this.totalrecords = totalrecords;

	}

	// Will be utilized by getRecord after saving or update
	public MenuManagerDTO(Integer id, String departmentMasterName, String treeMenuTypeName, String nodeType,
			String menuManagerParentNodeName, String nodeName, String imgUrl, String pageBaseUrl, String activeC) {
		this.id = id;
		this.departmentMasterName = departmentMasterName;
		this.treeMenuTypeName = treeMenuTypeName;
		this.nodeType = nodeType;
		this.menuManagerParentNodeName = menuManagerParentNodeName;
		this.nodeName = nodeName;

		this.imgUrl = imgUrl;
		this.pageBaseUrl = pageBaseUrl;
		this.activeC = activeC;
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
	 * @return the departmentMasterId
	 */
	public String getDepartmentMasterId() {
		return departmentMasterId;
	}

	/**
	 * @param departmentMasterId the departmentMasterId to set
	 */
	public void setDepartmentMasterId(String departmentMasterId) {
		this.departmentMasterId = departmentMasterId;
	}

	/**
	 * @return the departmentMasterName
	 */
	public String getDepartmentMasterName() {
		return departmentMasterName;
	}

	/**
	 * @param departmentMasterName the departmentMasterName to set
	 */
	public void setDepartmentMasterName(String departmentMasterName) {
		this.departmentMasterName = departmentMasterName;
	}

	/**
	 * @return the treeMenuTypeId
	 */
	public String getTreeMenuTypeId() {
		return treeMenuTypeId;
	}

	/**
	 * @param treeMenuTypeId the treeMenuTypeId to set
	 */
	public void setTreeMenuTypeId(String treeMenuTypeId) {
		this.treeMenuTypeId = treeMenuTypeId;
	}

	/**
	 * @return the treeMenuTypeName
	 */
	public String getTreeMenuTypeName() {
		return treeMenuTypeName;
	}

	/**
	 * @param treeMenuTypeName the treeMenuTypeName to set
	 */
	public void setTreeMenuTypeName(String treeMenuTypeName) {
		this.treeMenuTypeName = treeMenuTypeName;
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
	 * @return the menuManagerParentId
	 */
	public String getMenuManagerParentId() {
		return menuManagerParentId;
	}

	/**
	 * @param menuManagerParentId the menuManagerParentId to set
	 */
	public void setMenuManagerParentId(String menuManagerParentId) {
		this.menuManagerParentId = menuManagerParentId;
	}

	/**
	 * @return the menuManagerParentNodeName
	 */
	public String getMenuManagerParentNodeName() {
		return menuManagerParentNodeName;
	}

	/**
	 * @param menuManagerParentNodeName the menuManagerParentNodeName to set
	 */
	public void setMenuManagerParentNodeName(String menuManagerParentNodeName) {
		this.menuManagerParentNodeName = menuManagerParentNodeName;
	}

	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return the pageMasterId
	 */
	public String getPageMasterId() {
		return pageMasterId;
	}

	/**
	 * @param pageMasterId the pageMasterId to set
	 */
	public void setPageMasterId(String pageMasterId) {
		this.pageMasterId = pageMasterId;
	}

	/**
	 * @return the pageBaseUrl
	 */
	public String getPageBaseUrl() {
		return pageBaseUrl;
	}

	/**
	 * @param pageBaseUrl the pageBaseUrl to set
	 */
	public void setPageBaseUrl(String pageBaseUrl) {
		this.pageBaseUrl = pageBaseUrl;
	}

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdOn
	 */
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedOn
	 */
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @param updatedOn the updatedOn to set
	 */
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
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
	 * @return the parentNodeCount
	 */
	public int getParentNodeCount() {
		return parentNodeCount;
	}

	/**
	 * @param parentNodeCount the parentNodeCount to set
	 */
	public void setParentNodeCount(int parentNodeCount) {
		this.parentNodeCount = parentNodeCount;
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

}
