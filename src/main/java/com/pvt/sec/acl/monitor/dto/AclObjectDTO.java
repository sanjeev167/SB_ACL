/**
 * 
 */
package com.pvt.sec.acl.monitor.dto;

/**
 * @author Sanjeev
 *
 */
public class AclObjectDTO {
	
	private Integer id;	
	private Long object_id_class;	
	private Long object_id_identity;
	private Long parent_object=0L;	
	private Long owner_sid;	
	
	private Boolean entries_inheriting;
	
	private Integer totalrecords;
	private String status = "ErrorFree";
	private String errMsg = "No record found";


	public AclObjectDTO() {	}

	public AclObjectDTO(Integer id, Long object_id_class,Long object_id_identity,Long parent_object,Long owner_sid,Boolean entries_inheriting, Integer totalrecords) {
		this.id = id;
		this.object_id_class = object_id_class;
		
		this.object_id_identity = object_id_identity;
		this.parent_object = parent_object;
		this.owner_sid = owner_sid;
		
		this.entries_inheriting = entries_inheriting;		
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
	 * @return the object_id_class
	 */
	public Long getObject_id_class() {
		return object_id_class;
	}

	/**
	 * @param object_id_class the object_id_class to set
	 */
	public void setObject_id_class(Long object_id_class) {
		this.object_id_class = object_id_class;
	}

	/**
	 * @return the object_id_identity
	 */
	public Long getObject_id_identity() {
		return object_id_identity;
	}

	/**
	 * @param object_id_identity the object_id_identity to set
	 */
	public void setObject_id_identity(Long object_id_identity) {
		this.object_id_identity = object_id_identity;
	}

	/**
	 * @return the parent_object
	 */
	public Long getParent_object() {
		return parent_object;
	}

	/**
	 * @param parent_object the parent_object to set
	 */
	public void setParent_object(Long parent_object) {
		this.parent_object = parent_object;
	}

	
	
	/**
	 * @return the owner_sid
	 */
	public Long getOwner_sid() {
		return owner_sid;
	}

	/**
	 * @param owner_sid the owner_sid to set
	 */
	public void setOwner_sid(Long owner_sid) {
		this.owner_sid = owner_sid;
	}

	/**
	 * @return the entries_inheriting
	 */
	public Boolean getEntries_inheriting() {
		return entries_inheriting;
	}

	/**
	 * @param entries_inheriting the entries_inheriting to set
	 */
	public void setEntries_inheriting(Boolean entries_inheriting) {
		this.entries_inheriting = entries_inheriting;
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
