/**
 * 
 */
package com.pvt.sec.acl.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Sanjeev
 *
 */
public class Test  {
	
	public static void mainTest(String[] args) {
		
		/*System.out.println("Tset");
		
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		String rawPassword="visitor";
		String encodedPwd=bCryptPasswordEncoder.encode(rawPassword);
		
		System.out.println("Encoded Password = "+encodedPwd);
		*/
		CommaCleaner commaCleaner = new CommaCleaner();

        System.out.println(commaCleaner.cleanUpCommas(",R,b,c,d,T,"));
        System.out.println(commaCleaner.cleanUpCommas("a,b,c,d,e,,,,,"));
        System.out.println(commaCleaner.cleanUpCommas("a,b,,, ,c,d,  ,,e,,,,,"));
        System.out.println(commaCleaner.cleanUpCommas("a,b,c,d,  e,,,,,"));
        System.out.println(commaCleaner.cleanUpCommas(",,, ,,,,a,b,c,d,  e,,,,,"));
		
	}
}
