/**
 * 
 */
package com.config.validation.interfaceForServices;

import com.config.exception.common.CustomRuntimeException;

/**
 * @author Sanjeev
 *
 */
public interface FieldValueExists {
	/**
     * Checks whether or not a given value exists for a given field
     * 
     * @param value The value to check for
     * @param fieldName The name of the field for which to check if the value exists
     * @return True if the value exists for the field; false otherwise
     * @throws UnsupportedOperationException
	 * @throws CustomRuntimeException 
     */
    boolean fieldValueExists(Object value, String fieldName) throws CustomRuntimeException;

	boolean fieldValueExists(Object fieldValue, String fieldName, Object idValue, String id)throws CustomRuntimeException;
}
