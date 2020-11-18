package com.pvt.sec.acl.monitor.entity;

import java.io.Serializable;
import java.util.List;

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

import com.pvt.sec.acl.monitor.dto.AclSidDTO;


/**
 * The persistent class for the acl_sid database table.
 * 
 */
@Entity
@Table(name="acl_sid")
@NamedQuery(name="AclSid.findAll", query="SELECT a FROM AclSid a")
@SqlResultSetMapping(
        name = "AclSidDTOMapping",
        classes = @ConstructorResult(
                targetClass = AclSidDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                		    @ColumnResult(name = "principal", type = Boolean.class),                		
                            @ColumnResult(name = "sid"),                            
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class AclSid implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private Boolean principal;

	private String sid;

	//bi-directional many-to-one association to AclEntry
	@OneToMany(mappedBy="aclSid")
	private List<AclEntry> aclEntries;

	//bi-directional many-to-one association to AclObjectIdentity
	@OneToMany(mappedBy="aclSid")
	private List<AclObjectIdentity> aclObjectIdentities;

	public AclSid() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	public String getSid() {
		return this.sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public List<AclEntry> getAclEntries() {
		return this.aclEntries;
	}

	public void setAclEntries(List<AclEntry> aclEntries) {
		this.aclEntries = aclEntries;
	}

	public AclEntry addAclEntry(AclEntry aclEntry) {
		getAclEntries().add(aclEntry);
		aclEntry.setAclSid(this);

		return aclEntry;
	}

	public AclEntry removeAclEntry(AclEntry aclEntry) {
		getAclEntries().remove(aclEntry);
		aclEntry.setAclSid(null);

		return aclEntry;
	}

	public List<AclObjectIdentity> getAclObjectIdentities() {
		return this.aclObjectIdentities;
	}

	public void setAclObjectIdentities(List<AclObjectIdentity> aclObjectIdentities) {
		this.aclObjectIdentities = aclObjectIdentities;
	}

	public AclObjectIdentity addAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		getAclObjectIdentities().add(aclObjectIdentity);
		aclObjectIdentity.setAclSid(this);

		return aclObjectIdentity;
	}

	public AclObjectIdentity removeAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		getAclObjectIdentities().remove(aclObjectIdentity);
		aclObjectIdentity.setAclSid(null);

		return aclObjectIdentity;
	}

}