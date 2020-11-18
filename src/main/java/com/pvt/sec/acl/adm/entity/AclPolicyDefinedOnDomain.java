package com.pvt.sec.acl.adm.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.pvt.sec.rbac.entity.AppRole;



/**
 * The persistent class for the acl_policy_defined_on_domain database table.
 * 
 */
@Entity
@Table(name="acl_policy_defined_on_domain")
@NamedQuery(name="AclPolicyDefinedOnDomain.findAll", query="SELECT a FROM AclPolicyDefinedOnDomain a")
public class AclPolicyDefinedOnDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="is_principal")
	private Boolean isPrincipal;

	@Column(name="permission_assigned_mask_collection")
	private String permissionAssignedMaskCollection;

	//bi-directional many-to-one association to DomainClass
	@ManyToOne
	@JoinColumn(name="domain_id")
	private DomainClass domainClass;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role_id")
	private AppRole appRole;

	public AclPolicyDefinedOnDomain() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsPrincipal() {
		return this.isPrincipal;
	}

	public void setIsPrincipal(Boolean isPrincipal) {
		this.isPrincipal = isPrincipal;
	}

	public String getPermissionAssignedMaskCollection() {
		return this.permissionAssignedMaskCollection;
	}

	public void setPermissionAssignedMaskCollection(String permissionAssignedMaskCollection) {
		this.permissionAssignedMaskCollection = permissionAssignedMaskCollection;
	}

	public DomainClass getDomainClass() {
		return this.domainClass;
	}

	public void setDomainClass(DomainClass domainClass) {
		this.domainClass = domainClass;
	}

	public AppRole getAppRole() {
		return this.appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}

}