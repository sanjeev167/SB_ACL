/**
 * 
 */
package com.pvt.sec.acl.adm.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.config.mutable.MyBasePermission;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.pvt.sec.acl.adm.dto.Aclrights;
import com.pvt.sec.acl.adm.entity.AclPolicyDefinedOnDomain;
import com.pvt.sec.acl.adm.entity.DomainClass;
import com.pvt.sec.acl.adm.entity.PermissionType;
import com.pvt.sec.acl.adm.repo.AclPolicyDefinedOnDomainRepo;
import com.pvt.sec.acl.adm.repo.DomainClassRepo;

/**
 * @author Sanjeev
 *
 */
@Service
public class CustomSecurityService {

	private static final Logger log = LoggerFactory.getLogger(CustomACLService.class);

	/**
	 * Declaration
	 **/
	private final MutableAclService mutableAclService;

	private final CustomACLService customACLService;

	private final ObjectIdentityRetrievalStrategyImpl identityRetrievalStrategy;

	/**
	 * Initializing above declared object through @Autowired constructor of
	 * CustomSecurityService
	 **/
	@Autowired
	public CustomSecurityService(MutableAclService mutableAclService, CustomACLService customACLService,
			ObjectIdentityRetrievalStrategyImpl identityRetrievalStrategy) {
		this.mutableAclService = mutableAclService;
		this.customACLService = customACLService;
		this.identityRetrievalStrategy = identityRetrievalStrategy;
	}

	/**
	 * Get current logged in user authentication object.
	 **/
	private Authentication getAuthentication() {
		final SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return null;
		}
		return context.getAuthentication();
	}

	/**
	 * Get current logged in user authorization object.
	 **/

	private <T> Collection<T> getAuthorities(Class<T> clazz) {
		final Authentication authentication = getAuthentication();
		if (authentication == null) {
			return new ArrayList<>();
		}
		@SuppressWarnings("uncheked")
		Collection<T> authorities = (Collection<T>) getAuthentication().getAuthorities();
		return authorities;
	}

	/**
	 * Get current logged in user as string.
	 **/
	private String getLoggedInUser() {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return username;
	}

	/**
	 * Check current logged in user role.
	 **/
	private boolean isUserHasRole(String role) {
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			hasRole = authority.getAuthority().equals(role);
			if (hasRole) {
				break;
			}
		}
		return hasRole;
	}

	/**
	 * A general method of persisting permission bulk permission assigned either to
	 * a user or a role.
	 **/
	
	public Boolean addBulkAclPermissionsForRoleOrUser(Object objectWithId, String userNameOrRoleName,
			List permissionArrList, boolean isPrincipal) {
		// TODO Auto-generated method stub
		boolean hasRecordBeenAdded = true;
		int maskIntegralValue;
		Permission permission;
		try {
			// start persisting permissions.
			for (int i = 0; i < permissionArrList.size(); i++) {
				permission = null;
				maskIntegralValue = Integer.valueOf(permissionArrList.get(i) + "");
				permission = MyBasePermission.getPermission(maskIntegralValue);				
				customACLService.addPermission(objectWithId, userNameOrRoleName, isPrincipal, permission);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			hasRecordBeenAdded = false;
		}
		return hasRecordBeenAdded;
	}

	@Autowired
	AclAdministrationService aclAdministrationService;

	public Boolean updateBulkAclPermissionsForRoleOrUser(Object objectWithId, String userNameOrRoleName,
			List<Integer> comingPermissionArr, boolean isPrincipal, Integer comingContextId) {
  
		
		boolean hasRecordBeenUpdated = false;
		try {
			Sid comingSid = customACLService.obtainSidObject(userNameOrRoleName.trim(), isPrincipal);
     		ArrayList<Integer> existingPermissionArray = customACLService.getAclBySidAndObjectId(objectWithId,
					comingSid);			
			ArrayList<Integer> preservedUnmatchedPermissionArray = new ArrayList<Integer>();
			int existingPermissionMask;
			int comingPermissionMask;
			PermissionType permissionType;
			int contextIdLocal;
			for (int i = 0; i < existingPermissionArray.size(); i++) {
				existingPermissionMask = existingPermissionArray.get(i);
				permissionType = aclAdministrationService.getPermissionTypeMapForMaskToLabelChange()
						.get(existingPermissionMask);
				contextIdLocal = permissionType.getPermissionContext().getId();
				for (int j = 0; j < comingPermissionArr.size(); j++) {
					comingPermissionMask = comingPermissionArr.get(j);					
					if (existingPermissionMask < 17 || (existingPermissionMask == comingPermissionMask)
							|| (contextIdLocal == comingContextId)) {
						;// do nothing
					} else {
						preservedUnmatchedPermissionArray.add(existingPermissionMask);
					}
				} // Inner loop
			} // Outer loop

			for (int k = 0; k < preservedUnmatchedPermissionArray.size(); k++) {
				comingPermissionArr.add(preservedUnmatchedPermissionArray.get(k));
			}			
			// Remove permissions from
			Permission permission;
			for (int i = 0; i < existingPermissionArray.size(); i++) {
				permission = MyBasePermission.getPermission(existingPermissionArray.get(i));				
				
				customACLService.removePermission(objectWithId, userNameOrRoleName.trim(), isPrincipal, permission);
     		}
			// Add permissions from
			permission = null;
			for (int i = 0; i < comingPermissionArr.size(); i++) {
				permission = MyBasePermission.getPermission(comingPermissionArr.get(i));
				
				customACLService.addPermission(objectWithId, userNameOrRoleName.trim(), isPrincipal, permission);
			}
			hasRecordBeenUpdated = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return hasRecordBeenUpdated;
	}

	public Boolean deleteBulkAclPermissionsForRoleOrUser(Object domain, String userOrRoleName, boolean principal) {
		// TODO Auto-generated method stub
		boolean hasRecordBeenDeleted = false;

		return hasRecordBeenDeleted;
	}

	
	/**
	 * A generic method to get ACL of the currently logged in user.
	 **/
	public <T> List<String> getMyAclForObjectAsLabel(T one) {
		List<String> userACL = new ArrayList<>();
		if (one != null) {
			userACL = getACL(one);
		}
		return userACL;
	}
	
	

	/**
	 * Supporting above getMyAclForObjectAsLabel
	 **/
	private List<String> getACL(Object domain) {
		final List<Sid> sids = customACLService.retrieveSidsBy(getAuthentication());
		final MutableAcl acl = customACLService.retrieveAclForObject(domain);
		ArrayList<Permission> allPermissions = Lists.newArrayList(MyBasePermission.READ, MyBasePermission.WRITE,
				MyBasePermission.CREATE, MyBasePermission.DELETE, MyBasePermission.ADMINISTRATION);

		List<String> permissionLabels = new ArrayList<>();
		Map<Integer, String> maskToLabel = ImmutableMap.of(1, "READ", 2, "WRITE", 4, "CREATE", 8, "DELETE", 16,
				"ADMINISTRATION");

		for (Permission permission : allPermissions) {
			try {
				boolean isGranted = acl.isGranted(Lists.newArrayList(permission), sids, false);
				if (isGranted) {
					String label = maskToLabel.get(permission.getMask());
					permissionLabels.add(label);
				}
			} catch (NotFoundException e) {
				log.info("Get Acl error '{}'", e.getMessage());
			}
		}
		return permissionLabels;
	}

	public List<Integer> getMyAclForObjectAsMask(Object domain) {
		final MutableAcl aclForObject = customACLService.retrieveAclForObject(domain);
		List<AccessControlEntry> accessControlEntry = aclForObject.getEntries();

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		Collection<? extends GrantedAuthority> rolenames = ((UserDetails) principal).getAuthorities();

		List<Integer> permissionList = new ArrayList<Integer>();
		// First check user level permission and stuff it into list
		for (int i = 0; i < accessControlEntry.size(); i++) {
			Sid sid = accessControlEntry.get(i).getSid();
			Sid userSid = new PrincipalSid(username);
			if (sid.equals(userSid)) {
				if (!permissionList.contains(accessControlEntry.get(i).getPermission().getMask()))
					permissionList.add(accessControlEntry.get(i).getPermission().getMask());
			}
		}

		// Now, check role level permission and stuff it into list
		rolenames.forEach(rolename -> {
			for (int i = 0; i < accessControlEntry.size(); i++) {
				Sid sid = accessControlEntry.get(i).getSid();
				// System.out.println("sid1 = "+sid);
				Sid roleSid = new GrantedAuthoritySid(rolename);
				// System.out.println("roleSid = "+roleSid);
				if (sid.equals(roleSid)) {
					if (!permissionList.contains(accessControlEntry.get(i).getPermission().getMask()))
						permissionList.add(accessControlEntry.get(i).getPermission().getMask());
				}
			} // Inner for loop
		});
		return permissionList;
	}

	public String getOwnerOfObject(Object objectWithId) {
		final MutableAcl acl = customACLService.retrieveAclForObject(objectWithId);
		PrincipalSid principalSid = PrincipalSid.class.cast(acl.getOwner());
		return principalSid.getPrincipal();
	}

	public String userLoginFromAuthentication() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null) {
			return null;
		}
		UserDetails userDetails = UserDetails.class.cast(principal);
		return userDetails.getUsername();
	}


	/**
	 * Will prepare list of permission with userRoleName, roleType and
	 * permissionList for all users and roles who have any permission on the object.
	 * Object owner permission are excluded from the list.
	 */
	
	public List<Aclrights> getPermissionListForOtherUsers(Object objectWithId) {

		// Check the loggedIn user is owner of the object or not.
		boolean bothUserAndRolePermissionsToBeSelected = true;
		String loggedInUserName = userLoginFromAuthentication();
		String objectOwner = getOwnerOfObject(objectWithId);
		if (loggedInUserName.equals(objectOwner))
			bothUserAndRolePermissionsToBeSelected = false;
		List<Aclrights> userRoleAccessList = new ArrayList<Aclrights>();

		final MutableAcl aclForObject = customACLService.retrieveAclForObject(objectWithId);

		String roleUserName;
		String userPrincipal;

		try {
			Aclrights usersAndRolePermissionDetailRow = null;
			List<AccessControlEntry> accessControlEntryList = aclForObject.getEntries();
			int permissionMask;
			Sid ownerSid = aclForObject.getOwner();
			for (int i = 0; i < accessControlEntryList.size(); i++) {
				boolean roleUserNameAlreadyStored = false;
				Sid sid = accessControlEntryList.get(i).getSid();
				// Take out exact user/role name
				if (sid.equals(ownerSid)) {
					roleUserName = ownerSid.toString().substring(12).replace('[', ' ');
					roleUserName = (roleUserName.replace(']', ' ')).trim();
					userPrincipal = "Owner";
				} else {
					if (sid.getClass().getName().contains("GrantedAuthoritySid")) {
						roleUserName = sid.toString().substring(19).replace('[', ' ');
						roleUserName = (roleUserName.replace(']', ' ')).trim();
						userPrincipal = "Role";
					} else {
						roleUserName = (sid.toString().substring(12)).replace('[', ' ');
						roleUserName = (roleUserName.replace(']', ' ')).trim();
						userPrincipal = "User";
					}
				}

				if (bothUserAndRolePermissionsToBeSelected) {
					// Now check roleUserName before inserting into OtherUsersAndRolePermissionRow
					roleUserNameAlreadyStored = checkRoleUserNameInserted(usersAndRolePermissionDetailRow,
							roleUserName);
					if (roleUserNameAlreadyStored) {
						usersAndRolePermissionDetailRow = new Aclrights();
						usersAndRolePermissionDetailRow.setRoleOrUserName(roleUserName);
						usersAndRolePermissionDetailRow.setRole(userPrincipal);
						// Add principal for this entry
						permissionMask = accessControlEntryList.get(i).getPermission().getMask();
						usersAndRolePermissionDetailRow.getPreCheckedValuesListForRow()
								.add(MyBasePermission.basePermissionMap.get(permissionMask));

						// Don't store owner of this object as our objective is fetch other user and
						// roles permissions.
						if (!roleUserName.equals(getLoggedInUser()))
							userRoleAccessList.add(usersAndRolePermissionDetailRow);
					} else {
						// This roleUserName still has some permissions. Keep adding it.
						permissionMask = accessControlEntryList.get(i).getPermission().getMask();

						usersAndRolePermissionDetailRow.getPreCheckedValuesListForRow()
								.add(MyBasePermission.basePermissionMap.get(permissionMask));
					}
				} // End of if
				else {
					if (!userPrincipal.equals("Role")) {
						// Now check roleUserName before inserting into OtherUsersAndRolePermissionRow
						roleUserNameAlreadyStored = checkRoleUserNameInserted(usersAndRolePermissionDetailRow,
								roleUserName);
						if (roleUserNameAlreadyStored) {
							usersAndRolePermissionDetailRow = new Aclrights();
							usersAndRolePermissionDetailRow.setRoleOrUserName(roleUserName);
							usersAndRolePermissionDetailRow.setRole(userPrincipal);
							// Add principal for this entry
							permissionMask = accessControlEntryList.get(i).getPermission().getMask();
							usersAndRolePermissionDetailRow.getPreCheckedValuesListForRow()
									.add(MyBasePermission.basePermissionMap.get(permissionMask));

							// Don't store owner of this object as our objective is fetch other user and
							// roles permissions.
							if (!roleUserName.equals(getLoggedInUser()))
								userRoleAccessList.add(usersAndRolePermissionDetailRow);
						} else {
							// This roleUserName still has some permissions. Keep adding it.
							permissionMask = accessControlEntryList.get(i).getPermission().getMask();

							usersAndRolePermissionDetailRow.getPreCheckedValuesListForRow()
									.add(MyBasePermission.basePermissionMap.get(permissionMask));
						}
					}
				}
			} // End of for loop
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Print the prepared collection
		//printOtherUsersAndRolePermissionList(userRoleAccessList);
		return userRoleAccessList;
	}

	private boolean checkRoleUserNameInserted(Aclrights usersAndRolePermissionDetailRow, String roleUserName) {
		// TODO Auto-generated method stub
		boolean newRecord = false;
		if (usersAndRolePermissionDetailRow == null) {
			// System.out.println("New record found");
			newRecord = true;
		}
		if (usersAndRolePermissionDetailRow != null
				&& !usersAndRolePermissionDetailRow.getRoleOrUserName().equals(roleUserName)) {
			// System.out.println("New record found");
			newRecord = true;
		}
		if (usersAndRolePermissionDetailRow != null
				&& usersAndRolePermissionDetailRow.getRoleOrUserName().equals(roleUserName)) {
			// System.out.println("Old record found");
			newRecord = false;
		}

		return newRecord;
	}

	private void printOtherUsersAndRolePermissionList(List<Aclrights> otherUsersAndRolePermissionList) {
		// TODO Auto-generated method stub
		for (int i = 0; i < otherUsersAndRolePermissionList.size(); i++) {
			System.out.println("User or Role Name = > " + otherUsersAndRolePermissionList.get(i).getRoleOrUserName());
			System.out.println("\tUser or Role Principal = > " + otherUsersAndRolePermissionList.get(i).getRole());
			List<String> permissionList = otherUsersAndRolePermissionList.get(i).getPreCheckedValuesListForRow();
			for (int j = 0; j < permissionList.size(); j++) {
				System.out.println("\tPermission = > " + permissionList.get(j));

			}
		}
	}
	
	/**
	 * FOLLOWING METHODS ARE APPLICATION SPECIFIC. WILL BE DIFFERENT FOR DIFFERENT
	 * APPLICATIONS.
	 **/

	@Autowired
	DomainClassRepo domainClassRepo;
	@Autowired	
    AclPolicyDefinedOnDomainRepo aclPolicyDefinedOnDomainRepo;

	// @Transactional(propagation= Propagation.REQUIRED)	
    public <T> void addAclPermissions(T objectWithId) {

		ObjectIdentity objectIdentity = identityRetrievalStrategy.getObjectIdentity(objectWithId);
		mutableAclService.createAcl(objectIdentity);
		String loggedInUser = getLoggedInUser();

		// Load policy defined for the domain of the coming object.
		String domainClassNameWithPakg = objectWithId.getClass().getName();
		DomainClass domainClass = domainClassRepo.findByClassName(domainClassNameWithPakg);
		List<AclPolicyDefinedOnDomain> aclPolicyDefinedOnDomainList = aclPolicyDefinedOnDomainRepo
				.getAllAclPolicyDefinedOnDomain(domainClass);

		boolean isPrincipal;
		AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain;
		String permissionMaskArray[];
		int maskIntegralValue;
		String roleName;
		Permission permission;
		try {
			for (int i = 0; i < aclPolicyDefinedOnDomainList.size(); i++) {
				aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainList.get(i);
				isPrincipal = aclPolicyDefinedOnDomain.getIsPrincipal();
				
				permissionMaskArray = aclPolicyDefinedOnDomain.getPermissionAssignedMaskCollection().split(",");
				if (isPrincipal) {//For User					
					for (int j = 0; j < permissionMaskArray.length; j++) {
						permission = null;
						maskIntegralValue = Integer.valueOf(permissionMaskArray[j]);
						permission = MyBasePermission.getPermission(maskIntegralValue);
						customACLService.addPermission(objectWithId, loggedInUser, true, permission);
					}
				} else {//For Role					
					for (int j = 0; j < permissionMaskArray.length; j++) {
						permission = null;
						maskIntegralValue = Integer.valueOf(permissionMaskArray[j]);
						roleName = aclPolicyDefinedOnDomain.getAppRole().getRoleName();
						permission = MyBasePermission.getPermission(maskIntegralValue);
						// System.out.println("maskIntegralValue = "+maskIntegralValue);
						// System.out.println("permission = "+permission);						
						customACLService.addPermission(objectWithId, roleName, false, permission);
						
					}
				}
			} // outer for loop
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}// addAclPermissions

	public <T> void removeSelectedAclPermissions(T objectWithId, String userOrRoleName,
			ArrayList<Integer> selectedPermissionList, boolean isPrincipal) {

		Permission permissions[] = new Permission[selectedPermissionList.size()];
		Permission permission;
		for (int i = 0; i < selectedPermissionList.size(); i++) {
			permission = MyBasePermission.getPermission(selectedPermissionList.get(i));
			permissions[i] = permission;
		} // End of for loop

		customACLService.removePermissions(objectWithId, userOrRoleName, isPrincipal, permissions);
	}

	public List<Integer> getPermissionListForOtherUser(Object objectWithId, String userOrRoleName,
			Boolean isPrincipal) {
		final MutableAcl aclForObject = customACLService.retrieveAclForObject(objectWithId);
		Sid sid = customACLService.obtainSidObject(userOrRoleName, isPrincipal);
		List<Integer> userOrRoleAccessList = new ArrayList<Integer>();
		List<AccessControlEntry> accessControlEntryList = aclForObject.getEntries();
		// System.out.println("sid coming fromUI "+sid);
		for (int i = 0; i < accessControlEntryList.size(); i++) {
			// System.out.println("Sid Coming from acl
			// "+accessControlEntryList.get(i).getSid());

			if (accessControlEntryList.get(i).getSid().equals(sid)) {
				userOrRoleAccessList.add(accessControlEntryList.get(i).getPermission().getMask());
			}
		}
		// Print the prepared collection
		// printOtherUsersAndRolePermissionList(userRoleAccessList);
		return userOrRoleAccessList;
	}

	public boolean removeACLByObject(Object objectWithId) {
		// TODO Auto-generated method stub
		customACLService.removeACLByObject(objectWithId);
		return true;
	}
	
	public boolean deleteAllAclsExceptOwnerAndSuperAdminAcls(Object objectWithId) {
		// TODO Auto-generated method stub
		MutableAcl acl = customACLService.retrieveAclForObject(objectWithId);	
		Sid superAdminSid =customACLService.obtainSidObject("ROLE_SUPER_ADMIN", false);
		Sid ownerSid =acl.getOwner();
		
		final MutableAcl mutableAcl = MutableAcl.class.cast(acl);
		List<AccessControlEntry> mutableAclEntries=mutableAcl.getEntries();
		AccessControlEntry accessControlEntry;Sid entrySid;
		
		for(int i=0;i<mutableAclEntries.size();i++) {			
			accessControlEntry=mutableAclEntries.get(i);
			entrySid=accessControlEntry.getSid();
			if(entrySid.equals(superAdminSid) || entrySid.equals(ownerSid)) {				
				mutableAcl.deleteAce(i);
			}
		}
		return true;
	}

}