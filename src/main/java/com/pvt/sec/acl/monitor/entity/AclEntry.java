package com.pvt.sec.acl.monitor.entity;

import java.io.Serializable;

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
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.pvt.sec.acl.monitor.dto.AclEntryDTO;


/**
 * The persistent class for the acl_entry database table.
 * 
 */
@Entity
@Table(name="acl_entry")
@NamedQuery(name="AclEntry.findAll", query="SELECT a FROM AclEntry a")
@SqlResultSetMapping(
        name = "AclEntryDTOMapping",
        classes = @ConstructorResult(
                targetClass = AclEntryDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "acl_object_identity", type=Long.class), 
                            
                            @ColumnResult(name = "ace_order", type=Integer.class), 
                            @ColumnResult(name = "sid", type=Long.class), 
                            @ColumnResult(name = "mask", type=Integer.class), 
                            @ColumnResult(name = "granting", type=Boolean.class), 
                            @ColumnResult(name = "audit_success", type=Boolean.class), 
                            @ColumnResult(name = "audit_failure", type=Boolean.class), 
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class AclEntry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="ace_order")
	private Integer aceOrder;

	@Column(name="audit_failure")
	private Boolean auditFailure;

	@Column(name="audit_success")
	private Boolean auditSuccess;

	private Boolean granting;

	private Integer mask;

	//bi-directional many-to-one association to AclObjectIdentity
	@ManyToOne
	@JoinColumn(name="acl_object_identity")
	private AclObjectIdentity aclObjectIdentityBean;

	//bi-directional many-to-one association to AclSid
	@ManyToOne
	@JoinColumn(name="sid")
	private AclSid aclSid;

	public AclEntry() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAceOrder() {
		return this.aceOrder;
	}

	public void setAceOrder(Integer aceOrder) {
		this.aceOrder = aceOrder;
	}

	public Boolean getAuditFailure() {
		return this.auditFailure;
	}

	public void setAuditFailure(Boolean auditFailure) {
		this.auditFailure = auditFailure;
	}

	public Boolean getAuditSuccess() {
		return this.auditSuccess;
	}

	public void setAuditSuccess(Boolean auditSuccess) {
		this.auditSuccess = auditSuccess;
	}

	public Boolean getGranting() {
		return this.granting;
	}

	public void setGranting(Boolean granting) {
		this.granting = granting;
	}

	public Integer getMask() {
		return this.mask;
	}

	public void setMask(Integer mask) {
		this.mask = mask;
	}

	public AclObjectIdentity getAclObjectIdentityBean() {
		return this.aclObjectIdentityBean;
	}

	public void setAclObjectIdentityBean(AclObjectIdentity aclObjectIdentityBean) {
		this.aclObjectIdentityBean = aclObjectIdentityBean;
	}

	public AclSid getAclSid() {
		return this.aclSid;
	}

	public void setAclSid(AclSid aclSid) {
		this.aclSid = aclSid;
	}

}