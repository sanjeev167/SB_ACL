/**
 * 
 */
package com.config.mutable;

import org.springframework.security.acls.domain.DefaultPermissionFactory;

import com.config.mutable.MyBasePermission;

/**
 * CustomPermission class must be registered here so that this CustomPermissionFactory, which has been configured with 
 * AclPermissionEvaluator, could inform AclPermissionEvaluator to evaluate permission for this custom permission. too. 
 * 
 * @author Sanjeev
 *
 */
public class CustomPermissionFactory extends DefaultPermissionFactory {
	public CustomPermissionFactory() {
		super();
		registerPublicPermissions(MyBasePermission.class);
	}
}
