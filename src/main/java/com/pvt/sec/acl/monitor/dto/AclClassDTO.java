/**
 * 
 */
package com.pvt.sec.acl.monitor.dto;

/**
 * @author Sanjeev
 *
 */
public class AclClassDTO {

	private Integer id;	
	private String classWithPkg;

	private Integer totalrecords;

	private String status = "ErrorFree";
	private String errMsg = "No record found";	

	public AclClassDTO() {}

	public AclClassDTO(Integer id, String classWithPkg, Integer totalrecords) {
		this.id = id;
		this.classWithPkg = classWithPkg;
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
	 * @return the classWithPkg
	 */
	public String getClassWithPkg() {
		return classWithPkg;
	}

	/**
	 * @param classWithPkg the classWithPkg to set
	 */
	public void setClassWithPkg(String classWithPkg) {
		this.classWithPkg = classWithPkg;
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
