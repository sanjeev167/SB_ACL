package com.config.exception.common;

/**
 * @author Sanjeev Kumar
 * @created On: 7th July, 2010
 * @At 12.18 pm
 */
public class ExceptionApplicationUtility {	
		        
	 // 3. This method is used for wrapping a run time exception in a user defined exception
	 public static CustomRuntimeException wrapRunTimeException(Exception e) {
		 CustomRuntimeException customRuntimeException=null;		
		 if(e.getClass().getName().equals("com.custom.exception.CustomRuntimeException"))
		   { customRuntimeException=(CustomRuntimeException)e;}
		 else
		    {ExceptionInfo exceptionInfo = new ExceptionInfo(e,555+"");
		      customRuntimeException = new CustomRuntimeException(exceptionInfo, e);}
		  
		 return customRuntimeException;
	 }// End of wrapRuntimeException

	public static CustomBusinessException wrapBusinessException(Exception e) {
		CustomBusinessException customBusinessException = null;
		ExceptionInfo exceptionInfo = new ExceptionInfo(e, 444 + "");
		customBusinessException = new CustomBusinessException(exceptionInfo, e);

		return customBusinessException; 
	}// End of wrapRuntimeException
	 
	
}// End of ExceptionApplicationUtility