/**
 * 
 */
package com.share.api_response;

/**
 * @author Sanjeev
 *
 */

public class ApiFieldError {
	private String field;// Field on which validation applied
	private String code;// Validation that has been applied
	private Object rejectedValue;// Value that was rejected
	private String errMsg;// what the error message is	

	public ApiFieldError(String field, String code, Object rejectedValue, String errMsg) {
		this.field = field;
		this.code = code;
		this.rejectedValue = rejectedValue;
		this.errMsg = errMsg;		
	}



	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the rejectedValue
	 */
	public Object getRejectedValue() {
		return rejectedValue;
	}

	/**
	 * @param rejectedValue the rejectedValue to set
	 */
	public void setRejectedValue(Object rejectedValue) {
		this.rejectedValue = rejectedValue;
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

	

}// End of ApiFieldError
