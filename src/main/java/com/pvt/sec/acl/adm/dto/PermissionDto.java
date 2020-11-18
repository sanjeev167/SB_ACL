/**
 * 
 */
package com.pvt.sec.acl.adm.dto;

/**
 * @author Sanjeev
 *
 */
public class PermissionDto {
  
	private String read;
	private String write;
	private String create;
	private String delete;	
	private String administration;
	/**
	 * @return the read
	 */
	public String getRead() {
		return read;
	}
	/**
	 * @param read the read to set
	 */
	public void setRead(String read) {
		this.read = read;
	}
	/**
	 * @return the write
	 */
	public String getWrite() {
		return write;
	}
	/**
	 * @param write the write to set
	 */
	public void setWrite(String write) {
		this.write = write;
	}
	/**
	 * @return the create
	 */
	public String getCreate() {
		return create;
	}
	/**
	 * @param create the create to set
	 */
	public void setCreate(String create) {
		this.create = create;
	}
	/**
	 * @return the delete
	 */
	public String getDelete() {
		return delete;
	}
	/**
	 * @param delete the delete to set
	 */
	public void setDelete(String delete) {
		this.delete = delete;
	}
	/**
	 * @return the administration
	 */
	public String getAdministration() {
		return administration;
	}
	/**
	 * @param administration the administration to set
	 */
	public void setAdministration(String administration) {
		this.administration = administration;
	}
	
	
}
