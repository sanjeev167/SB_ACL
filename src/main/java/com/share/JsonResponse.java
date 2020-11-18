/**
 * 
 */
package com.share;

import java.util.List;
import java.util.Map;

import com.pvt.sec.acl.adm.dto.Aclrights;
import com.pvt.sec.acl.adm.dto.PermissionGridDto;

/**
 * @author Sanjeev
 *
 */
public class JsonResponse {
	private String recordId;
	private Object formObject;
	private boolean status;
	private String statusMsg;	
	private Map<String, String> fieldErrMsgMap;
	List<NameValue> comboList;
	List<PermissionGridDto> permissionGridDtoList;
	
	List<Aclrights> permissionListForGrid;
	
	private String editRoleId;
	private String editUserId;
	
	private String objectOwner;
	
	
	
	
	/**
	 * @return the permissionListForGrid
	 */
	public List<Aclrights> getPermissionListForGrid() {
		return permissionListForGrid;
	}
	/**
	 * @param permissionListForGrid the permissionListForGrid to set
	 */
	public void setPermissionListForGrid(List<Aclrights> permissionListForGrid) {
		this.permissionListForGrid = permissionListForGrid;
	}
	/**
	 * @return the permissionGridDtoList
	 */
	public List<PermissionGridDto> getPermissionGridDtoList() {
		return permissionGridDtoList;
	}
	/**
	 * @param permissionGridDtoList the permissionGridDtoList to set
	 */
	public void setPermissionGridDtoList(List<PermissionGridDto> permissionGridDtoList) {
		this.permissionGridDtoList = permissionGridDtoList;
	}
	/**
	 * @return the objectOwner
	 */
	public String getObjectOwner() {
		return objectOwner;
	}
	/**
	 * @param objectOwner the objectOwner to set
	 */
	public void setObjectOwner(String objectOwner) {
		this.objectOwner = objectOwner;
	}
	/**
	 * @return the editRoleId
	 */
	public String getEditRoleId() {
		return editRoleId;
	}
	/**
	 * @param editRoleId the editRoleId to set
	 */
	public void setEditRoleId(String editRoleId) {
		this.editRoleId = editRoleId;
	}
	/**
	 * @return the editUserId
	 */
	public String getEditUserId() {
		return editUserId;
	}
	/**
	 * @param editUserId the editUserId to set
	 */
	public void setEditUserId(String editUserId) {
		this.editUserId = editUserId;
	}
	/**
	 * @return the recordId
	 */
	public String getRecordId() {
		return recordId;
	}
	/**
	 * @param recordId the recordId to set
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public List<NameValue> getComboList() {
		return comboList;
	}
	public void setComboList(List<NameValue> comboList) {
		this.comboList = comboList;
	}
	/**
	 * @return the formObject
	 */
	public Object getFormObject() {
		return formObject;
	}
	/**
	 * @param formObject the formObject to set
	 */
	public void setFormObject(Object formObject) {
		this.formObject = formObject;
	}
	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	/**
	 * @return the statusMsg
	 */
	public String getStatusMsg() {
		return statusMsg;
	}
	/**
	 * @param statusMsg the statusMsg to set
	 */
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	/**
	 * @return the fieldErrMsgMap
	 */
	public Map<String, String> getFieldErrMsgMap() {
		return fieldErrMsgMap;
	}
	/**
	 * @param fieldErrMsgMap the fieldErrMsgMap to set
	 */
	public void setFieldErrMsgMap(Map<String, String> fieldErrMsgMap) {
		this.fieldErrMsgMap = fieldErrMsgMap;
	}
	
	
}


