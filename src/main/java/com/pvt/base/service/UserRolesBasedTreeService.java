/**
 * 
 */
package com.pvt.base.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.config.exception.common.CustomRuntimeException;
import com.pvt.sec.env.dto.MenuDTO;
import com.pvt.sec.env.entity.TreeMenuTypeMaster;
import com.pvt.sec.env.repo.TreeMenuTypeMasterRepository;
import com.pvt.sec.env.service.MenuManagerService;



/**
 * @author Sanjeev
 *
 */
@Service
public class UserRolesBasedTreeService {
	
	private static final Logger log=LoggerFactory.getLogger(UserRolesBasedTreeService.class);
	@Autowired
	ServletContext servletContext;

	@Autowired
	MenuManagerService menuManagerService;

	@Autowired
	TreeMenuTypeMasterRepository treeMenuTypeMasterRepository;

	ArrayList<String> finalHierarchicalRoleCollection = null;

	
	@Transactional("appTransactionManager")
	public ArrayList<MenuDTO> getMenuListForLoggedInUser(Collection<GrantedAuthority> authorities)
			throws CustomRuntimeException {
				
		ArrayList<MenuDTO> vMenuCollection = new ArrayList<MenuDTO>();
		finalHierarchicalRoleCollection = new ArrayList<String>();
		// TODO Auto-generated method stub
		Map<String, Map<String, List<MenuDTO>>> roleWiseMenuCollectionMap = (Map<String, Map<String, List<MenuDTO>>>) servletContext
				.getAttribute("RoleWiseMenuCollectionMap");

		
		
		Map<String, List<MenuDTO>> treeTypeWiseMenuCollectionMap = null;
		String role = "";
		for (GrantedAuthority grantedAuthority : authorities) {
			// Take out role one by one
			role = grantedAuthority.getAuthority();
		   // log.info("Coming role = "+role);
			// Take out all tree type menu collection for this role
			if (!checkWhetherThisRoleIsAlreadyBeenAddedOrNot(role,finalHierarchicalRoleCollection))
				finalHierarchicalRoleCollection.add(role);
			/////////
			findAllRolesDefinedForARoleInHierarchy(role,finalHierarchicalRoleCollection);
			 //log.info("findAllRolesDefinedForARoleInHierarchy = "+findAllRolesDefinedForARoleInHierarchy(role,finalHierarchicalRoleCollection));

			for (String roleInHierarchy : finalHierarchicalRoleCollection) {
				String newRole[] = roleInHierarchy.split("ROLE_");
				
				
				treeTypeWiseMenuCollectionMap = roleWiseMenuCollectionMap.get(newRole[1]);// Fetch all tree type wise
																							// menu
				//log.info("treeTypeWiseMenuCollectionMap = "+treeTypeWiseMenuCollectionMap.size()); // collection
				// Now take out each menu type menuDTOList
				String treeTypeName;
				
				
				if (treeTypeWiseMenuCollectionMap != null)
					for (Entry<String, List<MenuDTO>> treeTypeMenuEntryForARole : treeTypeWiseMenuCollectionMap
							.entrySet()) {
						treeTypeName = treeTypeMenuEntryForARole.getKey();
						
						
						// Take out tree type menu id using tree type name
						List<TreeMenuTypeMaster> treeMenuTypeMasterList = treeMenuTypeMasterRepository.findAll();
						int id = 0;
						for (TreeMenuTypeMaster treeMenuTypeMaster : treeMenuTypeMasterList) {
			 				if (treeMenuTypeMaster.getName().equals(treeTypeName)) {
								id = treeMenuTypeMaster.getId();
							}
						}
						ArrayList<MenuDTO> menuDTOListDefinedForARole = (ArrayList<MenuDTO>) treeTypeMenuEntryForARole
								.getValue();
						// Take out complete vertical tree menu structure available in the system
						ArrayList<MenuDTO> menuDTOListAvailableInSystem = menuManagerService.getSpecificTreeTypeStructure(id);
						// Now start collecting those tree structure which is allowed for this role.
						// For doing so, we simply pick that menDTO from the existing tree list which is
						// allowed for this role
						// Collect it into a list.
						//log.info("Sanjeev menuDTOListAvailableInSystem  = "+menuDTOListAvailableInSystem.size());
						for (MenuDTO menuDTODefinedForRole : menuDTOListDefinedForARole) {
							for (MenuDTO menuDTOAvailable : menuDTOListAvailableInSystem) {

								if (menuDTOAvailable.getId().equals(menuDTODefinedForRole.getId())) {
									// if matched, collect it in an array list
									 //log.info("menuDTOAvailable.getId() ="+menuDTOAvailable.getId());
									 //log.info("menuDTODefinedForRole.getId()="+menuDTODefinedForRole.getId());
									if (!checkIfThisMenuDTOAvailableAlreadyAddedd(menuDTOAvailable,vMenuCollection))
										{
										//This will replace ** (wild-card from the end.
										menuDTOAvailable.setLeafUrl(menuDTOAvailable.getLeafUrl().replace("*", ""));
										vMenuCollection.add(menuDTOAvailable);}
								}
							}
						}
					} //
			} //
		}
         
		
		//log.info("vMenuCollection size  = "+vMenuCollection.size());
		//printTreeNodeForARole(vMenuCollection);
		
		return vMenuCollection;

	}// End of prepareMenuListForLoggedInUser

	private void printTreeNodeForARole(ArrayList<MenuDTO> vMenuCollection2) {
		int i = 1;
		for (MenuDTO menuDTO : vMenuCollection2) {
			System.out.println("\t\t [" + (i++) + "]");
			System.out.println("\t\t\tNode Type = " + menuDTO.getNodeType());
			System.out.println("\t\t\tParent Node ID = " + menuDTO.getParent_id());
			System.out.println("\t\t\tNode Id = " + menuDTO.getId());
			System.out.println("\t\t\tNode Name = " + menuDTO.getName());
			System.out.println("\t\t\tUrl = " + menuDTO.getLeafUrl());
			System.out.println("\t\t\tImage Url = " + menuDTO.getNodeImgUrl());
		} // Tree node or leaf level looping

	}

	private boolean checkIfThisMenuDTOAvailableAlreadyAddedd(MenuDTO menuDTOAvailable, ArrayList<MenuDTO> vMenuCollection) {
		boolean isAdded = false;
		// isAdded=vMenuCollection.contains(menuDTOAvailable);
		for (MenuDTO menuDTO : vMenuCollection) {
			if (menuDTO.getId().equals(menuDTOAvailable.getId()))
				isAdded = true;
		}
		return isAdded;
	}

	private ArrayList<String> findAllRolesDefinedForARoleInHierarchy(String role,ArrayList<String> finalHierarchicalRoleCollection) {
		Map<String, List<String>> definedRoleHierarchyMap = (Map<String, List<String>>) servletContext
				.getAttribute("RolesInHierarchy");
       // First find all roles dependent on a role
		// Collect role and dependent roles in a collection
		// Then find all dependent roles of each dependent role
		// Keep looping as long as all dependent roles get exhausted
		// Now we have a collection of all roles dependencies in hierarchy
		Set<Entry<String, List<String>>> definedRoleHierarchyMapEntrySet = definedRoleHierarchyMap.entrySet();

		for (Entry<String, List<String>> dependentRoleHierarchyEntry : definedRoleHierarchyMapEntrySet) {
			String availableRoleInHierarcicalCollection = dependentRoleHierarchyEntry.getKey();
			//log.info("availableRoleInHierarcicalCollection  = "+availableRoleInHierarcicalCollection);
			//log.info("role  = "+role);
			//log.info("Compared Result = "+availableRoleInHierarcicalCollection.equals(role));
			if (availableRoleInHierarcicalCollection.equals(role)) {
				if (!checkWhetherThisRoleIsAlreadyBeenAddedOrNot(role,finalHierarchicalRoleCollection))
					finalHierarchicalRoleCollection.add(role);				
				//log.info("finalHierarchicalRoleCollection b4 adding dependent role   = "+finalHierarchicalRoleCollection);
				// Now take its all dependent roles
				List<String> dependentRoleInHierarchyList = dependentRoleHierarchyEntry.getValue();
				for (String dependentRole : dependentRoleInHierarchyList) {
					//log.info("Dependent Role = "+dependentRole);
					if (!checkWhetherThisRoleIsAlreadyBeenAddedOrNot(dependentRole,finalHierarchicalRoleCollection))
						finalHierarchicalRoleCollection.add(dependentRole);
					findAllRolesDefinedForARoleInHierarchy(dependentRole,finalHierarchicalRoleCollection);
				}
			}
			
			
		}
		
		
		return finalHierarchicalRoleCollection;
	}// End of findAllRolesDefinedForARoleInHierarchy

	private boolean checkWhetherThisRoleIsAlreadyBeenAddedOrNot(String role,ArrayList<String> finalHierarchicalRoleCollection) {
		boolean allReadyAdded = false;
		allReadyAdded = finalHierarchicalRoleCollection.contains(role);
		return allReadyAdded;
	}// End of checkWhetherThisRoleIsAlreadyBeenAddedOrNot

}
