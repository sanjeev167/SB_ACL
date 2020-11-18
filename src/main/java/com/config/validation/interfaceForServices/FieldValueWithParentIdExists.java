/**
 * 
 */
package com.config.validation.interfaceForServices;

import com.config.exception.common.CustomRuntimeException;

/**
 * @author Sanjeev
 *
 */
public interface FieldValueWithParentIdExists {
	/**
	 * Checks whether or not a given value exists for a given field
	 * 
	 * @param value     The value to check for
	 * @param fieldName The name of the field for which to check if the value exists
	 * @return True if the value exists for the field; false otherwise
	 * @throws UnsupportedOperationException
	 * @throws CustomRuntimeException
	 */
	boolean FieldValueWithParentIdAndChildExists(Object parentValue, String parentId, Object fieldValue,
			String fieldName) throws UnsupportedOperationException, CustomRuntimeException;

	public boolean FieldValueWithParentIdAndChildExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException;
	
	public boolean FirstChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException;

	public boolean SecondChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException;

	public boolean ThirdChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException;

}
