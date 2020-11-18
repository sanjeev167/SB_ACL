package com.pvt.sec.acl.adm.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the permission_on_domain database table.
 * 
 */
@Entity
@Table(name="permission_on_domain")
@NamedQuery(name="PermissionOnDomain.findAll", query="SELECT p FROM PermissionOnDomain p")
public class PermissionOnDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="permission_mask_collection")
	private String permissionMaskCollection;

	//bi-directional many-to-one association to DomainClass
	@ManyToOne
	@JoinColumn(name="domain_id")
	private DomainClass domainClass;

	public PermissionOnDomain() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPermissionMaskCollection() {
		return this.permissionMaskCollection;
	}

	public void setPermissionMaskCollection(String permissionMaskCollection) {
		this.permissionMaskCollection = permissionMaskCollection;
	}

	public DomainClass getDomainClass() {
		return this.domainClass;
	}

	public void setDomainClass(DomainClass domainClass) {
		this.domainClass = domainClass;
	}

}