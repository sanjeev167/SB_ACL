/**
 * 
 */
package com.pvt.sec.acl.adm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.acl.adm.dto.OwnerPermissionDTO;
import com.pvt.sec.acl.adm.dto.PermissionGridDto;
import com.pvt.sec.acl.adm.dto.RolePermissionDTO;
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
public class AclPolicyDefinedOnDomainService {

	@Autowired
	AclPolicyDefinedOnDomainRepo aclPolicyDefinedOnDomainRepo;
	@Autowired
	PermissionTypeRepo permissionTypeRepo;
	@Autowired
	PermissionOnDomainRepo permissionOnDomainRepo;
	@Autowired
	DomainClassRepo domainClassRepo;
	@Autowired
	AppRoleRepository roleRepo;

	/**
	 * Grid related services
	 **/
	public List<PermissionGridDto> getAclPolicyDefinedOnAllDomain() {

		List<AclPolicyDefinedOnDomain> aclPolicyDefinedOnDomainList = aclPolicyDefinedOnDomainRepo.getAllAclPolicy();
		AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain = null;
		List<PermissionGridDto> permissionGridDtoList = new ArrayList<PermissionGridDto>();
		PermissionGridDto permissionGridDto = null;
		String assignedPermissionArr[] = null;
		ArrayList<NameValue> permissionList = new ArrayList<NameValue>();
		NameValue nameValue;

		for (int i = 0; i < aclPolicyDefinedOnDomainList.size(); i++) {
			aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainList.get(i);
			permissionGridDto = new PermissionGridDto();
			permissionGridDto.setDomainPolicyId(aclPolicyDefinedOnDomain.getId() + "");
			permissionGridDto.setDomainName(aclPolicyDefinedOnDomain.getDomainClass().getClassName());
			if (aclPolicyDefinedOnDomain.getIsPrincipal()) {
				permissionGridDto.setOwnerOrRole("<strong style='color:red;'>Owner</strong>");
			} else {
				permissionGridDto.setOwnerOrRole(aclPolicyDefinedOnDomain.getAppRole().getRoleName());
			}
			assignedPermissionArr = aclPolicyDefinedOnDomain.getPermissionAssignedMaskCollection().split(",");
			// Take out permission as label and prepare its list for each domain found in
			// the list.
			permissionList = new ArrayList<NameValue>();
			for (int j = 0; j < assignedPermissionArr.length; j++) {
				nameValue = new NameValue();
				nameValue.setId(assignedPermissionArr[j]);
				nameValue.setName(getPermissionLabel(assignedPermissionArr[j]));
				nameValue.setAllReadyAssigned("checked");
				permissionList.add(nameValue);
			}
			permissionGridDto.setPermissionList(permissionList);
			permissionGridDtoList.add(permissionGridDto);
			// printPermissionDetails(permissionGridDto);
		}
		return permissionGridDtoList;
	}

	public List<NameValue> loadPermissionWithSelectedDomainPolicyId(String domainPolicyId, String domainClassName) {
		// TODO Auto-generated method stub

		AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainRepo
				.getOne(Integer.valueOf(domainPolicyId));
		String permissionMaskAssignedArr[] = aclPolicyDefinedOnDomain.getPermissionAssignedMaskCollection().split(",");

		List<NameValue> permissionList = getPermissionListCompatibleForCheckBox(permissionMaskAssignedArr,
				domainClassName);

		return permissionList;
	}

	public boolean deletePermissionPolicy(String domainPolicyId) {
		// TODO Auto-generated method stub
		aclPolicyDefinedOnDomainRepo.deleteById(Integer.valueOf(domainPolicyId));
		return true;
	}

	public List<NameValue> loadDomainComboWithSelectedDomainOfGrid(String domainClassName) {
		// TODO Auto-generated method stub

		List<DomainClass> domainClassList = domainClassRepo.findAll();
		DomainClass domainClass = domainClassRepo.findByClassName(domainClassName);
		List<NameValue> domainList = new ArrayList<NameValue>();
		NameValue nameValue;
		String allReadyAssigned = "selected";

		for (int i = 0; i < domainClassList.size(); i++) {
			nameValue = new NameValue();
			nameValue.setId(domainClassList.get(i).getId() + "");
			nameValue.setName(domainClassList.get(i).getClassName());
			if (domainClassList.get(i).equals(domainClass)) {
				nameValue.setAllReadyAssigned(allReadyAssigned);
			}
			domainList.add(nameValue);
		}

		return domainList;
	}

	public List<NameValue> loadRoleComboWithSelectedRoleOfGrid(String ownerOrRole) {
		// TODO Auto-generated method stub

		List<AppRole> roles = roleRepo.findAll();

		List<NameValue> roleList = new ArrayList<NameValue>();
		roleList.add(new NameValue("","-select-"));
		NameValue nameValue;		
		String allReadyAssigned = "selected";

		for (int i = 0; i < roles.size(); i++) {
			nameValue = new NameValue();
			nameValue.setId(roles.get(i).getId() + "");
			nameValue.setName(roles.get(i).getRoleName());
			if (roles.get(i).getRoleName().equals(ownerOrRole)) {
				nameValue.setAllReadyAssigned(allReadyAssigned);
			}
			roleList.add(nameValue);
		}

		return roleList;
	}

	/**
	 * Role Section services
	 */
	public List<NameValue> loadDomainAndContextBasedAvailablePermission_roleSection(Long domainId, String contextId) {
		// TODO Auto-generated method stub
		List<NameValue> permissionList;
		DomainClass domainClassLocal = domainClassRepo.getOne(domainId);
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClassLocal);
		if (permissionOnDomain != null) {
			String permissionDefinedOnDomain[] = permissionOnDomain.getPermissionMaskCollection().split(",");
			permissionList = getDomainPermissionListWithNoPreSelection(permissionDefinedOnDomain);
		} else {// Load a context based blank permission list
			permissionList = loadAvailableDomainPermission(contextId,domainClassLocal);
		}
		return permissionList;
	}

	public List<NameValue> loadPermissionWith_Domain_Role_PermissionContext_roleSection(Long domainId, int roleId,
			int contextId) {
		// TODO Auto-generated method stub
		List<NameValue> permissionList = null;
		String permissionDefinedOnDomainArr[] = {};
		String[] aclPolicyMaskArr = {};
		DomainClass domainClassLocal = domainClassRepo.getOne(domainId);
		AppRole role = roleRepo.getOne(roleId);
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClassLocal);
		AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain = null;
		if (permissionOnDomain != null) {
			permissionDefinedOnDomainArr = permissionOnDomain.getPermissionMaskCollection().split(",");
			aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainRepo.getAclPolicyDefinedOnDomainForRole(domainClassLocal,
					role);
			if (aclPolicyDefinedOnDomain != null) {
				aclPolicyMaskArr = aclPolicyDefinedOnDomain.getPermissionAssignedMaskCollection().split(",");
				permissionList = getDomainPermissionListWithPreSelection(permissionDefinedOnDomainArr,
						aclPolicyMaskArr);
			}else {				
				permissionList=loadAvailableDomainPermission(contextId+"",domainClassLocal);
			}
		} 
		return permissionList;
	}

	public boolean saveRolePermissionPolicy(RolePermissionDTO rolePermissionDTO) {
		// TODO Auto-generated method stub
		boolean isSaved = true;
		DomainClass domainClass = domainClassRepo.getOne(Long.valueOf(rolePermissionDTO.getDomainId()));
		AppRole role = roleRepo.getOne(Integer.valueOf(rolePermissionDTO.getRoleId()));
		AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainRepo
				.getAclPolicyDefinedOnDomainForRole(domainClass, role);
		if (!rolePermissionDTO.getRolePermissions().equals("")) {
			if (aclPolicyDefinedOnDomain != null) {
				aclPolicyDefinedOnDomain.setPermissionAssignedMaskCollection(rolePermissionDTO.getRolePermissions());
			} else {
				aclPolicyDefinedOnDomain = new AclPolicyDefinedOnDomain();
				aclPolicyDefinedOnDomain.setDomainClass(domainClass);
				
				aclPolicyDefinedOnDomain.setPermissionAssignedMaskCollection(rolePermissionDTO.getRolePermissions());
				aclPolicyDefinedOnDomain.setIsPrincipal(false);
				aclPolicyDefinedOnDomain.setAppRole(role);
			}
			aclPolicyDefinedOnDomainRepo.saveAndFlush(aclPolicyDefinedOnDomain);
		} else {
			if (aclPolicyDefinedOnDomain != null) {
				aclPolicyDefinedOnDomainRepo.delete(aclPolicyDefinedOnDomain);
			} else {
				isSaved = false;
			}
		}
		return isSaved;
	}

	public boolean updateRolePermissionPolicy(RolePermissionDTO rolePermissionDTO) {
		// TODO Auto-generated method stub
		boolean isUpdated = true;
		if (rolePermissionDTO.getRolePermissions().equals("")) {
			aclPolicyDefinedOnDomainRepo.deleteById(Integer.valueOf(rolePermissionDTO.getPolicyId()));
		} else {			
			AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainRepo
					.getOne(Integer.valueOf(rolePermissionDTO.getPolicyId()));
			aclPolicyDefinedOnDomain.setPermissionAssignedMaskCollection(rolePermissionDTO.getRolePermissions());

		}

		return isUpdated;
	}

	/**
	 * Owner Section services
	 **/
	public boolean saveOwnerPermissionPolicy(OwnerPermissionDTO ownerPermissionDTO) {
		// TODO Auto-generated method stub
		boolean isSaved = true;
		DomainClass domainClass = domainClassRepo.getOne(Long.valueOf(ownerPermissionDTO.getDomainId()));
		AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainRepo
				.getAclPolicyDefinedOnDomainForOwner(domainClass, true);// Owner fetching

		if (!ownerPermissionDTO.getOwnerPermissions().equals("")) {
			if (aclPolicyDefinedOnDomain != null) {
				aclPolicyDefinedOnDomain.setPermissionAssignedMaskCollection(ownerPermissionDTO.getOwnerPermissions());
			} else {
				aclPolicyDefinedOnDomain = new AclPolicyDefinedOnDomain();
				aclPolicyDefinedOnDomain.setDomainClass(domainClass);
				aclPolicyDefinedOnDomain.setPermissionAssignedMaskCollection(ownerPermissionDTO.getOwnerPermissions());
				aclPolicyDefinedOnDomain.setIsPrincipal(true);
			}
			aclPolicyDefinedOnDomainRepo.saveAndFlush(aclPolicyDefinedOnDomain);
		} else {
			if (aclPolicyDefinedOnDomain != null) {
				aclPolicyDefinedOnDomainRepo.delete(aclPolicyDefinedOnDomain);
			} else {
				isSaved = false;
			}
		}
		return isSaved;
	}

	public boolean updateOwnerPermissionPolicy(OwnerPermissionDTO ownerPermissionDTO) {
		// TODO Auto-generated method stub
		boolean isUpdated = true;
		if (ownerPermissionDTO.getOwnerPermissions().equals("")) {
			aclPolicyDefinedOnDomainRepo.deleteById(Integer.valueOf(ownerPermissionDTO.getPolicyId()));
		} else {
			AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainRepo
					.getOne(Integer.valueOf(ownerPermissionDTO.getPolicyId()));

			aclPolicyDefinedOnDomain.setPermissionAssignedMaskCollection(ownerPermissionDTO.getOwnerPermissions());

		}

		return isUpdated;
	}

	public List<NameValue> loadOwnerPermissionBasedOnDomainAndContextId(String domainId, String contextId,
			boolean ownerOrRole) {
		List<NameValue> permissionList = new ArrayList<NameValue>();
		String permissionMaskArr[] = {};
		AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain = null;
		PermissionOnDomain permissionOnDomain = null;
		try {
			DomainClass domainClass = domainClassRepo.getOne(Long.valueOf(domainId));
			permissionOnDomain = permissionOnDomainRepo.getDomainPermission(domainClass);
			aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainRepo.getAclPolicyDefinedOnDomainForOwner(domainClass,
					ownerOrRole);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (aclPolicyDefinedOnDomain != null)
			permissionMaskArr = aclPolicyDefinedOnDomain.getPermissionAssignedMaskCollection().split(",");

		String permissionLabel = "";
		String allReadyAssigned = "checked";
		int customTextId = 0;
		int maskPower;
		PermissionType permissionType;
		String permissionAvalaibleOnDomain[] = {};
		if (permissionOnDomain != null)
			permissionAvalaibleOnDomain = permissionOnDomain.getPermissionMaskCollection().split("[,]");
		boolean isInsertedInList;
		for (int i = 0; i < permissionAvalaibleOnDomain.length; i++) {
			isInsertedInList = false;
			permissionLabel = getPermissionLabel(permissionAvalaibleOnDomain[i]);
			maskPower = Math.getExponent(Float.valueOf(permissionAvalaibleOnDomain[i]));
			permissionType = permissionTypeRepo.findByMask(Integer.valueOf(permissionAvalaibleOnDomain[i]));

			if (permissionType != null)
				customTextId = permissionType.getPermissionContext().getId();
			if (permissionMaskArr.length != 0) {
				for (int j = 0; j < permissionMaskArr.length; j++) {
					if (permissionMaskArr[j].equals(permissionAvalaibleOnDomain[i])) {
						permissionList.add(new NameValue(permissionAvalaibleOnDomain[i], permissionLabel, maskPower,
								customTextId, allReadyAssigned));
						isInsertedInList = true;
						break;
					}
				} // inner for loop
			} // permissionMaskArr.length!=0
			if (!isInsertedInList)
				permissionList.add(
						new NameValue(permissionAvalaibleOnDomain[i], permissionLabel, maskPower, customTextId, ""));
		}
		return permissionList;
	}

	/**
	 * Common Section
	 **/
	public List<NameValue> loadAvailableDomainPermission(String contextId,DomainClass domainClass) {
		// TODO Auto-generated method stub
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.getDomainPermission(domainClass);
		List<NameValue> permissionList = new ArrayList<NameValue>();
		String permissionLabel = "";
		int customTextId;
		int maskPower;PermissionType permissionType;
		String permissionOnDomainArr[]= {};
		if(permissionOnDomain!=null)
		 permissionOnDomainArr=permissionOnDomain.getPermissionMaskCollection().split(",");
		for (int i = 0; i < permissionOnDomainArr.length; i++) {
		     permissionType=permissionTypeRepo.findByMask(Integer.valueOf(permissionOnDomainArr[i]));
			permissionLabel = permissionType.getName();
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();			
			if (maskPower < 5 || customTextId == (Integer.valueOf(contextId))) {
				permissionList.add(new NameValue(permissionType.getMask() + "", permissionLabel, maskPower,
						customTextId, ""));
			}
		}
		return permissionList;
	}

	public List<NameValue> loadAvailableDomainPermission(String domainId, String contextId) {
		DomainClass domainClass=domainClassRepo.getOne(Long.valueOf(domainId));
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.getDomainPermission(domainClass);
				List<NameValue> permissionList = new ArrayList<NameValue>();
				String permissionLabel = "";
				int customTextId;
				int maskPower;PermissionType permissionType;String permissionOnDomainArr[]= {};
				if(permissionOnDomain!=null)
				 permissionOnDomainArr=permissionOnDomain.getPermissionMaskCollection().split(",");
				for (int i = 0; i < permissionOnDomainArr.length; i++) {
				     permissionType=permissionTypeRepo.findByMask(Integer.valueOf(permissionOnDomainArr[i]));
					permissionLabel = permissionType.getName();
					maskPower = Math.getExponent(permissionType.getMask());
					customTextId = permissionType.getPermissionContext().getId();			
					if (maskPower < 5 || customTextId == (Integer.valueOf(contextId))) {
						permissionList.add(new NameValue(permissionType.getMask() + "", permissionLabel, maskPower,
								customTextId, ""));
					}
				}
				return permissionList;
	}
	
	
	
	public List<NameValue> getOwnerOrRolePermission(Integer domainClassId, Boolean isPrincipal, Integer roleId) {
		// TODO Auto-generated method stub
		AppRole role;
		DomainClass domainClassLocal = domainClassRepo.getOne(Long.valueOf(domainClassId + ""));
		// findByClassName(domainClass);
		AclPolicyDefinedOnDomain aclPolicyDefinedOnDomain;
		if (isPrincipal) {
			aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainRepo
					.getAclPolicyDefinedOnDomainForOwner(domainClassLocal, isPrincipal);
		} else {
			role = roleRepo.getOne(roleId);
			aclPolicyDefinedOnDomain = aclPolicyDefinedOnDomainRepo.getAclPolicyDefinedOnDomainForRole(domainClassLocal,
					role);
		}
		String permissionMaskAssignedArr[] = null;
		if (aclPolicyDefinedOnDomain != null)
			permissionMaskAssignedArr = aclPolicyDefinedOnDomain.getPermissionAssignedMaskCollection().split(",");

		List<NameValue> permissionList = getPermissionListCompatibleForCheckBox(permissionMaskAssignedArr,
				domainClassLocal.getClassName());

		return permissionList;
	}

	/***
	 * Private Methods for supporting above methods
	 **/
	private List<NameValue> getDomainPermissionListWithPreSelection(String[] permissionDefinedOnDomain,
			String[] aclPolicyMaskArr) {
		String permissionLabel = "";
		String allReadyAssigned = "checked";
		int customcontextId;
		int maskPower;
		PermissionType permissionType;
		List<NameValue> permissionList = new ArrayList<NameValue>();
		boolean isAlreadyInsertedInList;
		for (int i = 0; i < permissionDefinedOnDomain.length; i++) {
			isAlreadyInsertedInList = false;
			permissionType = permissionTypeRepo.findByMask(Integer.valueOf(permissionDefinedOnDomain[i]));
			permissionLabel = permissionType.getName();
			maskPower = Math.getExponent(permissionType.getMask());
			customcontextId = permissionType.getPermissionContext().getId();
			for (int j = 0; j < aclPolicyMaskArr.length; j++) {
				if (aclPolicyMaskArr[j].equals(permissionType.getMask() + "")) {
					permissionList.add(new NameValue(permissionType.getId() + "", permissionLabel, maskPower,
							customcontextId, allReadyAssigned));
					isAlreadyInsertedInList = true;
					break;
				}
			} // inner for loop inner
			if (!isAlreadyInsertedInList) {
				permissionList.add(
						new NameValue(permissionType.getId() + "", permissionLabel, maskPower, customcontextId, ""));
			}
		} // outer for loop inner
		return permissionList;
	}

	private List<NameValue> getDomainPermissionListWithNoPreSelection(String[] permissionMaskArr) {
		List<NameValue> permissionList = new ArrayList<NameValue>();
		String maskValue;
		int maskPower;
		int customTextId;
		String permissionLabel;
		PermissionType permissionType;
		for (int i = 0; i < permissionMaskArr.length; i++) {
			permissionType = permissionTypeRepo.findByMask(Integer.valueOf(permissionMaskArr[i]));
			maskValue = permissionType.getMask() + "";
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();
			permissionLabel = permissionType.getName();
			permissionList.add(new NameValue(maskValue, permissionLabel, maskPower, customTextId, ""));
		}
		return permissionList;
	}

	private List<NameValue> getPermissionListCompatibleForCheckBox(String[] permissionMaskAssignedArr,
			String domainClassName) {

		DomainClass domainClass = domainClassRepo.findByClassName(domainClassName);
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClass);
		String permissionAvlOnDomain[] = {};
		if (permissionOnDomain != null)
			permissionAvlOnDomain = permissionOnDomain.getPermissionMaskCollection().split(",");
		List<NameValue> permissionList = new ArrayList<NameValue>();
		String maskValue;
		String permissionLabel;
		String alreadySelected = "checked";
		Boolean alreadyAssigned;
		int maskPower;
		int customTextId;
		PermissionType permissionType;
		for (int i = 0; i < permissionAvlOnDomain.length; i++) {
			alreadyAssigned = false;
			permissionType = permissionTypeRepo.findByMask(Integer.valueOf(permissionAvlOnDomain[i]));
			maskValue = permissionType.getMask() + "";
			maskPower = Math.getExponent(permissionType.getMask());
			customTextId = permissionType.getPermissionContext().getId();
			permissionLabel = permissionType.getName();
			for (int j = 0; permissionMaskAssignedArr != null && j < permissionMaskAssignedArr.length; j++) {
				if (maskValue.equals(permissionMaskAssignedArr[j])) {
					permissionList
							.add(new NameValue(maskValue, permissionLabel, maskPower, customTextId, alreadySelected));
					alreadyAssigned = true;
				}
			}
			if (!alreadyAssigned)
				permissionList.add(new NameValue(maskValue, permissionLabel, maskPower, customTextId, ""));
		}
		return permissionList;
	}

	private String getPermissionLabel(String permissionMaskValue) {
		// TODO Auto-generated method stub
		String permissionLabel = null;
		List<PermissionType> permissionTypes = permissionTypeRepo.findAll();
		for (int i = 0; i < permissionTypes.size(); i++) {
			if ((permissionTypes.get(i).getMask() + "").equals(permissionMaskValue)) {
				permissionLabel = permissionTypes.get(i).getName();
			}
		}
		return permissionLabel;
	}

	private void printPermissionDetails(PermissionGridDto permissionGridDto) {
		// TODO Auto-generated method stub
		System.out.println("id  = " + permissionGridDto.getDomainId());
		System.out.println("Name  = " + permissionGridDto.getDomainName());
		System.out.println("Permission  = ");
		for (int k = 0; k < permissionGridDto.getPermissionList().size(); k++) {
			System.out.println("\t\tPermission = " + permissionGridDto.getPermissionList().get(k).getName());
		}
		System.out.println("##########################");
	}

	

}
