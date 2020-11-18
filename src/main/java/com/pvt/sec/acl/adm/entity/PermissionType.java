package com.pvt.sec.acl.adm.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the permission_type database table.
 * 
 */
@Entity
@Table(name="permission_type")
@NamedQuery(name="PermissionType.findAll", query="SELECT p FROM PermissionType p")
public class PermissionType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="is_base_permission")
	private Boolean isBasePermission;

	private Integer mask;

	private String name;

	//bi-directional many-to-one association to PermissionContext
	@ManyToOne
	@JoinColumn(name="permission_context_id")
	private PermissionContext permissionContext;

	public PermissionType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsBasePermission() {
		return this.isBasePermission;
	}

	public void setIsBasePermission(Boolean isBasePermission) {
		this.isBasePermission = isBasePermission;
	}

	public Integer getMask() {
		return this.mask;
	}

	public void setMask(Integer mask) {
		this.mask = mask;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PermissionContext getPermissionContext() {
		return this.permissionContext;
	}

	public void setPermissionContext(PermissionContext permissionContext) {
		this.permissionContext = permissionContext;
	}

}