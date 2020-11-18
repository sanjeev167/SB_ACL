/**
 * 
 */
package com.pvt.sec.acl.monitor.dto;

/**
 * @author Sanjeev
 *
 */
public class AclSidDTO {
	
	private Integer id;	
	private boolean principal;
	private String sid;
	private String status = "ErrorFree";
	private String errMsg = "No record found";
	
	private Integer totalrecords;	

	public AclSidDTO() {
	}

	public AclSidDTO(Integer id,boolean  principal,String sid, Integer totalrecords) {
		this.id = id;
		this.principal = principal;
		this.sid = sid;
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
	 * @return the principal
	 */
	public boolean isPrincipal() {
		return principal;
	}

	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	/**
	 * @return the sid
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * @param sid the sid to set
	 */
	public void setSid(String sid) {
		this.sid = sid;
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

}
