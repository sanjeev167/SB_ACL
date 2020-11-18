/**
 * 
 */
package com.pvt.sec.acl.adm.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.acl.adm.dto.DomainPermissionDTO;
import com.pvt.sec.acl.adm.dto.PermissionDefinitionDTO;
import com.pvt.sec.acl.adm.dto.PermissionGridDto;
import com.pvt.sec.acl.adm.entity.DomainClass;
import com.pvt.sec.acl.adm.entity.PermissionContext;
import com.pvt.sec.acl.adm.entity.PermissionOnDomain;
import com.pvt.sec.acl.adm.entity.PermissionType;
import com.pvt.sec.acl.adm.repo.AclPolicyDefinedOnDomainRepo;
import com.pvt.sec.acl.adm.repo.DomainClassRepo;
import com.pvt.sec.acl.adm.repo.PermissionContextRepo;
import com.pvt.sec.acl.adm.repo.PermissionOnDomainRepo;
import com.pvt.sec.acl.adm.repo.PermissionTypeRepo;

import com.pvt.sec.acl.util.CommaCleaner;
import com.pvt.sec.rbac.repo.AppRoleRepository;
import com.share.NameValue;

/**
 * @author Sanjeev
 *
 */
@Service
@Transactional("appTransactionManager")
public class PermissionOnDomainService {

	@Autowired
	PermissionOnDomainRepo permissionOnDomainRepo;
	@Autowired
	PermissionTypeRepo permissionTypeRepo;
	@Autowired
	AclPolicyDefinedOnDomainRepo aclPolicyDefinedOnDomainRepo;
	@Autowired
	DomainClassRepo domainClassRepo;
	@Autowired
	AppRoleRepository roleRepo;

	@Autowired
	PermissionContextRepo permissionContextRepo;

	public List<NameValue> getPermissionListOnDomain(Integer domainId) {
		// TODO Auto-generated method stub
		PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.getOne(domainId);

		String permissionMaskArr[] = permissionOnDomain.getPermissionMaskCollection().split(",");

		List<PermissionType> permissionTypes = permissionTypeRepo.findAll();

		ArrayList<NameValue> permissionDomainList = new ArrayList<NameValue>();

		for (int i = 0; i < permissionMaskArr.length; i++) {
			String permissionLabel = "";
			for (int j = 0; j < permissionTypes.size(); j++) {
				if ((permissionTypes.get(j).getMask() + "").equals(permissionMaskArr[i])) {
					permissionLabel = permissionTypes.get(j).getName();
					break;
				}
			}
			permissionDomainList.add(new NameValue(permissionMaskArr[i], permissionLabel));
		}
		return permissionDomainList;
	}

	public List<NameValue> getPermissionLoadingInDomainContainerOnDomainChange(Integer domainId) {
		ArrayList<NameValue> permissionDomainList = new ArrayList<NameValue>();
		String permissionMaskArr[] = {};
		DomainClass domainClass = domainClassRepo.getOne(Long.valueOf(domainId));
		try {
			PermissionOnDomain permissionOnDomain = permissionOnDomainRepo.getDomainPermission(domainClass);

			if (permissionOnDomain != null)
				permissionMaskArr = permissionOnDomain.getPermissionMaskCollection().split(",");
		} catch (EntityNotFoundException ex) {
			// No entity has been found.
			;
			// ex.printStackTrace();
		}
		List<PermissionType> permissionTypes = permissionTypeRepo.findAll();

		String permissionLabel = "";
		String allReadyAssigned = "checked";
		int customTextId;
		int maskPower;
		boolean isInsertedInList;
		for (int i = 0; i < permissionTypes.size(); i++) {
			isInsertedInList = false;
			permissionLabel = permissionTypes.get(i).getName();
			maskPower = Math.getExponent(permissionTypes.get(i).getMask());
			customTextId = permissionTypes.get(i).getPermissionContext().getId();

			for (int j = 0; j < permissionMaskArr.length; j++) {
				if (permissionMaskArr[j].equals(permissionTypes.get(i).getMask() + "")) {

					permissionDomainList.add(new NameValue(
							permissionLabel + "_" + customTextId + "_" + permissionTypes.get(i).getMask() + "",
							permissionLabel, maskPower, customTextId, allReadyAssigned));
					isInsertedInList = true;
					break;
				}
			}
			if (!isInsertedInList)
				permissionDomainList.add(new NameValue(
						permissionLabel + "_" + customTextId + "_" + permissionTypes.get(i).getMask() + "",
						permissionLabel, maskPower, customTextId, ""));

		}

		return permissionDomainList;
	}

	public List<PermissionGridDto> getPermissionDefinedOfAllDomain() {

		List<PermissionOnDomain> permissionOnDomainList = permissionOnDomainRepo.findAll();

		PermissionOnDomain permissionOnDomain = null;
		List<PermissionGridDto> permissionGridDtoList = new ArrayList<PermissionGridDto>();
		PermissionGridDto permissionGridDto = null;
		String definedPermissionArr[] = null;
		ArrayList<NameValue> permissionList = new ArrayList<NameValue>();
		NameValue nameValue;

		for (int i = 0; i < permissionOnDomainList.size(); i++) {
			permissionOnDomain = permissionOnDomainList.get(i);
			permissionGridDto = new PermissionGridDto();
			permissionGridDto.setDomainId(permissionOnDomain.getDomainClass().getId() + "");
			permissionGridDto.setDomainName(permissionOnDomain.getDomainClass().getClassName());

			definedPermissionArr = permissionOnDomain.getPermissionMaskCollection().split(",");
			// Take out permission as label and prepare its list for each domain found in
			// the list.
			permissionList = new ArrayList<NameValue>();
			for (int j = 0; j < definedPermissionArr.length; j++) {
				nameValue = new NameValue();
				nameValue.setId(definedPermissionArr[j]);
				nameValue.setName(getPermissionLabel(definedPermissionArr[j]));
				nameValue.setAllReadyAssigned("checked");
				permissionList.add(nameValue);
			}
			permissionGridDto.setPermissionList(permissionList);
			permissionGridDtoList.add(permissionGridDto);
			// printPermissionDetails(permissionGridDto);
		}
		return permissionGridDtoList;
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

	public boolean saveOrUpdateDomainPermissionAssociation(DomainPermissionDTO domainPermissionDTO) {
		// TODO Auto-generated method stub
		String contextId = domainPermissionDTO.getContextId();
		DomainClass domainClass = domainClassRepo.getOne(Long.valueOf(domainPermissionDTO.getDomainId()));
		PermissionOnDomain permissionOnDomain;
		permissionOnDomain = permissionOnDomainRepo.getDomainPermission(domainClass);
		String existingPermissions;
		String updatedPermissionString;
		if (permissionOnDomain == null) {// New domain permission association
			permissionOnDomain = new PermissionOnDomain();
			permissionOnDomain.setDomainClass(domainClass);
			permissionOnDomain.setPermissionMaskCollection(domainPermissionDTO.getTotalPermission());
			permissionOnDomainRepo.saveAndFlush(permissionOnDomain);
		} else {
			// Update domain permission association.
			// First take out existing permission and merge it with the coming permission
			existingPermissions = permissionOnDomain.getPermissionMaskCollection();
			updatedPermissionString = prepareExactMaskValuesWithExistingPermission(
					domainPermissionDTO.getTotalPermission(), existingPermissions, contextId);

			permissionOnDomain.setPermissionMaskCollection(updatedPermissionString);
		}

		return true;
	}

	public boolean deleteDomainPermissionAssignment(String domainId) {
		// TODO Auto-generated method stub
		DomainClass domainClass = domainClassRepo.getOne(Long.valueOf(domainId));
		permissionOnDomainRepo.deleteByDomainClass(domainClass);

		return true;
	}

	private String prepareExactMaskValuesWithExistingPermission(String totalPermission, String existingPermissions,
			String contextId) {

		PermissionContext permissionContext = permissionContextRepo.getOne(Integer.valueOf(contextId));
		List<PermissionType> permissionTypeList = permissionTypeRepo.findByPermissionContext(permissionContext);
		// Find those permission mask in the existing permission list which are not of
		// coming domain

		existingPermissions = new CommaCleaner().cleanUpCommas(existingPermissions);// Remove starting or trailing
																					// commas
		String existingPermissionsExactMaskArr[] = existingPermissions.split("[,]");

		totalPermission = new CommaCleaner().cleanUpCommas(totalPermission);// Remove starting or trailing commas

		// System.out.println("ExistingPermission Mask= "+existingPermissions);
		// System.out.println("ComingPermission Mask
		// ="+prepareExactMaskValuesFromExponent(totalPermission));

		// Find out those permissions which are neither of base nor of the current
		// domain
		// System.out.println("\tComparing Started.");
		for (int i = 0; i < existingPermissionsExactMaskArr.length; i++) {
			// System.out.println("\t\t existingPermission
			// Mask="+existingPermissionsExactMaskArr[i]);
			for (int j = 0; j < permissionTypeList.size(); j++) {
				// System.out.println("\t\t\t permissionTypeList
				// Mask="+permissionTypeList.get(j).getMask());
				if ((Integer.valueOf(existingPermissionsExactMaskArr[i]) < 17)
						|| existingPermissionsExactMaskArr[i].equals(permissionTypeList.get(j).getMask() + "")) {

					// System.out.println("\t\t\tMatch found ="+existingPermissionsExactMaskArr[i]);
					existingPermissionsExactMaskArr[i] = "0";
					break;

				}

			}
		}

		// Now existingPermissionsArr will have non zero masks which needs to be
		// preserved.
		// Collect these non zero mask as a string
		String unmatchedMaskString = "";

		for (int i = 0; i < existingPermissionsExactMaskArr.length; i++) {
			if (!existingPermissionsExactMaskArr[i].equals("0"))
				unmatchedMaskString = unmatchedMaskString + "," + existingPermissionsExactMaskArr[i];
		}
		// Now remove starting and trailing commas
		unmatchedMaskString = new CommaCleaner().cleanUpCommas(unmatchedMaskString);// Remove starting or trailing
																					// commas

		// System.out.println("Unmatched Existing Mask String = "+unmatchedMaskString);
		String newFinalCommaSeperatedMask = totalPermission + "," + unmatchedMaskString;

		// System.out.println("newFinalCommaSeperatedMask
		// ="+newFinalCommaSeperatedMask);
		return newFinalCommaSeperatedMask;
	}

	public String savePermissionDefinition(PermissionDefinitionDTO permissionDefinitionDTO) {
		// TODO Auto-generated method stub
		PermissionContext permissionContext = permissionContextRepo
				.getOne(permissionDefinitionDTO.getPermissionContextId());
		List<PermissionType> permissionTypeList = permissionTypeRepo.findByPermissionContext(permissionContext);

		// First check
		int permissionCount = permissionTypeList.size();
		if (permissionCount == 10)
			return "Only 10 permission is allowed in a context.<br>Make a new context or enter in other context.";

		// Second check for duplicate entry
		PermissionType permissionTypeLocal = permissionTypeRepo
				.findByName(permissionDefinitionDTO.getPermissionLabel());
		if (permissionTypeLocal != null)
			return "This permission label already exists.";

		int greatestAvlMaskValue = permissionTypeRepo.maxPermissionMask();
		PermissionType permissionType = new PermissionType();
		permissionType.setIsBasePermission(false);
		permissionType.setName(permissionDefinitionDTO.getPermissionLabel());
		permissionType.setPermissionContext(permissionContext);
		if (greatestAvlMaskValue == 0) {
			permissionType.setMask(1);
		} else {
			permissionType.setMask(greatestAvlMaskValue * 2);
		}

		permissionTypeRepo.saveAndFlush(permissionType);
		return "Permission saved successfully.";
	}

	public String updatePermissionDefinition(PermissionDefinitionDTO permissionDefinitionDTO) {
		// TODO Auto-generated method stub
		// First check for duplicate entry
		PermissionType permissionTypeLocal = permissionTypeRepo
				.findByName(permissionDefinitionDTO.getPermissionLabel());
		if (permissionTypeLocal != null)
			return "This permission label already exists.";

		PermissionContext permissionContext = permissionContextRepo
				.getOne(permissionDefinitionDTO.getPermissionContextId());
		PermissionType permissionType = permissionTypeRepo.getOne(permissionDefinitionDTO.getRecordId());
		permissionType.setName(permissionDefinitionDTO.getPermissionLabel());
		permissionType.setPermissionContext(permissionContext);
		return "Permission updated successfully.";
	}
    
	private String prepareExactMaskValuesFromExponent(String totalPermission) {

		// TODO Auto-generated method stub
		// System.out.println("totalPermission = "+totalPermission);
		String maskArr[] = totalPermission.split("[',']");
		String finalCommaSeperatedMask = "";
		int expo;
		int mask;

		for (int i = 0; i < maskArr.length; i++) {
			// System.out.println("maskArr[i] = "+maskArr[i]);
			expo = Integer.valueOf(maskArr[i]) - 1;
			// System.out.println("expo = "+expo);
			mask = (int) Math.pow(2, expo);
			// System.out.println("mask = "+mask);
			if (i == 0)
				finalCommaSeperatedMask = mask + "";
			else
				finalCommaSeperatedMask = finalCommaSeperatedMask + "," + mask;
		}
		finalCommaSeperatedMask = new CommaCleaner().cleanUpCommas(finalCommaSeperatedMask);// Remove starting or
																							// trailing commas
		return finalCommaSeperatedMask;
	}

}
