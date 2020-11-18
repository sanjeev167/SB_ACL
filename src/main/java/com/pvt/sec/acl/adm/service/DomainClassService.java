/**
 * 
 */
package com.pvt.sec.acl.adm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.acl.adm.dto.DomainNameDTO;

import com.pvt.sec.acl.adm.entity.DomainClass;


import com.pvt.sec.acl.adm.repo.DomainClassRepo;

import com.share.NameValue;

/**
 * @author Sanjeev
 *
 */
@Service
@Transactional("appTransactionManager")
public class DomainClassService {
	
	@Autowired
	DomainClassRepo domainClassRepo;
	public List<NameValue> getDomainList() {
		// TODO Auto-generated method stub
		List<DomainClass> domainClassList=domainClassRepo.findAll();		
		ArrayList<NameValue> domainList = new ArrayList<NameValue>();
		domainList.add(new NameValue("0", "--Select Domain--"));
		for(int i=0;i<domainClassList.size();i++) {			
			domainList.add(new NameValue(domainClassList.get(i).getId()+"", 
					                     domainClassList.get(i).getClassName()));
		}
		return domainList;
	}

	public boolean saveDomainDefinition(DomainNameDTO domainNameDTO) {
		// TODO Auto-generated method stub
        boolean isAlreadyExist=true;
		DomainClass domainClass = domainClassRepo.findDomainClassWithDomainName(domainNameDTO.getDomainName());
		if (domainClass == null) {
			domainClass = new DomainClass();
			domainClass.setClassName(domainNameDTO.getDomainName());
			domainClassRepo.saveAndFlush(domainClass);
			isAlreadyExist=false;
		}
		return isAlreadyExist;
	}

	public boolean updateExistingDomainDefinition(DomainNameDTO domainNameDTO) {
		// TODO Auto-generated method stub
        boolean isUpdated=true;       
        DomainClass domainClass = domainClassRepo.findDomainClassWithDomainName(domainNameDTO.getDomainClassNameForEdit());
		
		domainClass.setClassName(domainNameDTO.getDomainName());
		return isUpdated;
	}

	public void deleteSelectedDomain(String domainName) {
		// TODO Auto-generated method stub
		domainClassRepo.deleteByClassName(domainName);
		
	}

}
