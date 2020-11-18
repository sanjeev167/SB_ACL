/**
 * 
 */
package com.config.exception.common;

/**
 * @author Sanjeev
 *
 */
public class ExceptionFormatter {
	

	public static String[] formatException(Exception ex, boolean sevirity, Character layer) {
		
		String  expDetailsArr[]=new String[4];
		String expStr=ex.toString();
		String expDetails[]=ExceptionDetailRepository.getExpDetailsMap().get(expStr.split(".")[2]).split("#");
		
		expDetailsArr[0] = expDetails[0];//Error code
		expDetailsArr[1] = expDetails[1];//Severity
		expDetailsArr[2] = expDetails[2];//msg4Client
		expDetailsArr[3] = getExpMsg4Mstaff(ex);
		// TODO Auto-generated method stub
		return expDetailsArr;
	}

	public static String[] formatException(Exception ex, Character layer) {
		String  expDetailsArr[]=new String[4];
		String expStr=ex.toString();
		String expDetails[]=ExceptionDetailRepository.getExpDetailsMap().get(expStr.split(".")[2]).split("#");
		
		expDetailsArr[0] = expDetails[0];//Error code
		expDetailsArr[1] = expDetails[1];//Severity
		expDetailsArr[2] = expDetails[2];//msg4Client
		expDetailsArr[3] = getExpMsg4Mstaff(ex);
		
		return expDetailsArr;
	}

	private static String getExpMsg4Mstaff(Exception ex) {
		String msgForMstaff="";
		//Message for client will be in the following format
		//  Exception Name [] ==> Class Name [] ==> Method Name ==> Line No [ ] ==> Cause []
		
		
		return msgForMstaff;
	}
	
	
	

}
