package com.pvt.sec.acl.adm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the permission_context database table.
 * 
 */
@Entity
@Table(name="permission_context")
@NamedQuery(name="PermissionContext.findAll", query="SELECT p FROM PermissionContext p")
public class PermissionContext implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="is_base_context")
	private Boolean isBaseContext;

	private String name;

	//bi-directional many-to-one association to PermissionType
	@OneToMany(mappedBy="permissionContext")
	private List<PermissionType> permissionTypes;

	public PermissionContext() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsBaseContext() {
		return this.isBaseContext;
	}

	public void setIsBaseContext(Boolean isBaseContext) {
		this.isBaseContext = isBaseContext;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PermissionType> getPermissionTypes() {
		return this.permissionTypes;
	}

	public void setPermissionTypes(List<PermissionType> permissionTypes) {
		this.permissionTypes = permissionTypes;
	}

	public PermissionType addPermissionType(PermissionType permissionType) {
		getPermissionTypes().add(permissionType);
		permissionType.setPermissionContext(this);

		return permissionType;
	}

	public PermissionType removePermissionType(PermissionType permissionType) {
		getPermissionTypes().remove(permissionType);
		permissionType.setPermissionContext(null);

		return permissionType;
	}

}