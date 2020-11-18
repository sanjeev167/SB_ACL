/**
 * 
 */
package com.pvt.sec.acl.adm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.config.mutable.constants.ROLES;
import com.config.mutable.MyBasePermission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanjeev
 *
 */
@Service
@Transactional("acl_tm")
public class CustomACLService {

	private static final Logger log = LoggerFactory.getLogger(CustomACLService.class);

	/**
	 * Declaration
	 **/
	private final MutableAclService mutableAclService;

	private final ObjectIdentityRetrievalStrategyImpl objectIdentityRetrievalStrategy;

	private final AclPermissionEvaluator aclPermissionEvaluator;

	private final SidRetrievalStrategy sidRetrievalStrategy;

	/**
	 * Initialization through custructor
	 **/
	@Autowired
	public CustomACLService(SidRetrievalStrategy sidRetrievalStrategy,
			ObjectIdentityRetrievalStrategyImpl objectIdentityRetrievalStrategy, MutableAclService mutableAclService,
			JdbcMutableAclService jdbcMutableAclService) {
		this.sidRetrievalStrategy = sidRetrievalStrategy;
		this.objectIdentityRetrievalStrategy = objectIdentityRetrievalStrategy;
		this.mutableAclService = mutableAclService;
		this.aclPermissionEvaluator = new AclPermissionEvaluator(jdbcMutableAclService);
	}

	/**
	 * Use it for getting sid using userData and isPrincipal
	 **/
	public Sid obtainSidObject(Object userData, boolean isPrincipal) {

		log.info("Try to get principal object from data '{}'", userData);
		if (userData instanceof Sid) {
			log.info("Successfully got Sid object from data '{}'", userData);
			return Sid.class.cast(userData);
		}
		if (userData instanceof Authentication) {
			log.info("Successfully got Authentication object from data '{}'", userData);
			return new PrincipalSid(Authentication.class.cast(userData));
		}
		if (userData instanceof String) {
			String stringRecipient = String.class.cast(userData);
			boolean startsWithRolePrefix = stringRecipient.startsWith(ROLES.ROLE_PREFIX);
			if (!isPrincipal && !startsWithRolePrefix) {
				stringRecipient = ROLES.ROLE_PREFIX + userData;
			}

			if (startsWithRolePrefix) {
				log.info("Successfully got GrantedAuthoritySid object from data '{}'", stringRecipient);
				return new GrantedAuthoritySid(stringRecipient);
			} else {
				log.info("Successfully got PrincipalSid object from data '{}'", stringRecipient);

				return new PrincipalSid(stringRecipient);
			}
		}
		
		log.error("No way to get principal object from data '{}'", userData);
		throw new IllegalArgumentException("Illegal principal object");
	}

	public ObjectIdentity getObjectIdentity(Object objectWithId) {
		ObjectIdentity objectIdentity = objectIdentityRetrievalStrategy.getObjectIdentity(objectWithId);
		return objectIdentity;
	}

	/***
	 * A private method for persisting bulk permission. Will be used by other public
	 * method.
	 **/
	private void persistGeneralPermissions(final Object objectWithId, final String userNameOrRoleName,
			
			boolean isPrincipal, Permission... selectedPermissions) {
		for (Permission selectedPermission : selectedPermissions) {
			addPermission(objectWithId, userNameOrRoleName, isPrincipal, selectedPermission);
		}
	}

	/**
	 * Use it to add single permission for a user/role.
	 **/
	public MutableAcl addPermission(Object objectWithId, Object userData, boolean isPrincipal, Permission permission) {
		ObjectIdentity objectIdentity = objectIdentityRetrievalStrategy.getObjectIdentity(objectWithId);
		
		String formatedUserData=userData.toString();
		if(!isPrincipal) {
			if(!userData.toString().startsWith("ROLE_"))
				 formatedUserData="ROLE_"+userData;
		}
			
		return addPermission(objectIdentity, formatedUserData, isPrincipal, permission);
	}
	
	private MutableAcl addPermission(ObjectIdentity objectIdentity, Object userData, boolean isPrincipal,
			Permission permission) {
		MutableAcl mutableAcl;
		try {
			log.info("Try to find mutableAcl object for '{}'", objectIdentity);
			Acl acl = mutableAclService.readAclById(objectIdentity);
			mutableAcl = MutableAcl.class.cast(acl);
			log.info("Successfully found mutableAcl object for '{}'", objectIdentity);
		} catch (NotFoundException e) {
			log.info("Existing ACL is not found and new ACL will be created");
			mutableAcl = mutableAclService.createAcl(objectIdentity);
			log.info("Successfully created mutableAcl object for '{}'", objectIdentity);
		}
		List<AccessControlEntry> entries = mutableAcl.getEntries();
		log.info("Try to obtain sid by user data '{}'", userData);
		Sid sid = obtainSidObject(userData, isPrincipal);
		log.info("Successfully got sid by user data '{}'", userData);
		
		log.info("Try to update mutableAcl permission '{}' for object '{}' for user '{}'", permission, objectIdentity,
				userData);
		mutableAcl.insertAce(entries.size(), permission, sid, true);
		mutableAcl = mutableAclService.updateAcl(mutableAcl);
		log.info("Successfully updated mutableAcl");
		return mutableAcl;
	}
	
	public void removePermission(Object objectWithId, Object userData, boolean isPrincipal, Permission permission) {

		ObjectIdentity objectIdentity = objectIdentityRetrievalStrategy.getObjectIdentity(objectWithId);
		String formatedUserData=userData.toString();
		if(!isPrincipal) {
			if(!userData.toString().startsWith("ROLE_"))
				 formatedUserData="ROLE_"+userData;
		}
		removeSelectedPermission(objectIdentity, formatedUserData, isPrincipal, permission);
	}
	
	public void removeACLByObject(Object objectWithId) {
		ObjectIdentity objectIdentity = objectIdentityRetrievalStrategy.getObjectIdentity(objectWithId);
		mutableAclService.deleteAcl(objectIdentity, false);
	}
	
	void removePermissions(final Object objectWithId, final String userNameOrRoleName, boolean isPrincipal,
			Permission... selectedPermissions) {
		String formatedUserData=userNameOrRoleName.toString();
		if(!isPrincipal) {
			if(!userNameOrRoleName.toString().startsWith("ROLE_"))
				 formatedUserData="ROLE_"+userNameOrRoleName;
		}
		for (Permission selectedPermission : selectedPermissions) {
			removePermission(objectWithId, formatedUserData, isPrincipal, selectedPermission);
		}
	}

	/**
	 * Status[Working Fine ]:
	 **/

	public Sid getObjectOwner(Object objectWithId) {
		MutableAcl acl = retrieveAclForObject(objectWithId);
		return acl.getOwner();
	}

	/**
	 * Status[Working Fine ]:
	 **/

	public ArrayList<Integer> getObjectOwnerPermissions(Object objectWithId) {
		MutableAcl acl = retrieveAclForObject(objectWithId);
		Sid ownerSid = acl.getOwner();
		ArrayList<Integer> objectOwnerPermissionList = new ArrayList<Integer>();
		List<AccessControlEntry> acls = acl.getEntries();
		AccessControlEntry ace;
		for (int i = 0; i < acls.size(); i++) {
			ace = acls.get(i);
			if (ace.getSid().equals(ownerSid))
				objectOwnerPermissionList.add(ace.getPermission().getMask());
		}
		return objectOwnerPermissionList;
	}

	/**
	 * Status[Working Fine ]:
	 **/

	public ArrayList<String> getObjectOwnerPermissionLabel(Object objectWithId) {
		MutableAcl acl = retrieveAclForObject(objectWithId);
		Sid ownerSid = acl.getOwner();
		ArrayList<String> objectOwnerPermissionList = new ArrayList<String>();
		List<AccessControlEntry> acls = acl.getEntries();
		AccessControlEntry ace;
		for (int i = 0; i < acls.size(); i++) {
			ace = acls.get(i);
			if (ace.getSid().equals(ownerSid)) {
				objectOwnerPermissionList.add(MyBasePermission.basePermissionMap.get(ace.getPermission().getMask()));
			}
		}
		return objectOwnerPermissionList;
	}

	/**
	 * Status[Working Fine ]: Change Object-ownership
	 **/
	public MutableAcl changeOwnerForObjectByName(Object objectWithId, String oldUserName, String newUsername) {

		MutableAcl acl = retrieveAclForObject(objectWithId);
		PrincipalSid newPrincipalSid = new PrincipalSid(newUsername.trim());
		PrincipalSid oldPrincipalSid = new PrincipalSid(oldUserName.trim());
		acl.setOwner(newPrincipalSid);
		AccessControlEntry ace = null;
		//It will insert those permissions for new owner which were with the old owner
		for (int i = 0; i < acl.getEntries().size(); i++) {
			ace = acl.getEntries().get(i);
			if (ace.getSid().equals(oldPrincipalSid)) {
				acl.insertAce(acl.getEntries().size(), ace.getPermission(), newPrincipalSid, ace.isGranting());
			}
		} // for loop
		
		//Delete ADMINISTRATION RIGHTS of Old owner if it had. 
		//It could be updated from front end,too.
		//But safer end, it should be deleted while changing the ownership.
		for (int i = 0; i < acl.getEntries().size(); i++) {
			ace = acl.getEntries().get(i);			
			if (ace.getSid().equals(oldPrincipalSid) && ace.getPermission().getMask()==16) { 
				removePermission(objectWithId, oldUserName.trim(), true, ace.getPermission());			
			   System.out.println("Administration permission of old user has been removed.");
			  }
			} // for loop		
		return mutableAclService.updateAcl(acl);
	}

	/**
	 * A method
	 **/
	MutableAcl retrieveAclForObject(Object objectWithId) {
		ObjectIdentity objectIdentity = objectIdentityRetrievalStrategy.getObjectIdentity(objectWithId);
		Acl acl = mutableAclService.readAclById(objectIdentity);
		return MutableAcl.class.cast(acl);
	}

	/**
	 * method.Used by local public method.
	 **/
	List<Sid> retrieveSidsBy(Authentication authentication) {
		return sidRetrievalStrategy.getSids(authentication);
	}

	/**
	 * Private method.Used by local public method.
	 **/
	private void removeSelectedPermission(ObjectIdentity objectIdentity, Object userData, boolean isPrincipal,
			final Permission selectedPermission) {
		boolean loopBreak = false;
		try {
			log.info("Try to get sid by user data '{}'", userData);
			final Sid sid = obtainSidObject(userData, isPrincipal);
			log.info("Successfully got sid '{}' by user data '{}'", sid, userData);

			log.info("Try to get acl by objectIdentity '{}'", objectIdentity);
			final Acl acl = mutableAclService.readAclById(objectIdentity);
			log.info("Successfully got acl '{}' by objectIdentity '{}'", acl, userData);

			final MutableAcl mutableAcl = MutableAcl.class.cast(acl);
			while (!loopBreak) {
				loopBreak = isLoopBreak(selectedPermission, sid, mutableAcl);
			}
			log.info("Try to persist mutableAcl");			
			mutableAclService.updateAcl(mutableAcl);
			log.info("Successfully persisted mutableAcl");
		} catch (NotFoundException e) {
			log.error("Error while removing selected permissions with message '{}'", e.getMessage());
		}
	}

	/**
	 * Private method.Used by local public method.
	 **/
	private boolean isLoopBreak(Permission selectedPermission, Sid sid, MutableAcl mutableAcl) {
		boolean loopBreak = true;
		final List<AccessControlEntry> mutableAclEntries = mutableAcl.getEntries();
		log.info("In loop, got {} Access Control Entries", mutableAclEntries.size());

		log.info("Iterate for Access Control Entries");
		for (int i = 0; i < mutableAclEntries.size(); i++) {

			final AccessControlEntry entry = mutableAclEntries.get(i);
			final Sid entrySid = entry.getSid();
			final Permission permission = entry.getPermission();
			if (entrySid.equals(sid) && permission.equals(selectedPermission)) {
				log.info("Try to delete Access Control Entry by index '{}'", i);
				mutableAcl.deleteAce(i);				
				log.info("Successfully deleted Access Control Entry by index '{}'", i);
				log.info("Set loopBreak to false and break inner 'for' loop");
				loopBreak = false;
				break;
			}
		}
		return loopBreak;
	}

	/**
	 * Status[Working Fine ]:
	 **/

	public ArrayList<Integer> getAclBySidAndObjectId(Object objectWithId, Sid comingSid) {
		MutableAcl acl = retrieveAclForObject(objectWithId);
		ArrayList<Integer> existingPermissionMask = new ArrayList<Integer>();
		AccessControlEntry accessControlEntry;
		List<AccessControlEntry> accessControlEntryList = acl.getEntries();
		for (int i = 0; i < accessControlEntryList.size(); i++) {
			accessControlEntry = accessControlEntryList.get(i);
			if (accessControlEntry.getSid().equals(comingSid)) {
				existingPermissionMask.add(accessControlEntry.getPermission().getMask());
			}
		}
		return existingPermissionMask;
	}

}// End of CustomAclService
