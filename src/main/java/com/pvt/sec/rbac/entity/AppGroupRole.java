package com.pvt.sec.rbac.entity;

import java.io.Serializable;
import javax.persistence.*;


import com.pvt.sec.rbac.dto.AppGroupRoleDTO;


/**
 * The persistent class for the app_group_role database table.
 * 
 */
@Entity
@Table(name="app_group_role")
@NamedQuery(name="AppGroupRole.findAll", query="SELECT a FROM AppGroupRole a")
@SqlResultSetMapping(
        name = "AppGroupRoleDTOMapping",
        classes = @ConstructorResult(
                targetClass = AppGroupRoleDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "departmentName"), 
                            @ColumnResult(name = "groupName"), 
                            @ColumnResult(name = "roleName"), 
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class AppGroupRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to AppGroup
	@ManyToOne
	@JoinColumn(name="app_group_id")
	private AppGroup appGroup;

	//bi-directional many-to-one association to AppRole
	@ManyToOne
	@JoinColumn(name="app_role_id")
	private AppRole appRole;

	public AppGroupRole() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AppGroup getAppGroup() {
		return this.appGroup;
	}

	public void setAppGroup(AppGroup appGroup) {
		this.appGroup = appGroup;
	}

	public AppRole getAppRole() {
		return this.appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}

}