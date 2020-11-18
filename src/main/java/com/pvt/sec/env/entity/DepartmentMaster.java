package com.pvt.sec.env.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.pvt.sec.env.dto.DepartmentMasterDTO;
import com.pvt.sec.rbac.entity.AppGroup;

import com.pvt.sec.rbac.entity.AppRole;
import com.pvt.sec.rbac.entity.RoleHierarchy;
import com.pvt.sec.rbac.entity.UserCategory;

/**
 * The persistent class for the department_master database table.
 * 
 */
@Entity
@Table(name = "department_master")
@NamedQuery(name = "DepartmentMaster.findAll", query = "SELECT d FROM DepartmentMaster d")
@SqlResultSetMapping(name = "DepartmentMasterDTOMapping", classes = @ConstructorResult(targetClass = DepartmentMasterDTO.class, columns = {
		@ColumnResult(name = "id", type = Integer.class), @ColumnResult(name = "departmentName"),
		@ColumnResult(name = "totalrecords", type = Integer.class), }))

public class DepartmentMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	// bi-directional many-to-one association to AppGroup
	@OneToMany(mappedBy = "departmentMaster")
	private List<AppGroup> appGroups;

	// bi-directional many-to-one association to AppRole
	@OneToMany(mappedBy = "departmentMaster")
	private List<AppRole> appRoles;

	// bi-directional many-to-one association to ModuleMaster
	@OneToMany(mappedBy = "departmentMaster")
	private List<ModuleMaster> moduleMasters;

	// bi-directional many-to-one association to UserCategory
	@OneToMany(mappedBy = "departmentMaster")
	private List<UserCategory> userCategories;

	// bi-directional many-to-one association to RoleHierarchy
	@OneToMany(mappedBy = "departmentMaster")
	private List<RoleHierarchy> roleHierarchies;

	public DepartmentMaster() {
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

	public List<AppGroup> getAppGroups() {
		return this.appGroups;
	}

	public void setAppGroups(List<AppGroup> appGroups) {
		this.appGroups = appGroups;
	}

	public AppGroup addAppGroup(AppGroup appGroup) {
		getAppGroups().add(appGroup);
		appGroup.setDepartmentMaster(this);

		return appGroup;
	}

	public AppGroup removeAppGroup(AppGroup appGroup) {
		getAppGroups().remove(appGroup);
		appGroup.setDepartmentMaster(null);

		return appGroup;
	}

	public List<AppRole> getAppRoles() {
		return this.appRoles;
	}

	public void setAppRoles(List<AppRole> appRoles) {
		this.appRoles = appRoles;
	}

	public AppRole addAppRole(AppRole appRole) {
		getAppRoles().add(appRole);
		appRole.setDepartmentMaster(this);

		return appRole;
	}

	public AppRole removeAppRole(AppRole appRole) {
		getAppRoles().remove(appRole);
		appRole.setDepartmentMaster(null);

		return appRole;
	}

	public List<ModuleMaster> getModuleMasters() {
		return this.moduleMasters;
	}

	public void setModuleMasters(List<ModuleMaster> moduleMasters) {
		this.moduleMasters = moduleMasters;
	}

	public ModuleMaster addModuleMaster(ModuleMaster moduleMaster) {
		getModuleMasters().add(moduleMaster);
		moduleMaster.setDepartmentMaster(this);

		return moduleMaster;
	}

	public ModuleMaster removeModuleMaster(ModuleMaster moduleMaster) {
		getModuleMasters().remove(moduleMaster);
		moduleMaster.setDepartmentMaster(null);

		return moduleMaster;
	}

	public List<UserCategory> getUserCategories() {
		return this.userCategories;
	}

	public void setUserCategories(List<UserCategory> userCategories) {
		this.userCategories = userCategories;
	}

	public UserCategory addUserCategory(UserCategory userCategory) {
		getUserCategories().add(userCategory);
		userCategory.setDepartmentMaster(this);

		return userCategory;
	}

	public UserCategory removeUserCategory(UserCategory userCategory) {
		getUserCategories().remove(userCategory);
		userCategory.setDepartmentMaster(null);

		return userCategory;
	}

	public List<RoleHierarchy> getRoleHierarchies() {
		return this.roleHierarchies;
	}

	public void setRoleHierarchies(List<RoleHierarchy> roleHierarchies) {
		this.roleHierarchies = roleHierarchies;
	}

	public RoleHierarchy addRoleHierarchy(RoleHierarchy roleHierarchy) {
		getRoleHierarchies().add(roleHierarchy);
		roleHierarchy.setDepartmentMaster(this);

		return roleHierarchy;
	}

	public RoleHierarchy removeRoleHierarchy(RoleHierarchy roleHierarchy) {
		getRoleHierarchies().remove(roleHierarchy);
		roleHierarchy.setDepartmentMaster(null);

		return roleHierarchy;
	}

}