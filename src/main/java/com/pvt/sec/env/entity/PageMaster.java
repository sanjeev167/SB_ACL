package com.pvt.sec.env.entity;


import java.io.Serializable;
import java.util.List;

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

import com.pvt.sec.env.dto.PageMasterDTO;




/**
 * The persistent class for the page_master database table.
 * 
 */
@Entity
@Table(name="page_master")
@NamedQuery(name="PageMaster.findAll", query="SELECT p FROM PageMaster p")
@SqlResultSetMapping(
        name = "PageMasterDTOMapping",
        classes = @ConstructorResult(
                targetClass = PageMasterDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "departmentName"), 
                            @ColumnResult(name = "moduleName"), 
                            @ColumnResult(name = "pageName"), 
                            @ColumnResult(name = "baseurl"), 
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class PageMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String baseurl;

	private String name;

	//bi-directional many-to-one association to ModuleMaster
	@ManyToOne
	@JoinColumn(name="module_id")
	private ModuleMaster moduleMaster;

	//bi-directional many-to-one association to MenuManager
	@OneToMany(mappedBy="pageMaster")
	private List<MenuManager> menuManagers;
		
		
	public PageMaster() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBaseurl() {
		return this.baseurl;
	}

	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ModuleMaster getModuleMaster() {
		return this.moduleMaster;
	}

	public void setModuleMaster(ModuleMaster moduleMaster) {
		this.moduleMaster = moduleMaster;
	}
	public List<MenuManager> getMenuManagers() {
		return this.menuManagers;
	}

	public void setMenuManagers(List<MenuManager> menuManagers) {
		this.menuManagers = menuManagers;
	}

	public MenuManager addMenuManager(MenuManager menuManager) {
		getMenuManagers().add(menuManager);
		menuManager.setPageMaster(this);

		return menuManager;
	}

	public MenuManager removeMenuManager(MenuManager menuManager) {
		getMenuManagers().remove(menuManager);
		menuManager.setPageMaster(null);

		return menuManager;
	}
}