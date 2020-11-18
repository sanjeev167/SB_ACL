/**
 * 
 */
package com.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.rbac.entity.AppRole;
import com.pvt.sec.rbac.entity.WebAccessRights;
import com.pvt.sec.rbac.repo.AppRoleRepository;
import com.share.PathUtilities;

/**
 * @author Sanjeev
 *
 */
@Service
public class UrlAndRoleMatcherServiceImpl implements UrlAndRoleMatcherService {

	static final Logger log = LoggerFactory.getLogger(UrlAndRoleMatcherServiceImpl.class);

	Map<String, ArrayList<String>> urlAndAccessRolesMap;
	ArrayList<String> roleList;

	@Autowired
	AppRoleRepository appRoleRepository;

	public int counter = 0;

	@Transactional(value = "appTransactionManager")
	public Map<String, ArrayList<String>> getWebUrlAndRoleMatcherList() {
		Map<String, String> wildCardCharacterUrlWithRoleMap = new HashMap<String, String>();
		try {
			List<AppRole> appRoles = appRoleRepository.findAll();
			List<WebAccessRights> webAccessRights = new ArrayList<WebAccessRights>();
			String pageBaseUrl = "";
			for (AppRole appRole : appRoles) {
				webAccessRights = appRole.getWebAccessRights();
				for (WebAccessRights accessRightsRbac : webAccessRights) {
					pageBaseUrl = accessRightsRbac.getPageMaster().getBaseurl();
					fillUrlAndRolesInMap(pageBaseUrl, appRole.getRoleName());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		 printUrlWithAllAccessRoles(urlAndAccessRolesMap);
		return urlAndAccessRolesMap;

	}

	private void fillUrlAndRolesInMap(String pageBaseUrl, String accessRole) {
		// TODO Auto-generated method stub
		String urlKey = pageBaseUrl;
		// This will execute only when the first record comes
		if (urlAndAccessRolesMap == null) {
			urlAndAccessRolesMap = new HashMap<String, ArrayList<String>>();
			roleList = new ArrayList<>();
			roleList.add(accessRole);
			urlAndAccessRolesMap.put(urlKey, roleList);
			// log.info("First time urlKey " + urlKey + " Role =" + roleList);

		}
		// This will execute when the url key is already present and new role is coming
		else {
			if (urlAndAccessRolesMap.containsKey(urlKey)) {
				// log.info("Duplicate urlKey " + urlKey + " Role =" + roleList);
				roleList = urlAndAccessRolesMap.get(urlKey);
				roleList.add(accessRole);
			} else {
				// This will execute when a new url key comes
				// log.info("New urlKey " + urlKey + " Role =" + roleList);
				roleList = new ArrayList<>();
				roleList.add(accessRole);
				urlAndAccessRolesMap.put(urlKey, roleList);
			}
		}
	}// End of fillUrlAndRolesInMap

	private Map<String, ArrayList<String>> clubWildCardCharacterUrlRolesWithOtherUrlIfMatched(
			Map<String, String> wildCardCharacterUrlWithRoleMap, Map<String, ArrayList<String>> urlAndAccessRolesMap) {
		if (wildCardCharacterUrlWithRoleMap != null) {
			if (urlAndAccessRolesMap != null) {
				// Take out url from wildCardCharacterUrlWithRoleMap and match it within
				// urlAndAccessRolesMap
				Iterator wildCardCharacterUrlWithRoleMapIteratorOuter = wildCardCharacterUrlWithRoleMap.entrySet()
						.iterator();
				while (wildCardCharacterUrlWithRoleMapIteratorOuter.hasNext()) {
					Map.Entry mapElementOuter = (Map.Entry) wildCardCharacterUrlWithRoleMapIteratorOuter.next();
					// log.info("Url outer => " + mapElementOuter.getKey() + " : Roles outer" +
					// mapElementOuter.getValue().toString());
					String urlPatternOuter = mapElementOuter.getKey().toString();
					String outerRole = mapElementOuter.getValue().toString();
					// Now take url from urlAndAccessRolesMap
					Iterator urlAndAccessRolesMapIteratorInner = urlAndAccessRolesMap.entrySet().iterator();
					while (urlAndAccessRolesMapIteratorInner.hasNext()) {
						Map.Entry mapElementInner = (Map.Entry) urlAndAccessRolesMapIteratorInner.next();
						// log.info("Url inner => " + mapElementInner.getKey() + " : Roles Inner " +
						// mapElementInner.getValue().toString());
						String urlInner = mapElementInner.getKey().toString();
						// log.info("urlInner = "+urlInner+" urlPatternOuter = "+urlPatternOuter);
						// log.info("Inner Outer Match = "+PathUtilities.match(urlInner,
						// urlPatternOuter));
						if (PathUtilities.match(urlInner, urlPatternOuter)) {
							ArrayList<String> innerRoleList = (ArrayList<String>) mapElementInner.getValue();
							// log.info("Inner Role before adding = "+innerRoleList.toString());
							if (!innerRoleList.contains("ROLE_" + outerRole))// avoid duplicate entry
								innerRoleList.add("ROLE_" + outerRole);

							// log.info("Inner Role After adding = "+innerRoleList.toString());
						}
					} // End of urlAndAccessRolesMapIterator loop
				} // End of wildCardCharacterUrlWithRoleMapIterator loop
			} else {
				// This is possible when no access rights have been defined on operation

			}
		}

		// printUrlWithAllAccessRoles(urlAndAccessRolesMap);
		return urlAndAccessRolesMap;

	}

	private void printUrlWithAllAccessRoles(Map<String, ArrayList<String>> urlAndAccessRolesMap) {
		// TODO Auto-generated method stub
		// Getting an iterator\
		Iterator urlAndAccessRolesIterator;
		if (urlAndAccessRolesMap != null) {
			urlAndAccessRolesIterator = urlAndAccessRolesMap.entrySet().iterator();
			// Iterate through the hashmap

			while (urlAndAccessRolesIterator.hasNext()) {
				Map.Entry mapElement = (Map.Entry) urlAndAccessRolesIterator.next();
				//System.out.println("Sanjeev Url => " + mapElement.getKey() + " : Roles " + mapElement.getValue().toString());
			}
		}
	}

}// End of UrlAndRoleMatcherServiceImpl
