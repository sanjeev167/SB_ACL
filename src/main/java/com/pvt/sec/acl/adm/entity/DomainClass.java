package com.pvt.sec.acl.adm.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.type.TrueFalseType;

import java.util.List;


/**
 * The persistent class for the domain_class database table.
 * 
 */
@Entity
@Table(name="domain_class")
@NamedQuery(name="DomainClass.findAll", query="SELECT d FROM DomainClass d")
public class DomainClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="class_name")
	private String className;

	//bi-directional many-to-one association to AclPolicyDefinedOnDomain
	@OneToMany(mappedBy="domainClass", orphanRemoval =true )
	private List<AclPolicyDefinedOnDomain> aclPolicyDefinedOnDomains;

	//bi-directional many-to-one association to PermissionOnDomain
	@OneToMany(mappedBy="domainClass",orphanRemoval =true )
	private List<PermissionOnDomain> permissionOnDomains;

	public DomainClass() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<AclPolicyDefinedOnDomain> getAclPolicyDefinedOnDomains() {
		return this.aclPolicyDefinedOnDomains;
	}

	public void setAclPolicyDefinedOnDomains(List<AclPolicyDefinedOnDomain> aclPolicyDefinedOnDomains) {
		this.aclPolicyDefinedOnDomains = aclPolicyDefinedOnDomains;
	}

	public AclPolicyDefinedOnDomain addAclPolicyDefinedOnDomain(AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain) {
		getAclPolicyDefinedOnDomains().add(aclPolicyDefinedOnDomain);
		aclPolicyDefinedOnDomain.setDomainClass(this);

		return aclPolicyDefinedOnDomain;
	}

	public AclPolicyDefinedOnDomain removeAclPolicyDefinedOnDomain(AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain) {
		getAclPolicyDefinedOnDomains().remove(aclPolicyDefinedOnDomain);
		aclPolicyDefinedOnDomain.setDomainClass(null);

		return aclPolicyDefinedOnDomain;
	}

	public List<PermissionOnDomain> getPermissionOnDomains() {
		return this.permissionOnDomains;
	}

	public void setPermissionOnDomains(List<PermissionOnDomain> permissionOnDomains) {
		this.permissionOnDomains = permissionOnDomains;
	}

	public PermissionOnDomain addPermissionOnDomain(PermissionOnDomain permissionOnDomain) {
		getPermissionOnDomains().add(permissionOnDomain);
		permissionOnDomain.setDomainClass(this);

		return permissionOnDomain;
	}

	public PermissionOnDomain removePermissionOnDomain(PermissionOnDomain permissionOnDomain) {
		getPermissionOnDomains().remove(permissionOnDomain);
		permissionOnDomain.setDomainClass(null);

		return permissionOnDomain;
	}

}