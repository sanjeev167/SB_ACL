/**
 * 
 */
package com.share.JsonResp;

import java.util.List;
import java.util.Map;

import com.share.NameValueM;


/**
 * @author Sanjeev
 *
 */
public class JsonRes {
	private String recordId;
	private Object formObject;
	private boolean status;
	private String statusMsg;	
	private Map<String, String> fieldErrMsgMap;
	List<NameValueM> comboList;
	
	
	
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
	public List<NameValueM> getComboList() {
		return comboList;
	}
	public void setComboList(List<NameValueM> comboList) {
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


