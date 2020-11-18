/**
 * 
 */
package com.config.exception.common;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author ksanjeev
 * 
 */
public final class StackTraceTool {	
	
	private StackTraceTool() {}

	public static String getStackTraceAsString(CustomBaseException exception)
	{
		String message = " Exception ID : " 
			                            +exception.getExceptionInfo().getExceptionID()+ "\n " + 
			             "Message :" + exception.getMessage();
		return getStackMessage(message, exception);
	}

	public static String getStackTraceAsString(Throwable throwable)
	{
		String message = " Exception ID : " + 
		                 UniqueIDGeneratorFactory.getUniqueIDGenerator().getUniqueID()+ "\n " + 
		                 "Message :" + throwable.getMessage();
		return getStackMessage(message, throwable);

	}

	private static String getStackMessage(String message,Throwable exception)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.print(" [ ");
		pw.print(exception.getClass().getName());
		pw.print(" ] ");
		pw.print(message);
		exception.printStackTrace(pw);
		return sw.toString();
	}

}
