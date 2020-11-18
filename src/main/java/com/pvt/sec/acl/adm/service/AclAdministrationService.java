/**
 * 
 */
package com.pvt.sec.acl.adm.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.acl.adm.dto.Aclrights;
import com.pvt.sec.acl.adm.dto.ChangeOwnerDTO;
import com.pvt.sec.acl.adm.dto.SharedRolePermissionsDTO;
import com.pvt.sec.acl.adm.dto.SharedUserPermissionsDTO;
import com.pvt.sec.acl.adm.entity.AclPolicyDefinedOnDomain;
import com.pvt.sec.acl.adm.entity.DomainClass;
import com.pvt.sec.acl.adm.entity.PermissionOnDomain;
import com.pvt.sec.acl.adm.entity.PermissionType;
import com.pvt.sec.acl.adm.repo.AclPolicyDefinedOnDomainRepo;
import com.pvt.sec.acl.adm.repo.DomainClassRepo;
import com.pvt.sec.acl.adm.repo.PermissionOnDomainRepo;
import com.pvt.sec.acl.adm.repo.PermissionTypeRepo;

import com.pvt.sec.rbac.entity.AppRole;
import com.pvt.sec.rbac.repo.AppRoleRepository;
import com.share.NameValue;

/**
 * @author Sanjeev
 *
 */
@Service
@Transactional("appTransactionManager")
public class AclAdministrationService {

	@Autowired
	PermissionTypeRepo permissionTypeRepo;
	@Autowired
	AclPolicyDefinedOnDomainRepo aclPolicyDefinedOnDomainRepo;
	@Autowired
	DomainClassRepo domainClassRepo;
	@Autowired
	AppRoleRepository roleRepo;

	@Autowired
	PermissionOnDomainRepo permissionOnDomainRepo;
	@Autowired
	CustomSecurityService customSecurityService;
	@Autowired
	CustomACLService customACLService;
	@Autowired
	MutableAclService mutableAclService;

	@PreAuthorize("hasPermission(#domainObject, 'ADMINISTRATION')")
	public List<Aclrights> getPermissionListOfOtherUsersForGrid(Object domainObject)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		List<Aclrights> aclrightsList = customSecurityService.getPermissionListForOtherUsers(domainObject);

		return aclrightsList;
	}
	
	public List<NameValue> loadMyPermissionList(String domainClass, long domainId)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		Object domainObject = prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domainClass, domainId);

		List<Integer> myAclPermissionListOnObject = customSecurityService.getMyAclForObjectAsMask(domainObject);

		DomainClass domainClassLocal = domainClassRepo.findByClassName(domainClass);
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClassLocal);
		String permissionMaskArr[] = permissionOnDomain.getPermissionMaskCollection().split(",");

		List<NameValue> permissionList = new ArrayList<NameValue>();
		String permissionLabel = "";
		int customTextId;
		int maskPower;
		int maskVal;
		boolean isAlreadyInserted = false;
		Map<Integer, PermissionType> permissionTypeMap = getPermissionTypeMapForMaskToLabelChange();
		PermissionType permissionType;
		for (int i = 0; i < permissionMaskArr.length; i++) {
			isAlreadyInserted = false;
			permissionType = permissionTypeMap.get(Integer.valueOf(permissionMaskArr[i]));
			permissionLabel = permissionType.getName();
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();
			for (int j = 0; j < myAclPermissionListOnObject.size(); j++) {
				maskVal = myAclPermissionListOnObject.get(j);
				if (Integer.valueOf(permissionMaskArr[i]) == maskVal) {
					permissionList.add(new NameValue(permissionType.getMask() + "", permissionLabel, maskPower,
							customTextId, "checked"));
					isAlreadyInserted = true;
				}
			} // inner for loop
			if (!isAlreadyInserted)
				permissionList.add(new NameValue(permissionMaskArr[i], permissionLabel, maskPower, customTextId, ""));

		} // outer for loop
		return permissionList;

	}// loadLoggedInUserAclPermissionList

	public List<NameValue> getPermissionListDefinedOnDomain(String domainClass) {

		DomainClass domainClassLocal = domainClassRepo.findByClassName(domainClass);
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClassLocal);
		String permissionMaskArr[] = permissionOnDomain.getPermissionMaskCollection().split(",");

		Map<Integer, PermissionType> permissionTypeMap = getPermissionTypeMapForMaskToLabelChange();
		List<NameValue> permissionList = new ArrayList<NameValue>();
		String permissionLabel = "";
		int customTextId;
		int maskPower;

		for (int i = 0; i < permissionMaskArr.length; i++) {
			PermissionType permissionType = permissionTypeMap.get(Integer.valueOf(permissionMaskArr[i]));
			permissionLabel = permissionType.getName();
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();
			permissionList
					.add(new NameValue(permissionType.getMask() + "", permissionLabel, maskPower, customTextId, ""));
		}
		return permissionList;
	}// getPermissionListDefinedOnDomain

	public List<NameValue> getContextBasedPermissionList(String contextId) {
		// TODO Auto-generated method stub
		List<PermissionType> permissionTypeList = permissionTypeRepo.getPermissionType();
		List<NameValue> permissionList = new ArrayList<NameValue>();
		String permissionLabel = "";
		int customTextId;
		int maskPower;

		for (int i = 0; i < permissionTypeList.size(); i++) {
			permissionLabel = permissionTypeList.get(i).getName();
			maskPower = Math.getExponent(permissionTypeList.get(i).getMask());
			customTextId = permissionTypeList.get(i).getPermissionContext().getId();
			if (maskPower < 5 || customTextId == (Integer.valueOf(contextId))) {
				permissionList.add(new NameValue(permissionTypeList.get(i).getId() + "", permissionLabel, maskPower,
						customTextId, ""));
			}
		}
		return permissionList;
	}

	public List<NameValue> getAllContextPermissionList() {
		List<PermissionType> permissionTypeList = permissionTypeRepo.getPermissionType();
		List<NameValue> permissionList = new ArrayList<NameValue>();
		String permissionLabel = "";
		int customTextId;
		int maskPower;

		for (int i = 0; i < permissionTypeList.size(); i++) {
			permissionLabel = permissionTypeList.get(i).getName();
			maskPower = Math.getExponent(permissionTypeList.get(i).getMask());
			customTextId = permissionTypeList.get(i).getPermissionContext().getId();

			permissionList.add(new NameValue(permissionTypeList.get(i).getId() + "", permissionLabel, maskPower,
					customTextId, ""));

		}
		return permissionList;
	}

	public List<NameValue> getUserOrRoleSpecificPermissionList(String domainClass, Long id, String userOrRoleName,
			Boolean isPrincipal) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		// TODO Auto-generated method stub
		Object domainObject = prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domainClass, id);

		List<Integer> permissionMaskForOtherUserOrRoleList = customSecurityService
				.getPermissionListForOtherUser(domainObject, userOrRoleName, isPrincipal);

		DomainClass domainClassLocal = domainClassRepo.findByClassName(domainClass);
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClassLocal);
		String permissionMaskArr[] = permissionOnDomain.getPermissionMaskCollection().split(",");

		Map<Integer, PermissionType> permissionTypeMap = getPermissionTypeMapForMaskToLabelChange();
		List<NameValue> permissionList = new ArrayList<NameValue>();
		String permissionLabel = "";
		int customTextId;
		int maskPower;
		int comingMaskValueFromAcl;
		boolean isAllreadyInserted = false;
		for (int i = 0; i < permissionMaskArr.length; i++) {
			isAllreadyInserted = false;
			PermissionType permissionType = permissionTypeMap.get(Integer.valueOf(permissionMaskArr[i]));
			permissionLabel = permissionType.getName();
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();
			for (int j = 0; j < permissionMaskForOtherUserOrRoleList.size(); j++) {
				comingMaskValueFromAcl = permissionMaskForOtherUserOrRoleList.get(j);
				if (comingMaskValueFromAcl == (Integer.valueOf(permissionMaskArr[i]))) {
					permissionList.add(new NameValue(permissionType.getMask() + "", permissionLabel, maskPower,
							customTextId, "checked"));
					isAllreadyInserted = true;
				}
			} // inner for loop
			if (!isAllreadyInserted)
				permissionList.add(
						new NameValue(permissionType.getMask() + "", permissionLabel, maskPower, customTextId, ""));
		}

		return permissionList;
	}

	public List<NameValue> loadCustomBasedPermissions_forLoggedInUser(Integer contextId, String domainClass,
			String domainId, Boolean isPrincipal, String userOrRoleName)
			throws NumberFormatException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		Object domainObject = prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domainClass,
				Long.valueOf(domainId));

		List<Integer> myAclPermissionListOnObject = customSecurityService.getMyAclForObjectAsMask(domainObject);

		DomainClass domainClassLocal = domainClassRepo.findByClassName(domainClass);
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClassLocal);
		String permissionMaskArr[] = permissionOnDomain.getPermissionMaskCollection().split(",");

		List<NameValue> permissionList = new ArrayList<NameValue>();
		String permissionLabel = "";
		int customTextId;
		int maskPower;
		int maskVal;
		boolean isAlreadyInserted = false;
		Map<Integer, PermissionType> permissionTypeMap = getPermissionTypeMapForMaskToLabelChange();
		PermissionType permissionType;
		for (int i = 0; i < permissionMaskArr.length; i++) {
			isAlreadyInserted = false;
			permissionType = permissionTypeMap.get(Integer.valueOf(permissionMaskArr[i]));
			permissionLabel = permissionType.getName();
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();
			for (int j = 0; j < myAclPermissionListOnObject.size(); j++) {
				maskVal = myAclPermissionListOnObject.get(j);
				if (Integer.valueOf(permissionMaskArr[i]) == maskVal) {
					permissionList.add(new NameValue(permissionType.getMask() + "", permissionLabel, maskPower,
							customTextId, "checked"));
					isAlreadyInserted = true;
				}
			} // inner for loop
			if (!isAlreadyInserted)
				permissionList.add(new NameValue(permissionMaskArr[i], permissionLabel, maskPower, customTextId, ""));

		} // outer for loop
		return permissionList;
	}

	public List<NameValue> getPermissionListOfObjectOwner(Object objectWithId)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		// TODO Auto-generated method stub
		List<String> ownerListOfPermission = customACLService.getObjectOwnerPermissionLabel(objectWithId);

		DomainClass domainClassLocal = domainClassRepo.findByClassName(objectWithId.getClass().getCanonicalName());
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClassLocal);
		String permissionMaskArr[] = permissionOnDomain.getPermissionMaskCollection().split(",");

		Map<Integer, PermissionType> permissionTypeMapWithKeyAsMask = getPermissionTypeMapForMaskToLabelChange();

		List<NameValue> permissionList = new ArrayList<NameValue>();
		String permissionLabel = "";
		int customTextId;
		int maskPower;
		String comingLabelValueFromAcl;
		String avialableLabelValue;
		boolean isAllreadyInserted = false;
		PermissionType permissionType;
		String mask;
		for (int i = 0; i < permissionMaskArr.length; i++) {
			isAllreadyInserted = false;
			permissionType = permissionTypeMapWithKeyAsMask.get(Integer.valueOf(permissionMaskArr[i]));
			avialableLabelValue = permissionType.getName();
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();
			mask = permissionType.getMask() + "";
			for (int j = 0; j < ownerListOfPermission.size(); j++) {
				comingLabelValueFromAcl = ownerListOfPermission.get(j);
				if (comingLabelValueFromAcl.equals(avialableLabelValue)) {
					permissionList.add(new NameValue(mask, avialableLabelValue, maskPower, customTextId, "checked"));
					isAllreadyInserted = true;
				}
			} // inner for loop
			if (!isAllreadyInserted)
				permissionList.add(new NameValue(mask, avialableLabelValue, maskPower, customTextId, ""));
		}

		return permissionList;
	}

	public List<NameValue> loadCustomBasedPermissions_forSharingPermission(String contextId, String domainClass,
			Long domainId) {
		// TODO Auto-generated method stub
		DomainClass domainClassLocal = domainClassRepo.findByClassName(domainClass);
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClassLocal);
		String permissionMaskArr[] = permissionOnDomain.getPermissionMaskCollection().split(",");
		List<NameValue> permissionList = new ArrayList<NameValue>();
		String permissionLabel = "";
		int customTextId;
		int maskPower;
		PermissionType permissionType;
		Map<Integer, PermissionType> permissionTypeMapWithKeyAsMask = getPermissionTypeMapForMaskToLabelChange();

		for (int i = 0; i < permissionMaskArr.length; i++) {
			permissionType = permissionTypeMapWithKeyAsMask.get(Integer.valueOf(permissionMaskArr[i]));
			permissionLabel = permissionType.getName();
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();
			if (maskPower < 5 || customTextId == Integer.valueOf(contextId))
				permissionList.add(
						new NameValue(permissionType.getMask() + "", permissionLabel, maskPower, customTextId, ""));
		} // for-loop
		return permissionList;
	}// loadCustomBasedPermissions_forSharingPermission

	public List<NameValue> loadCustomBasedPermissions_forSeeingChangeOwnerPermission(String contextId,
			String domainClass, Long domainId)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		Object domainObject = prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domainClass, domainId);
		
		List<String> ownerListOfPermission = customACLService.getObjectOwnerPermissionLabel(domainObject);

		DomainClass domainClassLocal = domainClassRepo.findByClassName(domainClass);
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClassLocal);
		String permissionMaskArr[] = permissionOnDomain.getPermissionMaskCollection().split(",");

		List<NameValue> permissionList = new ArrayList<NameValue>();
		String permissionLabel = "";
		int customTextId;
		int maskPower;
		String comingLabelValueFromAcl;
		String avialableLabelValue;
		boolean isAllreadyInserted = false;
		PermissionType permissionType;
		Map<Integer, PermissionType> permissionTypeMapWithKeyAsMask = getPermissionTypeMapForMaskToLabelChange();

		for (int i = 0; i < permissionMaskArr.length; i++) {
			isAllreadyInserted = false;
			permissionType = permissionTypeMapWithKeyAsMask.get(Integer.valueOf(permissionMaskArr[i]));
			permissionLabel = permissionType.getName();
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();
			avialableLabelValue = permissionType.getName();
			if (ownerListOfPermission != null) {
				for (int j = 0; j < ownerListOfPermission.size(); j++) {
					comingLabelValueFromAcl = ownerListOfPermission.get(j);
					if (comingLabelValueFromAcl.equals(avialableLabelValue)) {
						permissionList.add(new NameValue(permissionType.getMask() + "", permissionLabel, maskPower,
								customTextId, "checked"));
						isAllreadyInserted = true;
					}
				} // Inner for-loop
				if (!isAllreadyInserted) {
					permissionList.add(
							new NameValue(permissionType.getMask() + "", permissionLabel, maskPower, customTextId, ""));
				}
			}
		} // Outer for-loop

		return permissionList;
	}// loadCustomBasedPermissions_forChangeOwner

	public List<NameValue> loadCustomBasedPermissions_forEditingPermission(String contextId, String domainClass,
			Long domainId, boolean isPrincipal, String userOrRoleName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub

		Object domainObject = prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domainClass, domainId);

		List<Integer> permissionMaskForOtherUserOrRoleList = customSecurityService
				.getPermissionListForOtherUser(domainObject, userOrRoleName, isPrincipal);

		DomainClass domainClassLocal = domainClassRepo.findByClassName(domainClass);
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClassLocal);
		String permissionMaskArr[] = permissionOnDomain.getPermissionMaskCollection().split(",");

		Map<Integer, PermissionType> permissionTypeMap = getPermissionTypeMapForMaskToLabelChange();
		List<NameValue> permissionList = new ArrayList<NameValue>();
		String permissionLabel = "";
		int customTextId;
		int maskPower;
		int comingMaskValueFromAcl;
		boolean isAllreadyInserted = false;
		for (int i = 0; i < permissionMaskArr.length; i++) {
			isAllreadyInserted = false;
			PermissionType permissionType = permissionTypeMap.get(Integer.valueOf(permissionMaskArr[i]));
			permissionLabel = permissionType.getName();
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();
			for (int j = 0; j < permissionMaskForOtherUserOrRoleList.size(); j++) {
				comingMaskValueFromAcl = permissionMaskForOtherUserOrRoleList.get(j);
				if (comingMaskValueFromAcl == (Integer.valueOf(permissionMaskArr[i]))) {
					permissionList.add(new NameValue(permissionType.getMask() + "", permissionLabel, maskPower,
							customTextId, "checked"));
					isAllreadyInserted = true;
				}
			} // inner for loop

			if (!isAllreadyInserted) {
				permissionList.add(
						new NameValue(permissionType.getMask() + "", permissionLabel, maskPower, customTextId, ""));
			}
		}
		return permissionList;
	}

	public List<NameValue> loadPermissionsForSharingBasedOnSelectedRole(String roleId, Long contextId,
			String domainClass) {
		// TODO Auto-generated method stub
		DomainClass domainClasss = domainClassRepo.findByClassName(domainClass);
		AppRole role = roleRepo.getOne(Integer.valueOf(roleId));
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClasss);
		String permissionOnDomainMaskArr[] = permissionOnDomain.getPermissionMaskCollection().split(",");
		String aclPolicyDefinedOnDomainMaskArr[] = {};
		AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainRepo
				.getAclPolicyDefinedOnDomainForRole(domainClasss, role);
		if (aclPolicyDefinedOnDomain != null)
			aclPolicyDefinedOnDomainMaskArr = aclPolicyDefinedOnDomain.getPermissionAssignedMaskCollection().split(",");

		List<NameValue> permissionList = new ArrayList<NameValue>();
		boolean hasThisRecordAlreadyInserted = false;
		String permissionLabel = "";
		int customTextId;
		int maskPower;

		PermissionType permissionType;
		Map<Integer, PermissionType> permissionTypeMap = getPermissionTypeMapForMaskToLabelChange();

		for (int i = 0; i < permissionOnDomainMaskArr.length; i++) {
			hasThisRecordAlreadyInserted = false;
			permissionType = permissionTypeMap.get(Integer.valueOf(permissionOnDomainMaskArr[i]));
			permissionLabel = permissionType.getName();
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();
			for (int j = 0; j < aclPolicyDefinedOnDomainMaskArr.length; j++) {
				if (permissionOnDomainMaskArr[i].equals(aclPolicyDefinedOnDomainMaskArr[j])) {
					permissionList.add(new NameValue(permissionType.getMask() + "", permissionLabel, maskPower,
							customTextId, "checked"));
					hasThisRecordAlreadyInserted = true;
				}
			}
			if (!hasThisRecordAlreadyInserted)
				permissionList.add(
						new NameValue(permissionType.getMask() + "", permissionLabel, maskPower, customTextId, ""));
		}

		return permissionList;
	}

	public List<NameValue> loadPermissionsForSharingBasedOnSelectedUser(String roleId, String usertId, Long contextId,
			String domainClass) {
		// TODO Auto-generated method stub
		DomainClass domainClasss = domainClassRepo.findByClassName(domainClass);
		AppRole role = roleRepo.getOne(Integer.valueOf(roleId));
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClasss);
		String permissionOnDomainMaskArr[] = permissionOnDomain.getPermissionMaskCollection().split(",");
		String aclPolicyDefinedOnDomainMaskArr[] = {};
		AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainRepo
				.getAclPolicyDefinedOnDomainForOwner(domainClasss, true);
		if (aclPolicyDefinedOnDomain != null)
			aclPolicyDefinedOnDomainMaskArr = aclPolicyDefinedOnDomain.getPermissionAssignedMaskCollection().split(",");

		List<NameValue> permissionList = new ArrayList<NameValue>();
		boolean hasThisRecordAlreadyInserted = false;
		String permissionLabel = "";
		int customTextId;
		int maskPower;

		PermissionType permissionType;
		Map<Integer, PermissionType> permissionTypeMap = getPermissionTypeMapForMaskToLabelChange();

		for (int i = 0; i < permissionOnDomainMaskArr.length; i++) {
			hasThisRecordAlreadyInserted = false;
			permissionType = permissionTypeMap.get(Integer.valueOf(permissionOnDomainMaskArr[i]));
			permissionLabel = permissionType.getName();
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();
			for (int j = 0; j < aclPolicyDefinedOnDomainMaskArr.length; j++) {
				if (permissionOnDomainMaskArr[i].equals(aclPolicyDefinedOnDomainMaskArr[j])) {
					permissionList.add(new NameValue(permissionType.getMask() + "", permissionLabel, maskPower,
							customTextId, "checked"));
					hasThisRecordAlreadyInserted = true;
				}
			}
			if (!hasThisRecordAlreadyInserted)
				permissionList.add(
						new NameValue(permissionType.getMask() + "", permissionLabel, maskPower, customTextId, ""));
		}

		return permissionList;
	}

	/**
	 * This method will be used for sharing a permission set with a user
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 ***/
	public boolean shareUserPermission(SharedUserPermissionsDTO sharedUserPermissionsDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		boolean isPermissionShared = false;
		String domain = sharedUserPermissionsDTO.getDomainClassName();
		Long id = Long.valueOf(sharedUserPermissionsDTO.getId());
		Object objectWithId = prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domain, id);
		String userNameOrRoleName = getChangedOrExistingUser(sharedUserPermissionsDTO);
		String permissionArr[] = sharedUserPermissionsDTO.getUserSharedPermissions().split(",");
		List<String> permissionArrList = preparePermissionArrayList(permissionArr);
		boolean isPrincipal = true;// For user
		try {
			customSecurityService.addBulkAclPermissionsForRoleOrUser(objectWithId, userNameOrRoleName,
					permissionArrList, isPrincipal);
			isPermissionShared = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isPermissionShared;
	}

	/**
	 * This method will be used for updating an existing permission set of a user
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 ***/
	public boolean updateUserPermission(SharedUserPermissionsDTO sharedUserPermissionsDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		boolean isPermissionUpdated = false;
		Integer comingContextId = sharedUserPermissionsDTO.getComingContextId();
		String domain = sharedUserPermissionsDTO.getDomainClassName();
		Long id = Long.valueOf(sharedUserPermissionsDTO.getId());
		Object objectWithId = prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domain, id);

		String userNameOrRoleName = getChangedOrExistingUser(sharedUserPermissionsDTO);
		String permissionArr[] = sharedUserPermissionsDTO.getUserSharedPermissions().split(",");

		List<Integer> permissionArrList = preparePermissionArrayListWithIntegerAsMask(permissionArr);
			
		boolean isPrincipal = true;// For user
		try {
			customSecurityService.updateBulkAclPermissionsForRoleOrUser(objectWithId, userNameOrRoleName,
					permissionArrList, isPrincipal, comingContextId);
			isPermissionUpdated = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isPermissionUpdated;
	}

	/**
	 * This method will be used for sharing a permission set with a role
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 ***/
	public boolean shareRolePermission(SharedRolePermissionsDTO sharedRolePermissionsDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		boolean isPermissionShared = false;
		String domain = sharedRolePermissionsDTO.getDomainClassName();
		Long id = Long.valueOf(sharedRolePermissionsDTO.getId());
		Object objectWithId = prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domain, id);

		String userNameOrRoleName = getChangedOrExistingRole(sharedRolePermissionsDTO);
		String permissionArr[] = sharedRolePermissionsDTO.getRoleSharedPermissions().split(",");
		List<String> permissionArrList = preparePermissionArrayList(permissionArr);
		boolean isPrincipal = false;// For user
		try {
			customSecurityService.addBulkAclPermissionsForRoleOrUser(objectWithId, userNameOrRoleName,
					permissionArrList, isPrincipal);
			isPermissionShared = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isPermissionShared;
	}

	/**
	 * This method will be used for updating an existing permission set of a role
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 ***/
	public boolean updateRolePermission(SharedRolePermissionsDTO sharedRolePermissionsDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		boolean isPermissionUpdated = false;
		Integer comingContextId = sharedRolePermissionsDTO.getComingContextId();
		String domain = sharedRolePermissionsDTO.getDomainClassName();

		Long id = Long.valueOf(sharedRolePermissionsDTO.getId());
		Object objectWithId = prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domain, id);

		String userNameOrRoleName = getChangedOrExistingRole(sharedRolePermissionsDTO);

		String permissionArr[] = sharedRolePermissionsDTO.getRoleSharedPermissions().split(",");
		List<Integer> permissionArrList = preparePermissionArrayListWithIntegerAsMask(permissionArr);
		boolean isPrincipal = false;// For role
		try {
			customSecurityService.updateBulkAclPermissionsForRoleOrUser(objectWithId, userNameOrRoleName,
					permissionArrList, isPrincipal, comingContextId);
			isPermissionUpdated = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isPermissionUpdated;
	}

	private List<Integer> preparePermissionArrayListWithIntegerAsMask(String[] permissionArr) {
		// TODO Auto-generated method stub
		List<Integer> preparePermissionArrayList = new ArrayList<Integer>();

		for (int i = 0; i < permissionArr.length; i++) {
			preparePermissionArrayList.add(Integer.valueOf(permissionArr[i]));
		}
		return preparePermissionArrayList;

	}

	public boolean saveChangeOwnerships(ChangeOwnerDTO changeOwnerDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		boolean isOwnerChanged = false;
		String domain = changeOwnerDTO.getDomainClassName();
		Long id = Long.valueOf(changeOwnerDTO.getId());
		Object objectWithId = prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domain, id);
		String newUsername = changeOwnerDTO.getNewUserName().trim();
		String oldUserName = changeOwnerDTO.getOldUserName().trim();
		try {
			MutableAcl mutableAcl = customACLService.changeOwnerForObjectByName(objectWithId, oldUserName, newUsername);
			isOwnerChanged = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isOwnerChanged;
	}

	public boolean deleteUserOrRolePermission(Integer domainId, String domainClassName, String userOrRoleName,
			boolean isPrincipal) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		boolean isUserOrRolePermissionDeleted = false;
		String domain = domainClassName;
		Long id = Long.valueOf(domainId);
		Object objectWithId = prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domain, id);
		try {
			ArrayList<Integer> selectedPermissionList = (ArrayList<Integer>) customSecurityService
					.getPermissionListForOtherUser(objectWithId, userOrRoleName, isPrincipal);
			customSecurityService.removeSelectedAclPermissions(objectWithId, userOrRoleName, selectedPermissionList,
					isPrincipal);
			isUserOrRolePermissionDeleted = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isUserOrRolePermissionDeleted;
	}

	public boolean deleteAllAclsExceptOwnerAndSuperAdminAcls(String domainClassName,String domainId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		boolean hasAllBeenDeleted=false;		
		String domain = domainClassName;
		Long id = Long.valueOf(domainId);
		Object objectWithId = prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domain, id);
		try {
		hasAllBeenDeleted=customSecurityService.deleteAllAclsExceptOwnerAndSuperAdminAcls(objectWithId); 
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return hasAllBeenDeleted;
	}
	
	/**
	 * Private methods for local usages
	 ***/

	public Object prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(String domain, Long id)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		String className = domain.trim();
		Class clz = Class.forName(className);
		Object object = clz.newInstance();
		// with single parameter, return void
		String methodName = "setId";
		Method setNameMethod = object.getClass().getMethod(methodName, Long.class);
		setNameMethod.invoke(object, id); // pass arg
		return object;
	}// prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg

	public Map<Integer, PermissionType> getPermissionTypeMapForMaskToLabelChange() {
		// TODO Auto-generated method stub
		Map<Integer, PermissionType> permissionTypeMap = new LinkedHashMap<Integer, PermissionType>();

		List<PermissionType> permissionTypes = permissionTypeRepo.findAll();
		for (int i = 0; i < permissionTypes.size(); i++) {
			permissionTypeMap.put(permissionTypes.get(i).getMask(), permissionTypes.get(i));
		}
		return permissionTypeMap;
	}// getPermissionTypeMapForMaskToLabelChange
	
	private String getChangedOrExistingUser(SharedUserPermissionsDTO sharedUserPermissionsDTO) {
		// TODO Auto-generated method stub
		String userToWhomPermissionIsAssigned;
		String changedUser = sharedUserPermissionsDTO.getIfUserChanged();
		if (changedUser == null || changedUser.equals(""))
			userToWhomPermissionIsAssigned = sharedUserPermissionsDTO.getUserNameSelected();
		else
			userToWhomPermissionIsAssigned = sharedUserPermissionsDTO.getIfUserChanged();

		return userToWhomPermissionIsAssigned;
	}

	private String getChangedOrExistingRole(SharedRolePermissionsDTO sharedRolePermissionsDTO) {
		// TODO Auto-generated method stub
		String roleToWhomPermissionIsAssigned;
		String changedRole = sharedRolePermissionsDTO.getIfRoleChanged();
		String selectedRole = sharedRolePermissionsDTO.getRoleNameSelected();
		if (changedRole == null || changedRole.equals(selectedRole))
			roleToWhomPermissionIsAssigned = selectedRole;
		else
			roleToWhomPermissionIsAssigned = changedRole;

		return roleToWhomPermissionIsAssigned;
	}

	private List<String> preparePermissionArrayList(String userSharedPermissionsArr[]) {
		// TODO Auto-generated method stub
		List<String> preparePermissionArrayList = new ArrayList<String>();

		for (int i = 0; i < userSharedPermissionsArr.length; i++) {
			preparePermissionArrayList.add(userSharedPermissionsArr[i]);
		}
		return preparePermissionArrayList;
	}

	private Map<String, PermissionType> getPermissionTypeMapForLabelToMaskChange() {
		// TODO Auto-generated method stub
		Map<String, PermissionType> permissionTypeMap = new LinkedHashMap<String, PermissionType>();

		List<PermissionType> permissionTypes = permissionTypeRepo.findAll();
		for (int i = 0; i < permissionTypes.size(); i++) {
			permissionTypeMap.put(permissionTypes.get(i).getName(), permissionTypes.get(i));
		}
		return permissionTypeMap;
	}// getPermissionTypeMapForMaskToLabelChange

}
