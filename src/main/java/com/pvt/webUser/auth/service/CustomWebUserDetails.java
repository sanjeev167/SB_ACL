/**
 * 
 */
package com.pvt.webUser.auth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pvt.sec.rbac.entity.AppGroupRole;
import com.pvt.sec.rbac.entity.AppRole;
import com.pvt.sec.rbac.entity.PageAccess;
import com.pvt.sec.rbac.entity.UserGroup;
import com.pvt.sec.rbac.entity.WebAccessRights;
import com.pvt.sec.rbac.entity.WebUser;

/**
 * @author Sanjeev
 *
 */
public class CustomWebUserDetails extends WebUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private List<String> allowedUrls = new ArrayList<String>();

	// @Override
	public Collection<? extends GrantedAuthority> getAuthorities(WebUser webUser) {

		List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<SimpleGrantedAuthority>();
		// Call suitable method for knowing the group assigned with this particular user
		List<UserGroup> userGroups = webUser.getUserGroups();// Thrugh webUser object method

		List<AppGroupRole> appGroupRoles = null;
		List<String> allowedUrls = new ArrayList<String>();

		for (UserGroup userGroup : userGroups) {

			// Once the group is known, take out all the roles associated with this group
			appGroupRoles = userGroup.getAppGroup().getAppGroupRoles();
			AppRole appRole = null;
			List<WebAccessRights> webAccessRights = null;
			// Now start taking out access rights and roles for each group and collect them
			// in a list
			for (AppGroupRole appGroupRole : appGroupRoles) {
				// Now start collecting all the roles defined for the groups
				appRole = appGroupRole.getAppRole();
				SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
						"ROLE_" + appRole.getRoleName());
				simpleGrantedAuthorityList.add(simpleGrantedAuthority);
				// Start collecting all the assigned web-urls
				webAccessRights = appRole.getWebAccessRights();
				for (WebAccessRights webAccessRight : webAccessRights) {
					allowedUrls.add(webAccessRight.getPageMaster().getBaseurl());
				}
			}
		}
		return simpleGrantedAuthorityList;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * @return the allowedUrls
	 */
	public List<String> getAllowedUrls() {
		return this.allowedUrls;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

}
