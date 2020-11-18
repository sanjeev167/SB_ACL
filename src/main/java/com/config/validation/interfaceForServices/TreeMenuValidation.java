/**
 * 
 */
package com.config.validation.interfaceForServices;

import com.config.exception.common.CustomRuntimeException;

/**
 * @author Sanjeev
 *
 */
public interface TreeMenuValidation {
	
	boolean parentNodeRequiredWithTreeNodeType(
			Object nodeTypeValue, 
			String nodeTypeName, 
			
			Object parentNodeCountValue,
			String parentNodeCount,
			
			Object parentNodeValue, 
			String parentNodeName ) throws UnsupportedOperationException, CustomRuntimeException;

}
