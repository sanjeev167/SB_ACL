/**
 * 
 */
package com.share.api_response;


import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * @author Sanjeev
 *
 */
public class ApiErrorsView {
	
	//This will hold the HTTPStats code
	private HttpStatus status;
	
	//Will hold 
	// [1] field name 
	// [2] rejected Value 
	// [3] Validation applied on the respective field
	// [4] Error message
	// [5] input required
	private String exMsg;
	private List<ApiFieldError> fieldErrors;	
	private List<ApiGlobalError> apiGlobalErrors;
	private List<String> errors;
	
	
	
	
public ApiErrorsView(HttpStatus status,String exMsg,List<String> errors) {	
		
		this.status=status;
		this.exMsg=exMsg;
		this.errors=errors;
	}
	

	public ApiErrorsView(HttpStatus status,String exMsg,List<ApiFieldError> fieldErrors,List<ApiGlobalError> apiGlobalErrors) {	
		
		this.status=status;
		this.exMsg=exMsg;
		this.fieldErrors=fieldErrors;
		this.apiGlobalErrors=apiGlobalErrors;
	}
	
	
	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the exMsg
	 */
	public String getExMsg() {
		return exMsg;
	}


	/**
	 * @param exMsg the exMsg to set
	 */
	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
	}


	/**
	 * @param fieldErrors the fieldErrors to set
	 */
	public void setFieldErrors(List<ApiFieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public ApiErrorsView(List<ApiFieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
		
	}

	

	public List<ApiFieldError> getFieldErrors() {
		return fieldErrors;
	}


	/**
	 * @return the apiGlobalErrors
	 */
	public List<ApiGlobalError> getApiGlobalErrors() {
		return apiGlobalErrors;
	}


	/**
	 * @param apiGlobalErrors the apiGlobalErrors to set
	 */
	public void setApiGlobalErrors(List<ApiGlobalError> apiGlobalErrors) {
		this.apiGlobalErrors = apiGlobalErrors;
	}


	public List<String> getErrors() {
		return errors;
	}


	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
	

	
}
