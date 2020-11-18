package com.pvt.sec.rbac.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.pvt.sec.rbac.entity.RoleHierarchy;

import com.pvt.sec.env.entity.DepartmentMaster;
import com.pvt.sec.rbac.dto.AppRoleDTO;

import java.util.List;

/**
 * The persistent class for the app_role database table.
 * 
 */
@Entity
@Table(name = "app_role")
@NamedQuery(name = "AppRole.findAll", query = "SELECT a FROM AppRole a")
@SqlResultSetMapping(name = "AppRoleDTOMapping", classes = @ConstructorResult(targetClass = AppRoleDTO.class, columns = {
		@ColumnResult(name = "id", type = Integer.class), @ColumnResult(name = "departmentName"),
		@ColumnResult(name = "roleName"), @ColumnResult(name = "totalrecords", type = Integer.class), }))
public class AppRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "role_name")
	private String roleName;
	// bi-directional many-to-one association to WebAccessRights
	@OneToMany(mappedBy = "appRole")
	private List<WebAccessRights> webAccessRights;

	// bi-directional many-to-one association to WebAccessRights
	@OneToMany(mappedBy = "appRole")
	private List<PageAccess> pageAccesses;

	// bi-directional many-to-one association to AppGroupRole
	@OneToMany(mappedBy = "appRole")
	private List<AppGroupRole> appGroupRoles;

	// bi-directional many-to-one association to DepartmentMaster
	@ManyToOne
	@JoinColumn(name = "department_id")
	private DepartmentMaster departmentMaster;

	// bi-directional many-to-one association to RoleHierarchy
	@OneToMany(mappedBy = "appRole")
	private List<RoleHierarchy> roleHierarchies;

	public AppRole() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<PageAccess> getPageAccesses() {
		return this.pageAccesses;
	}

	public void setPageAccesses(List<PageAccess> pageAccesses) {
		this.pageAccesses = pageAccesses;
	}

	public PageAccess addPageAccess(PageAccess pageAccess) {
		getPageAccesses().add(pageAccess);
		pageAccess.setAppRole(this);

		return pageAccess;
	}

	public PageAccess removePageAccess(PageAccess pageAccess) {
		getPageAccesses().remove(pageAccess);
		pageAccess.setAppRole(null);
		return pageAccess;
	}

	/////////////////
	public List<AppGroupRole> getAppGroupRoles() {
		return this.appGroupRoles;
	}

	public void setAppGroupRoles(List<AppGroupRole> appGroupRoles) {
		this.appGroupRoles = appGroupRoles;
	}

	public AppGroupRole addAppGroupRole(AppGroupRole appGroupRole) {
		getAppGroupRoles().add(appGroupRole);
		appGroupRole.setAppRole(this);

		return appGroupRole;
	}

	public AppGroupRole removeAppGroupRole(AppGroupRole appGroupRole) {
		getAppGroupRoles().remove(appGroupRole);
		appGroupRole.setAppRole(null);

		return appGroupRole;
	}

	public DepartmentMaster getDepartmentMaster() {
		return this.departmentMaster;
	}

	public void setDepartmentMaster(DepartmentMaster departmentMaster) {
		this.departmentMaster = departmentMaster;
	}

	public List<RoleHierarchy> getRoleHierarchies() {
		return this.roleHierarchies;
	}

	public void setRoleHierarchies(List<RoleHierarchy> roleHierarchies) {
		this.roleHierarchies = roleHierarchies;
	}

	public List<WebAccessRights> getWebAccessRights() {
		return this.webAccessRights;
	}

	public void setWebAccessRights(List<WebAccessRights> webAccessRights) {
		this.webAccessRights = webAccessRights;
	}

	public WebAccessRights addWebAccessRights(WebAccessRights webAccessRights) {
		getWebAccessRights().add(webAccessRights);
		webAccessRights.setAppRole(this);

		return webAccessRights;
	}

	public WebAccessRights removeAccessRightsRbac(WebAccessRights webAccessRights) {
		getWebAccessRights().remove(webAccessRights);
		webAccessRights.setAppRole(null);

		return webAccessRights;
	}

}