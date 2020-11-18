/**
 * 
 */
package com.share.api_response;

/**
 * @author Sanjeev
 *
 */
public class ApiGlobalError {
	
	private String globalErrorName;
	private String globalErrorMsg;

	public ApiGlobalError(String globalErrorName, String globalErrorMsg) {		
		
		this.globalErrorName=globalErrorName;
		this.globalErrorMsg=globalErrorMsg;
	}

	public String getGlobalErrorName() {
		return globalErrorName;
	}

	public void setGlobalErrorName(String globalErrorName) {
		this.globalErrorName = globalErrorName;
	}

	public String getGlobalErrorMsg() {
		return globalErrorMsg;
	}

	public void setGlobalErrorMsg(String globalErrorMsg) {
		this.globalErrorMsg = globalErrorMsg;
	}

	
	
}
