/**
 * 
 */
package com.pvt.sec.acl.adm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.acl.adm.entity.PermissionContext;
import com.pvt.sec.acl.adm.repo.PermissionContextRepo;
import com.share.NameValue;

/**
 * @author Sanjeev
 *
 */
@Service
@Transactional("appTransactionManager")
public class PermissionContextService {

	@Autowired
	PermissionContextRepo permissionContextRepo;

	public List<NameValue> loadPermissionContextList() {
		// TODO Auto-generated method stub
		List<PermissionContext> permissionContexts = permissionContextRepo.findByIsBaseContext(false);
		ArrayList<NameValue> permissionContextList = new ArrayList<NameValue>();
		permissionContextList.add(new NameValue("0", "--Select Context--"));

		for (int i = 0; i < permissionContexts.size(); i++) {
			permissionContextList
					.add(new NameValue(permissionContexts.get(i).getId() + "", permissionContexts.get(i).getName()));
		}
		return permissionContextList;
	}

	public List<NameValue> loadPermissionCustomContextList() {
		// TODO Auto-generated method stub
		
		List<PermissionContext> permissionContexts = permissionContextRepo.findAll();
		ArrayList<NameValue> permissionContextList = new ArrayList<NameValue>();
		permissionContextList.add(new NameValue("0", "--Select Custom Context--"));
		String allReadyAssigned = "selected";
		for (int i = 0; i < permissionContexts.size(); i++) {
			if (!permissionContexts.get(i).getIsBaseContext()) {
				if (i == 1)
					permissionContextList.add(new NameValue(permissionContexts.get(i).getId() + "",
							permissionContexts.get(i).getName(), allReadyAssigned));
				else
					permissionContextList.add(
							new NameValue(permissionContexts.get(i).getId() + "", permissionContexts.get(i).getName()));
			}
		}
		return permissionContextList;
	}

}
