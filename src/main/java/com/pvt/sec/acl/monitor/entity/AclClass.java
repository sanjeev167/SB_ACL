package com.pvt.sec.acl.monitor.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
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

import com.pvt.sec.acl.monitor.dto.AclClassDTO;


/**
 * The persistent class for the acl_class database table.
 * 
 */
@Entity
@Table(name="acl_class")
@NamedQuery(name="AclClass.findAll", query="SELECT a FROM AclClass a")
@SqlResultSetMapping(
        name = "AclClassDTOMapping",
        classes = @ConstructorResult(
                targetClass = AclClassDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "classWithPkg"),                            
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class AclClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="class")
	private String class_;

	//bi-directional many-to-one association to AclObjectIdentity
	@OneToMany(mappedBy="aclClass")
	private List<AclObjectIdentity> aclObjectIdentities;

	public AclClass() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	public List<AclObjectIdentity> getAclObjectIdentities() {
		return this.aclObjectIdentities;
	}

	public void setAclObjectIdentities(List<AclObjectIdentity> aclObjectIdentities) {
		this.aclObjectIdentities = aclObjectIdentities;
	}

	public AclObjectIdentity addAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		getAclObjectIdentities().add(aclObjectIdentity);
		aclObjectIdentity.setAclClass(this);

		return aclObjectIdentity;
	}

	public AclObjectIdentity removeAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		getAclObjectIdentities().remove(aclObjectIdentity);
		aclObjectIdentity.setAclClass(null);

		return aclObjectIdentity;
	}

}