/**
 * 
 */
package com.config.exception.common;

import java.sql.SQLException;


/**
 * @ author ksanjeev
 * @ 22/7/2016
 * @ 2.20 p.m.
 */
public class ExceptionInfo implements java.io.Serializable {

	private static final long serialVersionUID = 8836968905098483800L;
	public String exceptionCategory;	
	public String exceptionID;
	public boolean isLogged;	                       	
	public int exceptionAtLineNo;
	public String exceptionGeneratingClass;	
	public String exceptionEerrorCode;
	public String methodName;
	public String exceptionDetail;
	public String exceptionCause;
	public String sqlCode="";
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getExceptionCategory() {
		return exceptionCategory;
	}

	public String getExceptionID() {
		return exceptionID;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public int getExceptionAtLineNo() {
		return exceptionAtLineNo;
	}
	

	public String getExceptionGeneratingClass() {
		return exceptionGeneratingClass;
	}

	public String getExceptionEerrorCode() {
		return exceptionEerrorCode;
	}

	public String getMethodName() {
		return methodName;
	}

	public ExceptionInfo(Exception e,String exceptionEerrorCode){			
		this.isLogged = false;		
		this.exceptionID =UniqueIDGeneratorFactory.getUniqueIDGenerator().getUniqueID();		
		//Take out the rest information from the exception object		
		String x[]=(((e.getClass()).getName()).split("[.]", 0));
		this.exceptionCategory=x[2];
		StackTraceElement sTraceElement[]=e.getStackTrace();
		this.exceptionAtLineNo=sTraceElement[0].getLineNumber();
		this.exceptionGeneratingClass=sTraceElement[0].getClassName();
		this.exceptionEerrorCode=exceptionEerrorCode;
		this.methodName=sTraceElement[0].getMethodName();
		this.exceptionDetail=e.fillInStackTrace().toString();
		this.exceptionCause=e.getMessage();
		if(exceptionCategory.equals("SQLException"))
		  this.sqlCode=((SQLException)e).getSQLState();
     }//End of ExceptionInfo() initialization
	
  }//End of ExceptionInfo