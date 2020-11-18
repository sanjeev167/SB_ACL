package com.pvt.sec.rbac.entity;

import java.io.Serializable;
import javax.persistence.*;


import com.pvt.sec.env.entity.DepartmentMaster;
import com.pvt.sec.rbac.dto.AppGroupDTO;

import java.util.List;



/**
 * The persistent class for the app_group database table.
 * 
 */
@Entity
@Table(name="app_group")
@NamedQuery(name="AppGroup.findAll", query="SELECT a FROM AppGroup a")
@SqlResultSetMapping(
        name = "AppGroupDTOMapping",
        classes = @ConstructorResult(
                targetClass = AppGroupDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "departmentName"), 
                            @ColumnResult(name = "groupName"),                             
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class AppGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="group_name")
	private String groupName;

	//bi-directional many-to-one association to DepartmentMaster
	@ManyToOne
	@JoinColumn(name="department_id")
	private DepartmentMaster departmentMaster;

	//bi-directional many-to-one association to AppGroupRole
	@OneToMany(mappedBy="appGroup")
	private List<AppGroupRole> appGroupRoles;

	//bi-directional many-to-one association to UserGroup
	@OneToMany(mappedBy="appGroup")
	private List<UserGroup> userGroups;

	public AppGroup() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public DepartmentMaster getDepartmentMaster() {
		return this.departmentMaster;
	}

	public void setDepartmentMaster(DepartmentMaster departmentMaster) {
		this.departmentMaster = departmentMaster;
	}

	public List<AppGroupRole> getAppGroupRoles() {
		return this.appGroupRoles;
	}

	public void setAppGroupRoles(List<AppGroupRole> appGroupRoles) {
		this.appGroupRoles = appGroupRoles;
	}

	public AppGroupRole addAppGroupRole(AppGroupRole appGroupRole) {
		getAppGroupRoles().add(appGroupRole);
		appGroupRole.setAppGroup(this);

		return appGroupRole;
	}

	public AppGroupRole removeAppGroupRole(AppGroupRole appGroupRole) {
		getAppGroupRoles().remove(appGroupRole);
		appGroupRole.setAppGroup(null);

		return appGroupRole;
	}

	public List<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public UserGroup addUserGroup(UserGroup userGroup) {
		getUserGroups().add(userGroup);
		userGroup.setAppGroup(this);

		return userGroup;
	}

	public UserGroup removeUserGroup(UserGroup userGroup) {
		getUserGroups().remove(userGroup);
		userGroup.setAppGroup(null);

		return userGroup;
	}

}