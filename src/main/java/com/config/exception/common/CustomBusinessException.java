/**
 * 
 */
package com.config.exception.common;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.share.LoadPropertiesFileStatically;


/**
 * @author Sanjeev
 *
 */
public class CustomBusinessException extends Exception implements Serializable {
     
	private static final long serialVersionUID = -6513206704156645127L;
    private ExceptionInfo exceptionInfo;
    private Throwable wrappedException;
	private  String bussinessErrorKey="";
	
    	
	public CustomBusinessException(ExceptionInfo exceptionInfo, Exception e) {	
		CustomBusinessException businessException=(CustomBusinessException)e;		
		String businessError=LoadPropertiesFileStatically.getPropertyValue(businessException.getBussinessErrorKey());
		//super(exceptionInfo, wrappedException);
        this.exceptionInfo = exceptionInfo;
       // this.wrappedException = wrappedException;        
       Logger logger = LoggerFactory.getLogger(exceptionInfo.getExceptionGeneratingClass());          
       logger.error("BUSINESS ERROR -->"+ "Error in Line no[" + exceptionInfo.getExceptionAtLineNo() + "] "
                    + "of method " + exceptionInfo.getMethodName() + "() of class " + exceptionInfo.getExceptionGeneratingClass() 
                    + " [Main Cause-->" + businessError + "]");
	}

	public CustomBusinessException(String bussinessErrorKey) {		
		this.bussinessErrorKey=bussinessErrorKey;
	}

	/**
	 * @return the bussinessErrorKey
	 */
	public String getBussinessErrorKey() {
		return bussinessErrorKey;
	}


	/**
	 * @param bussinessErrorKey the bussinessErrorKey to set
	 */
	public void setBussinessErrorKey(String bussinessErrorKey) {
		this.bussinessErrorKey = bussinessErrorKey;
	}		
	
	
}
