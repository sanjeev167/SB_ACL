/**
 * 
 */
package com.config.exception.common;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author Sanjeev
 * 
 */
public class UniqueIDGeneratorFactory implements IUniqueIDGenerator
{	
	private long counter = 1;	
	public String getUniqueID()
	{
		String exceptionID = null;
		try {
			exceptionID = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException ue) {
			ue.printStackTrace();
		}
		exceptionID = "["+exceptionID+"-" +System.currentTimeMillis()+"]:"+counter++;
		return exceptionID;
	}
	
	public static UniqueIDGeneratorFactory getUniqueIDGenerator() {
		// TODO Auto-generated method stub
		return new UniqueIDGeneratorFactory();
	}
}
