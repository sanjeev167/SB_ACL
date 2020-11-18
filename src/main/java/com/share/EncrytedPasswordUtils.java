/**
 * 
 */
package com.share;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Sanjeev
 *
 */
public class EncrytedPasswordUtils {
	 
    // Encryte Password with BCryptPasswordEncoder
    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
 
    /*public static void main(String args[]) {
    	
    	log.info("Sanju  = "+encrytePassword("Pass@12345"));
    	
    }*/
    
}