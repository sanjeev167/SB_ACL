/**
 * 
 */
package com.config.exception.common;
import java.io.Serializable;

/**
 * @author Sanjeev
 *
 */
public class CustomBaseException extends Exception implements Serializable{
    /**
	 * This base exception will bind the caught exception  as well as its corresponding information in ExceptionInfo object 
	 */
	private static final long serialVersionUID = -1376523007527802758L;
	private ExceptionInfo exceptionInfo;//This is a reference variable of ExceptionInfo which will store the exception details of the catch exception.
    private Throwable wrappedException;//This is the exception that is to be thrown.
    
    public CustomBaseException(ExceptionInfo exceptionInfo,Throwable wrappedException) {
       super();
       this.exceptionInfo = exceptionInfo;
       this.wrappedException = wrappedException;
    }
	public ExceptionInfo getExceptionInfo() {
		// TODO Auto-generated method stub
		return exceptionInfo;
	}
	public Throwable getWrappedException() {
		// TODO Auto-generated method stub
		return wrappedException;
	}
}//End of CustomBaseException

