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

import com.pvt.sec.env.dto.ModuleMasterDTO;


/**
 * The persistent class for the module_master database table.
 * 
 */
@Entity
@Table(name="module_master")
@NamedQuery(name="ModuleMaster.findAll", query="SELECT m FROM ModuleMaster m")
@SqlResultSetMapping(
        name = "ModuleMasterDTOMapping",
        classes = @ConstructorResult(
                targetClass = ModuleMasterDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "departmentName"), 
                            @ColumnResult(name = "moduleName"),                             
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class ModuleMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to DepartmentMaster
	@ManyToOne
	@JoinColumn(name="department_id")
	private DepartmentMaster departmentMaster;

	//bi-directional many-to-one association to PageMaster
	@OneToMany(mappedBy="moduleMaster")
	private List<PageMaster> pageMasters;
	
	

	public ModuleMaster() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DepartmentMaster getDepartmentMaster() {
		return this.departmentMaster;
	}

	public void setDepartmentMaster(DepartmentMaster departmentMaster) {
		this.departmentMaster = departmentMaster;
	}

	public List<PageMaster> getPageMasters() {
		return this.pageMasters;
	}

	public void setPageMasters(List<PageMaster> pageMasters) {
		this.pageMasters = pageMasters;
	}

	public PageMaster addPageMaster(PageMaster pageMaster) {
		getPageMasters().add(pageMaster);
		pageMaster.setModuleMaster(this);

		return pageMaster;
	}

	public PageMaster removePageMaster(PageMaster pageMaster) {
		getPageMasters().remove(pageMaster);
		pageMaster.setModuleMaster(null);

		return pageMaster;
	}

	
	

}