/**
 * 
 */
package com.config.mutable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;

/**
 * @author Sanjeev
 *
 */
public class MyBasePermission extends BasePermission  {	
	private static final long serialVersionUID = 1L;
	public static String OBJECTOWNER;
	public static Map<Integer, String> basePermissionMap = new LinkedHashMap<Integer, String>();
	public static Map<String, Integer> basePermissionMapLabelIntoInteger = new LinkedHashMap<String, Integer>();
	
	//Keep it public so that acl permission evaluator could access it.
	public static final Permission REPORT = new MyBasePermission(1<<5, 'O'); // 32
	public static final Permission APPROVE = new MyBasePermission(1<<6, 'T'); // 64 erase acl for object	
	
	protected MyBasePermission(int mask) {
		super(mask);
	}	
	protected MyBasePermission(int mask, char code) {
		super(mask, code);
		// This will be used for converting a mask value into a permission label.
		basePermissionMap.put(1, "READ");
		basePermissionMap.put(2, "WRITE");
		basePermissionMap.put(4, "CREATE");
		basePermissionMap.put(8, "DELETE");
		basePermissionMap.put(16, "ADMINISTRATION");
		basePermissionMap.put(32, "REPORT");
		basePermissionMap.put(64, "APPROVE");
		// This will be used for converting a permission Label into its corresponding
		// mask value.
		basePermissionMapLabelIntoInteger.put("READ", 1);
		basePermissionMapLabelIntoInteger.put("WRITE", 2);
		basePermissionMapLabelIntoInteger.put("CREATE", 4);
		basePermissionMapLabelIntoInteger.put("DELETE", 8);
		basePermissionMapLabelIntoInteger.put("ADMINISTRATION", 16);
		basePermissionMapLabelIntoInteger.put("REPORT", 32);
		basePermissionMapLabelIntoInteger.put("APPROVE", 64);
	}

	/**
	 * @return the permission
	 */
	public static Permission permission;
	public static Permission getPermission(Integer mask) {
		Permission permission = null;
		if (mask == 1)
			permission = MyBasePermission.READ;
		if (mask == 2)
			permission = MyBasePermission.WRITE;
		if (mask == 4)
			permission = MyBasePermission.CREATE;
		if (mask == 8)
			permission = MyBasePermission.DELETE;
		if (mask == 16)
			permission = MyBasePermission.ADMINISTRATION;
		if (mask == 32)
			permission = MyBasePermission.REPORT;
		if (mask == 64)
			permission = MyBasePermission.APPROVE;
		return permission;
	}	
}
