/**
 * 
 */
package com.config.exception.common;

import java.util.HashMap;

/**
 * @author Sanjeev
 *
 */
public class ExceptionDetailRepository {

	private static HashMap<String, String> expDetailsMap = new HashMap<String, String>();

	/**
	 * @return the expDetMap
	 */
	public static HashMap<String, String> getExpDetailsMap() {
		return expDetailsMap;
	}

	/**
	 * @param expDetMap the expDetMap to set
	 */
	public static void setExpDetailsMap(HashMap<String, String> expDetMap) {
		HashMap<String, String> expDetailMap = new HashMap<String, String>();
		
		// expDetailMap.put("expName", "expCode"+"#"+"expSevirity"+""+"#"+"msg4Client");
		
		//Checked Exception listing exception code range 1 to 99
		expDetailMap.put("IOException", "1" + "#" + "T" + "#" + "msg4Client");
		expDetailMap.put("FileNotFoundException", "2" + "#" + "T" + "#" + "Requested file is not available.Contact support.");		
		
		//Database accessing related code range 100 to 199
		expDetailMap.put("SQLException", "100" + "#" + "T" +"#" + "Databse accessing has an issue. Contact support.");
		
		
		//Business related code range 200 to 299
		expDetailMap.put("RecordNotFoundException", "200" + "#" + "F" +"#" + "Record not found.");
		expDetailMap.put("RecordUniquenessException", "201" + "#" + "F" +"#" + "This field already exists.");
		
		//Runtime or unchecked Exception listing code range 300 t0 399
		expDetailMap.put("ArithmeticException", "300" + "#" + "F" + "#" +"Input data is producing arithmetical error.");
		expDetailMap.put("ArrayIndexOutOfBoundsException", "301" + "#" + "F" + "#" + "No such record is found");
		

		ExceptionDetailRepository.expDetailsMap = expDetailMap;
	}
}
