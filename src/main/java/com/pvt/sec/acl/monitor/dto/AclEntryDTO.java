/**
 * 
 */
package com.pvt.sec.acl.monitor.dto;

/**
 * @author Sanjeev
 *
 */
public class AclEntryDTO {
	
	private Integer id;	
	private Long acl_object_identity;
	private Integer ace_order;
	private Long sid;
	private Integer mask;
	private boolean granting;
	private boolean audit_success ;
	private boolean audit_failure ;

	private Integer totalrecords;

	private String status = "ErrorFree";
	private String errMsg = "No record found";

	
	public AclEntryDTO() {}

	public AclEntryDTO(Integer id, Long acl_object_identity,Integer ace_order,Long sid,Integer mask,boolean granting,
			boolean audit_success,boolean audit_failure, Integer totalrecords) {
		this.id = id;
		this.acl_object_identity = acl_object_identity;
		this.ace_order=ace_order;
		this.sid=sid;
		this.mask=mask;
		this.granting=granting;
		this.audit_success=audit_success;
		this.audit_failure=audit_failure;
		this.totalrecords = totalrecords;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the acl_object_identity
	 */
	public Long getAcl_object_identity() {
		return acl_object_identity;
	}

	/**
	 * @param acl_object_identity the acl_object_identity to set
	 */
	public void setAcl_object_identity(Long acl_object_identity) {
		this.acl_object_identity = acl_object_identity;
	}

	/**
	 * @return the ace_order
	 */
	public Integer getAce_order() {
		return ace_order;
	}

	/**
	 * @param ace_order the ace_order to set
	 */
	public void setAce_order(Integer ace_order) {
		this.ace_order = ace_order;
	}

	/**
	 * @return the sid
	 */
	public Long getSid() {
		return sid;
	}

	/**
	 * @param sid the sid to set
	 */
	public void setSid(Long sid) {
		this.sid = sid;
	}

	/**
	 * @return the mask
	 */
	public Integer getMask() {
		return mask;
	}

	/**
	 * @param mask the mask to set
	 */
	public void setMask(Integer mask) {
		this.mask = mask;
	}

	/**
	 * @return the granting
	 */
	public boolean isGranting() {
		return granting;
	}

	/**
	 * @param granting the granting to set
	 */
	public void setGranting(boolean granting) {
		this.granting = granting;
	}

	/**
	 * @return the audit_success
	 */
	public boolean isAudit_success() {
		return audit_success;
	}

	/**
	 * @param audit_success the audit_success to set
	 */
	public void setAudit_success(boolean audit_success) {
		this.audit_success = audit_success;
	}

	/**
	 * @return the audit_failure
	 */
	public boolean isAudit_failure() {
		return audit_failure;
	}

	/**
	 * @param audit_failure the audit_failure to set
	 */
	public void setAudit_failure(boolean audit_failure) {
		this.audit_failure = audit_failure;
	}

	/**
	 * @return the totalrecords
	 */
	public Integer getTotalrecords() {
		return totalrecords;
	}

	/**
	 * @param totalrecords the totalrecords to set
	 */
	public void setTotalrecords(Integer totalrecords) {
		this.totalrecords = totalrecords;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
	
	
}
