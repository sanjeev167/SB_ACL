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

import com.pvt.sec.acl.adm.entity.DomainClass;
import com.pvt.sec.acl.adm.entity.PermissionOnDomain;
import com.pvt.sec.acl.adm.entity.PermissionType;
import com.pvt.sec.acl.adm.repo.AclPolicyDefinedOnDomainRepo;
import com.pvt.sec.acl.adm.repo.DomainClassRepo;
import com.pvt.sec.acl.adm.repo.PermissionOnDomainRepo;
import com.pvt.sec.acl.adm.repo.PermissionTypeRepo;
import com.pvt.sec.rbac.repo.AppRoleRepository;
import com.share.NameValue;


/**
 * @author Sanjeev
 *
 */
@Service
@Transactional("appTransactionManager")
public class PermissionTypeService {

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

	public List<NameValue> loadBaseAndCustomPermissionTogatherWithPreSelection(String domainId) {
		// TODO Auto-generated method stub
		String permissionMaskArr[] = {};
		List<NameValue> permissionList = new ArrayList<NameValue>();
		List<PermissionType> permissionTypeList = permissionTypeRepo.getPermissionType();
		PermissionOnDomain permissionOnDomain;
		try {
			DomainClass domainClassLocal = domainClassRepo.getOne(Long.valueOf(domainId));
			permissionOnDomain = permissionOnDomainRepo.findByDomainClass(domainClassLocal);
			permissionMaskArr = permissionOnDomain.getPermissionMaskCollection().split(",");
		} catch (EntityNotFoundException ex) {
			// No entity found exception
			ex.printStackTrace();
		}
		String permissionLabel = "";
		String allReadyAssigned = "checked";
		int customTextId;
		int maskPower;
		boolean isInsertedInList;
		for (int i = 0; i < permissionTypeList.size(); i++) {
			isInsertedInList = false;
			permissionLabel = permissionTypeList.get(i).getName();
			maskPower = Math.getExponent(permissionTypeList.get(i).getMask());
			customTextId = permissionTypeList.get(i).getPermissionContext().getId();
			if (permissionMaskArr != null) {
				for (int j = 0; j < permissionMaskArr.length; j++) {
					if (permissionMaskArr[j].equals(permissionTypeList.get(i).getMask() + "")) {

						permissionList.add(new NameValue(
								permissionLabel + "_" + customTextId + "_" + permissionTypeList.get(i).getId() + "",
								permissionLabel, maskPower, customTextId, allReadyAssigned));
						isInsertedInList = true;
						break;
					}
				}
			} // permissionMaskArr check for no data
			if (!isInsertedInList)
				permissionList.add(new NameValue(
						permissionLabel + "_" + customTextId + "_" + permissionTypeList.get(i).getId() + "",
						permissionLabel, maskPower, customTextId, ""));

		}

		return permissionList;
	}

	public List<NameValue> loadBaseAndCustomPermissionTogather() {
		// TODO Auto-generated method stub
				List<PermissionType> permissionTypeList = permissionTypeRepo.getPermissionType();
				List<NameValue> permissionList = new ArrayList<NameValue>();				
					for (int i = 0; i < permissionTypeList.size(); i++) {						
						int maskPower = Math.getExponent(permissionTypeList.get(i).getMask());
						int customTextId = permissionTypeList.get(i).getPermissionContext().getId();
						String recordId=permissionTypeList.get(i).getId()+"";						
						String name = permissionTypeList.get(i).getName();
						permissionList.add(new NameValue(name+"_"+customTextId+"_"+recordId, name, maskPower, customTextId, ""));
					}
					return permissionList;
	}

	public boolean deletePermissionDefinition(String recordId) {
		// TODO Auto-generated method stub
		permissionTypeRepo.deleteById(Integer.valueOf(recordId));
		return true;
	}

}
