/**
 * 
 */
package com.config;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Sanjeev
 *
 */
public interface UrlAndRoleMatcherService {

	public Map<String,ArrayList<String>> getWebUrlAndRoleMatcherList();
	
	//public Map<String,ArrayList<String>> getMethodUrlAndRoleMatcherList();
}//End of UrlAndRoleMatcherService
