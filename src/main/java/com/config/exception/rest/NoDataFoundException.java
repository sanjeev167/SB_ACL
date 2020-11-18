/**
 * 
 */
package com.config.exception.rest;

/**
 * @author Sanjeev
 *
 */
public class NoDataFoundException extends RuntimeException {

	private String recordName;
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoDataFoundException(String recordName) {
        super("Record not found.");
        this.recordName=recordName;
    }

	/**
	 * @return the recordName
	 */
	public String getRecordName() {
		return recordName;
	}

	/**
	 * @param recordName the recordName to set
	 */
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	
	
}
