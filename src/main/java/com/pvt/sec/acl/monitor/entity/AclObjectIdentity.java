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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.pvt.sec.acl.monitor.dto.AclObjectDTO;

/**
 * The persistent class for the acl_object_identity database table.
 * 
 */
@Entity
@Table(name="acl_object_identity")
@NamedQuery(name="AclObjectIdentity.findAll", query="SELECT a FROM AclObjectIdentity a")

@SqlResultSetMapping(
        name = "AclObjectDTOMapping",
        classes = @ConstructorResult(
                targetClass = AclObjectDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "object_id_class", type=Long.class), 
                            
                            @ColumnResult(name = "object_id_identity", type=Long.class), 
                            @ColumnResult(name = "parent_object", type=Long.class), 
                            @ColumnResult(name = "owner_sid", type=Long.class), 
                            @ColumnResult(name = "entries_inheriting", type=Boolean.class), 
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))


public class AclObjectIdentity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="entries_inheriting")
	private Boolean entriesInheriting;

	@Column(name="object_id_identity")
	private Long objectIdIdentity;

	//bi-directional many-to-one association to AclEntry
	@OneToMany(mappedBy="aclObjectIdentityBean")
	private List<AclEntry> aclEntries;

	//bi-directional many-to-one association to AclClass
	@ManyToOne
	@JoinColumn(name="object_id_class")
	private AclClass aclClass;

	//bi-directional many-to-one association to AclObjectIdentity
	@ManyToOne
	@JoinColumn(name="parent_object")
	private AclObjectIdentity aclObjectIdentity;

	//bi-directional many-to-one association to AclObjectIdentity
	@OneToMany(mappedBy="aclObjectIdentity")
	private List<AclObjectIdentity> aclObjectIdentities;

	//bi-directional many-to-one association to AclSid
	@ManyToOne
	@JoinColumn(name="owner_sid")
	private AclSid aclSid;

	public AclObjectIdentity() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEntriesInheriting() {
		return this.entriesInheriting;
	}

	public void setEntriesInheriting(Boolean entriesInheriting) {
		this.entriesInheriting = entriesInheriting;
	}

	public Long getObjectIdIdentity() {
		return this.objectIdIdentity;
	}

	public void setObjectIdIdentity(Long objectIdIdentity) {
		this.objectIdIdentity = objectIdIdentity;
	}

	public List<AclEntry> getAclEntries() {
		return this.aclEntries;
	}

	public void setAclEntries(List<AclEntry> aclEntries) {
		this.aclEntries = aclEntries;
	}

	public AclEntry addAclEntry(AclEntry aclEntry) {
		getAclEntries().add(aclEntry);
		aclEntry.setAclObjectIdentityBean(this);

		return aclEntry;
	}

	public AclEntry removeAclEntry(AclEntry aclEntry) {
		getAclEntries().remove(aclEntry);
		aclEntry.setAclObjectIdentityBean(null);

		return aclEntry;
	}

	public AclClass getAclClass() {
		return this.aclClass;
	}

	public void setAclClass(AclClass aclClass) {
		this.aclClass = aclClass;
	}

	public AclObjectIdentity getAclObjectIdentity() {
		return this.aclObjectIdentity;
	}

	public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		this.aclObjectIdentity = aclObjectIdentity;
	}

	public List<AclObjectIdentity> getAclObjectIdentities() {
		return this.aclObjectIdentities;
	}

	public void setAclObjectIdentities(List<AclObjectIdentity> aclObjectIdentities) {
		this.aclObjectIdentities = aclObjectIdentities;
	}

	public AclObjectIdentity addAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		getAclObjectIdentities().add(aclObjectIdentity);
		aclObjectIdentity.setAclObjectIdentity(this);

		return aclObjectIdentity;
	}

	public AclObjectIdentity removeAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		getAclObjectIdentities().remove(aclObjectIdentity);
		aclObjectIdentity.setAclObjectIdentity(null);

		return aclObjectIdentity;
	}

	public AclSid getAclSid() {
		return this.aclSid;
	}

	public void setAclSid(AclSid aclSid) {
		this.aclSid = aclSid;
	}

}