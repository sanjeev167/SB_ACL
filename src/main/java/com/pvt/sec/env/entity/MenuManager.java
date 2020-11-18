package com.pvt.sec.env.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.pvt.sec.env.dto.MenuManagerDTO;

/**
 * The persistent class for the menu_manager database table.
 * 
 */
@Entity
@Table(name = "menu_manager")
@NamedQuery(name = "MenuManager.findAll", query = "SELECT m FROM MenuManager m")
@SqlResultSetMapping(
        name = "MenuManagerDTOMapping",
        classes = @ConstructorResult(
                targetClass = MenuManagerDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                	    @ColumnResult(name = "departmentMasterName", type = String.class), 
                	    @ColumnResult(name = "treeMenuTypeName", type = String.class), 
                	    @ColumnResult(name = "nodeType", type = String.class), 
                	    @ColumnResult(name = "menuManagerParentNodeName", type = String.class), 
                	    @ColumnResult(name = "nodeName", type = String.class), 
                	    @ColumnResult(name = "imgUrl", type = String.class),                            
                	    @ColumnResult(name = "pageBaseUrl", type = String.class), 
                	    @ColumnResult(name = "activeC", type = String.class),                             
                	    @ColumnResult(name = "totalrecords", type = Integer.class)
                            }))

public class MenuManager implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "active_c", length = 1)
	private String activeC;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_on")
	private Time createdOn;

	@Column(name = "img_url", length = 50)
	private String imgUrl;

	@Column(name = "node_name", length = 50)
	private String nodeName;

	@Column(name = "node_type", length = 1)
	private String nodeType;

	@Column(name = "updated_by")
	private Integer updatedBy;

	@Column(name = "updated_on")
	private Time updatedOn;

	// bi-directional many-to-one association to MenuManager
	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "parent_node_id")
	private MenuManager parentNodeManager;

	// bi-directional many-to-one association to MenuManager
	@OneToMany(mappedBy = "parentNodeManager")
	private List<MenuManager> childNodeManagerList;

	// bi-directional many-to-one association to TreeMenuTypeMaster
	@ManyToOne//(cascade = { CascadeType.ALL })
	@JoinColumn(name = "department_id")
	private DepartmentMaster departmentMaster;
	// bi-directional many-to-one association to TreeMenuTypeMaster
	@ManyToOne//(cascade = { CascadeType.ALL })
	@JoinColumn(name = "tree_menu_type_id")
	private TreeMenuTypeMaster treeMenuTypeMaster;

	// bi-directional many-to-one association to TreeMenuTypeMaster
	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "page_id")
	private PageMaster pageMaster;

	public MenuManager() {
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
	public Time getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Time createdOn) {
		this.createdOn = createdOn;
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
	public Time getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @param updatedOn the updatedOn to set
	 */
	public void setUpdatedOn(Time updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * @return the parentNodeManager
	 */
	public MenuManager getParentNodeManager() {
		return parentNodeManager;
	}

	/**
	 * @param parentNodeManager the parentNodeManager to set
	 */
	public void setParentNodeManager(MenuManager parentNodeManager) {
		this.parentNodeManager = parentNodeManager;
	}

	/**
	 * @return the childNodeManagerList
	 */
	public List<MenuManager> getChildNodeManagerList() {
		return childNodeManagerList;
	}

	/**
	 * @param childNodeManagerList the childNodeManagerList to set
	 */
	public void setChildNodeManagerList(List<MenuManager> childNodeManagerList) {
		this.childNodeManagerList = childNodeManagerList;
	}

	/**
	 * @return the treeMenuTypeMaster
	 */
	public TreeMenuTypeMaster getTreeMenuTypeMaster() {
		return treeMenuTypeMaster;
	}

	/**
	 * @param treeMenuTypeMaster the treeMenuTypeMaster to set
	 */
	public void setTreeMenuTypeMaster(TreeMenuTypeMaster treeMenuTypeMaster) {
		this.treeMenuTypeMaster = treeMenuTypeMaster;
	}

	/**
	 * @return the pageMaster
	 */
	public PageMaster getPageMaster() {
		return pageMaster;
	}

	/**
	 * @param pageMaster the pageMaster to set
	 */
	public void setPageMaster(PageMaster pageMaster) {
		this.pageMaster = pageMaster;
	}

	/**
	 * @return the departmentMaster
	 */
	public DepartmentMaster getDepartmentMaster() {
		return departmentMaster;
	}

	/**
	 * @param departmentMaster the departmentMaster to set
	 */
	public void setDepartmentMaster(DepartmentMaster departmentMaster) {
		this.departmentMaster = departmentMaster;
	}

}