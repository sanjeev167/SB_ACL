package com.pvt.sec.env.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the tree_menu_type_master database table.
 * 
 */
@Entity
@Table(name="tree_menu_type_master")
@NamedQuery(name="TreeMenuTypeMaster.findAll", query="SELECT t FROM TreeMenuTypeMaster t")
public class TreeMenuTypeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="active_c", length=1)
	private String activeC;

	@Column(name="created_by")
	private Integer createdBy;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(length=50)
	private String name;

	@Column(name="updated_by")
	private Integer updatedBy;

	@Column(name="updated_on")
	private Timestamp updatedOn;

	

	//bi-directional many-to-one association to MenuManager
	@OneToMany(mappedBy="treeMenuTypeMaster")
	private List<MenuManager> menuManagers;

	public TreeMenuTypeMaster() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActiveC() {
		return this.activeC;
	}

	public void setActiveC(String activeC) {
		this.activeC = activeC;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	

	public List<MenuManager> getMenuManagers() {
		return this.menuManagers;
	}

	public void setMenuManagers(List<MenuManager> menuManagers) {
		this.menuManagers = menuManagers;
	}

	

}