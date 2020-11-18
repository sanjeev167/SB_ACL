/**
 * 
 */
package com.config.exception.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Sanjeev
 *
 */
public class CustomErrorResponse {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private boolean status=false;
	private LocalDateTime timestamp;
	private HttpStatus statusCode;
	private String message;
	private String path;
	private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

	public CustomErrorResponse(LocalDateTime timestamp, HttpStatus statusCode, String message,
			List<FieldErrorDTO> fieldErrors, String path) {
		super();
		this.timestamp = timestamp;
		this.statusCode = statusCode;
		this.message = message;
		this.fieldErrors = fieldErrors;
		this.path = path;
	}

	public CustomErrorResponse() {
		// TODO Auto-generated constructor stub
	}

	public CustomErrorResponse(LocalDateTime timestamp2, HttpStatus status2, String message2, String path2) {
		// TODO Auto-generated constructor stub
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
	 * @return the timestamp
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the statusCode
	 */
	public HttpStatus getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the fieldErrors
	 */
	public List<FieldErrorDTO> getFieldErrors() {
		return fieldErrors;
	}

	/**
	 * @param fieldErrors the fieldErrors to set
	 */
	public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	
	

}
