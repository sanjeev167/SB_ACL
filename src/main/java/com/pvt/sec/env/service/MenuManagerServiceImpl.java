/**
 * 
 */
package com.pvt.sec.env.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.config.db.AppPersistentConstant;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.env.dto.MenuDTO;
import com.pvt.sec.env.dto.MenuManagerDTO;
import com.pvt.sec.env.entity.MenuManager;
import com.pvt.sec.env.entity.PageMaster;
import com.pvt.sec.env.entity.TreeMenuTypeMaster;
import com.pvt.sec.env.repo.DepartmentRepository;
import com.pvt.sec.env.repo.MenuManagerRepository;
import com.pvt.sec.env.repo.PageRepository;
import com.pvt.sec.env.repo.TreeMenuTypeMasterRepository;
import com.pvt.sec.rbac.entity.AppRole;
import com.pvt.sec.rbac.entity.WebAccessRights;
import com.pvt.sec.rbac.repo.AppRoleRepository;
import com.share.AppUtil;
import com.share.NameValueM;
import com.share.grid_pagination.DataTableRequest;
import com.share.grid_pagination.DataTableResults;
import com.share.grid_pagination.PaginationCriteria;

/**
 * @author Sanjeev
 *
 */
@Transactional("appTransactionManager")
@Service
public class MenuManagerServiceImpl implements MenuManagerService {
	static final Logger log = LoggerFactory.getLogger(MenuManagerServiceImpl.class);
	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppPersistentConstant.APP_JPA_UNIT)
	private EntityManager entityManager;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	PageRepository pageRepository;
	@Autowired
	MenuManagerRepository menuManagerRepository;
	@Autowired
	TreeMenuTypeMasterRepository treeMenuTypeMasterRepository;

	@Autowired
	AppRoleRepository appRoleRepository;

	@Override
	public DataTableResults<MenuManagerDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {
		log.info("     MenuManagerServiceImpl :==> loadGrid ==> Started");
		DataTableResults<MenuManagerDTO> dataTableResult;
		try {
			DataTableRequest<MenuManager> dataTableInRQ = new DataTableRequest<MenuManager>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;

			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "SELECT parent.id as ID, " + "dm.name as departmentMasterName,"
						+ "tmtm.name as treeMenuTypeName, " + "parent.node_type as nodeType, "
						+ " child.node_name  AS menuManagerParentNodeName," + "parent.node_name AS nodeName,"
						+ "parent.img_url as imgUrl, " + "pm.name as pageBaseUrl, " + "parent.active_c as activeC,"
						+ "(SELECT COUNT(1)-1 FROM menu_manager ) AS totalrecords " +

						" FROM menu_manager parent, menu_manager child ,department_master dm , "
						+ "tree_menu_type_master tmtm ,page_master pm " + " WHERE parent.parent_node_id = child.id "
						+ "and parent.department_id=dm.id " + "and  parent.tree_menu_type_id=tmtm.id "
						+ "and  parent.page_id=pm.id ORDER BY parent.id";

			else
				baseQuery = "SELECT child.id as ID, " + "dm.name as departmentMasterName,"
						+ "tmtm.name as treeMenuTypeName, " + "parent.node_type as nodeType, "
						+ " child.node_name  AS menuManagerParentNodeName," + "parent.node_name AS nodeName,"
						+ "parent.img_url as imgUrl, " + "pm.name as pageBaseUrl, " + "parent.active_c as activeC,"
						+ "(SELECT COUNT(1)-1 FROM menu_manager parent where " + whereClauseForBaseQuery
						+ " ) AS totalrecords " +

						" FROM menu_manager parent, menu_manager child ,department_master dm , "
						+ "tree_menu_type_master tmtm ,page_master pm " + " WHERE parent.parent_node_id = child.id and "
						+ whereClauseForBaseQuery + "and  parent.page_id=pm.id " + "ORDER BY parent.id";

			// log.info("baseQuery ="+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "MenuManagerDTOMapping");

			@SuppressWarnings("unchecked")
			List<MenuManagerDTO> menuManagerList = query.getResultList();

			dataTableResult = new DataTableResults<MenuManagerDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(menuManagerList);
			if (!AppUtil.isObjectEmpty(menuManagerList)) {
				dataTableResult.setRecordsTotal(menuManagerList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(menuManagerList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(menuManagerList.size()));
				}
			}

		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     MenuManagerServiceImpl :==> loadGrid ==> Ended");
		return dataTableResult;
	}

	// This will directly put your result into your mapped dto

	@Override
	public MenuManagerDTO getReordById(Integer id) throws CustomRuntimeException {
		log.info("     MenuManagerServiceImpl :==> getReordById ==> Started");
		MenuManagerDTO menuManagerDTO;
		try {
			MenuManager menuManager = menuManagerRepository.getOne(id);
			menuManagerDTO = new MenuManagerDTO();
			menuManagerDTO.setId(menuManager.getId());
			menuManagerDTO.setDepartmentMasterId(menuManager.getDepartmentMaster().getId() + "");
			menuManagerDTO.setTreeMenuTypeId(menuManager.getTreeMenuTypeMaster().getId() + "");
			menuManagerDTO.setNodeType(menuManager.getNodeType());

			menuManagerDTO.setMenuManagerParentId(menuManager.getParentNodeManager().getId() + "");

			menuManagerDTO.setNodeName(menuManager.getNodeName());
			menuManagerDTO.setImgUrl(menuManager.getImgUrl());
			if (!menuManager.getPageMaster().getBaseurl().equals("#"))// When the page has no module
				menuManagerDTO.setModuleId(menuManager.getPageMaster().getModuleMaster().getId() + "");

			menuManagerDTO.setPageMasterId(menuManager.getPageMaster().getId() + "");

		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     MenuManagerServiceImpl :==> getReordById ==> Ended");
		return menuManagerDTO;
	}

	@Override
	public MenuManagerDTO saveAndUpdate(MenuManagerDTO menuManagerDTO) throws CustomRuntimeException {
		log.info("     MenuManagerServiceImpl :==> saveAndUpdate ==> Started");
		MenuManager menuManager;
		MenuManagerDTO menuManagerDTONew;
		try {
			if (menuManagerDTO.getId() != null) {// Edit case
				menuManager = menuManagerRepository.getOne(menuManagerDTO.getId());
			} else { // Add case
				menuManager = new MenuManager();
			}

			menuManager.setDepartmentMaster(
					departmentRepository.getOne(Integer.parseInt(menuManagerDTO.getDepartmentMasterId())));
			if (menuManagerDTO.getMenuManagerParentId() != null && !menuManagerDTO.getMenuManagerParentId().equals(""))
				menuManager.setParentNodeManager(
						menuManagerRepository.getOne(Integer.parseInt(menuManagerDTO.getMenuManagerParentId())));
			else {
				MenuManager firstMenuManager = new MenuManager();
				firstMenuManager.setNodeName("None");
				firstMenuManager.setNodeType("N");
				menuManager.setParentNodeManager(firstMenuManager);
			}
			menuManager.setNodeName(menuManagerDTO.getNodeName());
			menuManager.setNodeType(menuManagerDTO.getNodeType());
			menuManager.setTreeMenuTypeMaster(
					treeMenuTypeMasterRepository.getOne(Integer.parseInt(menuManagerDTO.getTreeMenuTypeId())));

			if (menuManagerDTO.getPageMasterId() != null && !menuManagerDTO.getPageMasterId().equals(""))
				menuManager.setPageMaster(pageRepository.getOne(Integer.parseInt(menuManagerDTO.getPageMasterId())));
			else {
				ArrayList<PageMaster> pageMasterList = pageRepository.getDummyNodePageIfExists();
				if (pageMasterList.size() > 0) {
					menuManager.setPageMaster(pageMasterList.get(0));
				} else {
					PageMaster pageMaster = new PageMaster();
					pageMaster.setBaseurl("#");
					pageMaster.setName("#");
					menuManager.setPageMaster(pageMaster);
				}

			}
			menuManager.setImgUrl(menuManagerDTO.getImgUrl());

			MenuManager returnedMenuManager = menuManagerRepository.saveAndFlush(menuManager);

			menuManagerDTONew = new MenuManagerDTO(returnedMenuManager.getId(),
					returnedMenuManager.getDepartmentMaster().getName(),
					returnedMenuManager.getTreeMenuTypeMaster().getName(), returnedMenuManager.getNodeType(),
					returnedMenuManager.getParentNodeManager().getNodeName(), returnedMenuManager.getNodeName(),
					returnedMenuManager.getImgUrl(), returnedMenuManager.getPageMaster().getBaseurl(),
					returnedMenuManager.getActiveC());

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     MenuManagerServiceImpl :==> saveAndUpdate ==> Ended");
		return menuManagerDTONew;
	}

	@Override
	public boolean deleteOneRecord(Integer id) throws CustomRuntimeException {
		log.info("     MenuManagerServiceImpl :==> deleteOneRecord ==> Started");
		boolean isRecordDelete = true;
		try {
			menuManagerRepository.deleteById(id);
		} catch (Exception ex) {
			isRecordDelete = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     MenuManagerServiceImpl :==> deleteOneRecord ==> Ended");
		return isRecordDelete;
	}

	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {
		log.info("     MenuManagerServiceImpl :==> deleteMultipleRecords ==> Started");
		boolean isRecordDelete = true;
		try {
			menuManagerRepository.deleteMenuManagerWithIds(recordIdArray);
		} catch (Exception ex) {
			isRecordDelete = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     MenuManagerServiceImpl :==> deleteMultipleRecords ==> Started");
		return isRecordDelete;
	}

	@Override
	public List<NameValueM> getListTreeMenuType() throws CustomRuntimeException {
		log.info("     MenuManagerServiceImpl :==> getListTreeMenuType ==> Started");

		List<NameValueM> moduleList = new ArrayList<NameValueM>();
		NameValueM nameValue;

		try {
			List<TreeMenuTypeMaster> treeMenuTypeMasterList = treeMenuTypeMasterRepository.findAll();
			for (TreeMenuTypeMaster treeMenuTypeMaster : treeMenuTypeMasterList) {
				nameValue = new NameValueM(treeMenuTypeMaster.getId(), treeMenuTypeMaster.getName());
				moduleList.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     MenuManagerServiceImpl :==> getListTreeMenuType ==> Ended");

		return moduleList;
	}

	@Override
	public List<NameValueM> getListTreeParentNode() throws CustomRuntimeException {
		log.info("     MenuManagerServiceImpl :==> getListTreeParentNode ==> Started");

		List<NameValueM> moduleList = new ArrayList<NameValueM>();
		// moduleList.add(new NameValue(0, "Root"));//For testing
		NameValueM nameValue;
		try {
			List<MenuManager> menuManagerList = menuManagerRepository.findAllTreeNodeList();
			for (MenuManager menuManager : menuManagerList) {
				nameValue = new NameValueM(menuManager.getId(), menuManager.getNodeName());
				moduleList.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     MenuManagerServiceImpl :==> getListTreeParentNode ==> Ended");

		return moduleList;
	}

	@Override
	public Integer getMinId() throws CustomRuntimeException {
		log.info("     MenuManagerServiceImpl :==> getMinId ==> Started");
		Integer recordId = menuManagerRepository.getMinId();
		log.info("     MenuManagerServiceImpl :==> getMinId ==> Ended");
		return recordId;
	}

	@Override
	public ArrayList<MenuDTO> getSpecificTreeTypeStructure(Integer specificTreeTypeId) throws CustomRuntimeException {
		log.info("     MenuManagerServiceImpl :==> getSpecificTreeTypeStructure ==> Started");
		List<MenuManager> menuManagerList = menuManagerRepository.findAllSortedTreeNodeList();
		// log.info("Sanjeev menuManagerList = "+menuManagerList.size());
		ArrayList<MenuDTO> treeMenuList = new ArrayList<MenuDTO>();
		MenuDTO menuDTO = null;
		for (MenuManager menuManager : menuManagerList) {
			if (menuManager.getParentNodeManager() != null) {
				// log.info("menuManager.getId() = "+menuManager.getId());
				// log.info("menuManager.getNodeName() = "+menuManager.getNodeName());
				// log.info("menuManager.getParentNodeManager().getId() =
				// "+menuManager.getParentNodeManager().getId());
				// log.info("menuManager.getPageMaster().getBaseurl() =
				// "+menuManager.getPageMaster().getBaseurl());

				menuDTO = new MenuDTO(menuManager.getId() + "", menuManager.getNodeName(),
						menuManager.getParentNodeManager().getId() + "", menuManager.getPageMaster().getBaseurl(),
						menuManager.getNodeType(), "fa " + menuManager.getImgUrl());
				treeMenuList.add(menuDTO);
			}
		}
		log.info("     MenuManagerServiceImpl :==> getSpecificTreeTypeStructure ==> Ended");
		return treeMenuList;
	}

	/**
	 * Step-1 : First pick all the roles available in the system Step-2 : Then pick
	 * all the urls of pages from page level access rights for all the roles Step-3
	 * : Then load all the menu details list available in the system Step-4 : Then
	 * iterate through this menu details list and match the page level access rights
	 * within it. Step-5 : When matched, stuff into menuDTO object Step-6 : Then
	 * collect all menuDTO into menuCollection Step-7 : Then bind each menu
	 * collection within roleWiseMenuCollection
	 *
	 * Caution: A user may have multiple roles in the system. So,
	 * roleWiseMenuCollection will be further filtered for each user so that it
	 * could get all the access rights in userWiseMenuCollection which will be
	 * prepared after successful login and stuffed into security based user details.
	 */

	// Logic for userWiseMenuCollection

	// If a user has one role, then its menuCollection can be taken out directly
	// using its role from
	// roleWiseMenuCollection.
	// For a user has more than one role, we will have to iterate through
	// roleWiseMenuCollection
	// Take one menu-type through a loop
	// Take user's defined role collection
	// For each user's role through this role collection, start collecting unique
	// entry for each menu-type in a
	// separate collection
	// Finally stuff all these collection in one collection

	@Override
	public Map<String, Map<String, List<MenuDTO>>> getRoleWiseMenuCollectionMap() throws CustomRuntimeException {
		log.info("     MenuManagerServiceImpl :==> getRoleWiseMenuCollectionMap ==> Started");

		Map<String, List<MenuDTO>> treeTypeMenuMap = new HashMap<String, List<MenuDTO>>();
		Map<String, Map<String, List<MenuDTO>>> roleWiseTreeMenuMap = new HashMap<String, Map<String, List<MenuDTO>>>();
		List<AppRole> appRoleList = appRoleRepository.findAll();
		List<WebAccessRights> webAccessRightsList;
		PageMaster pageMaster;
		String roleAccessPageBaseUrl;
		MenuManager parentMenuManager;
		boolean isThisMenuDTOHasallreadyBeenAdded = true;
		for (AppRole appRole : appRoleList) {
			treeTypeMenuMap = new HashMap<String, List<MenuDTO>>();
			webAccessRightsList = appRole.getWebAccessRights();
			// log.info("\t For Role " + appRole.getRoleName());
			for (WebAccessRights webAccessRights : webAccessRightsList) {
				pageMaster = webAccessRights.getPageMaster();
				// log.info("\t\tPage " + pageMaster.getBaseurl());
				// List<OperationAccess>
				// operationAccessesList=webAccessRights.getOperationAccesses();
				boolean pageOpenUrlFound = true;
				// Remark :when method level security is used ,pageOpenUrlFound=false should be
				// used
				// for(OperationAccess operationAccess :operationAccessesList) {
				// if(operationAccess.getOperationMaster().getOpurl().trim().equals("/"))//All
				// the page open url has only base url not anything additional
				// pageOpenUrlFound=true;
				// }

				roleAccessPageBaseUrl = pageMaster.getBaseurl();
				// .replaceAll("\\*(?!( |$))", "");
				List<MenuManager> menuManagerList = menuManagerRepository.findAll();
				if (pageOpenUrlFound)
					for (MenuManager menuManager : menuManagerList) {
						if (menuManager.getPageMaster() != null
								&& roleAccessPageBaseUrl.equals(menuManager.getPageMaster().getBaseurl())
								&& menuManager.getNodeType().equals("L")) {

							isThisMenuDTOHasallreadyBeenAdded = checkThisNodeHasAllReadyBeenAddedOrNot(menuManager,
									treeTypeMenuMap);							 
							if (!isThisMenuDTOHasallreadyBeenAdded)
								collectNodeOrLeafTreeTypeWise(menuManager, treeTypeMenuMap);
							// Start collecting its all parent node
							parentMenuManager = menuManager.getParentNodeManager();
							do {
								isThisMenuDTOHasallreadyBeenAdded = checkThisNodeHasAllReadyBeenAddedOrNot(
										parentMenuManager, treeTypeMenuMap);
								if (!isThisMenuDTOHasallreadyBeenAdded) {
									/*
									 * log.info("\t\t\t\tNew Node[" + parentMenuManager.getNodeName() +
									 * "] found. Going to collect.");
									 */
									collectNodeOrLeafTreeTypeWise(parentMenuManager, treeTypeMenuMap);
									// After adding this new node,get its upper parent and let it go in the loop
									parentMenuManager = parentMenuManager.getParentNodeManager();
								} else {
									/*
									 * log.info("\t\t\t\tOld Node[" + parentMenuManager.getNodeName() +
									 * "] found. Going to discard.");
									 */
									parentMenuManager = parentMenuManager.getParentNodeManager();
								}
							} while (parentMenuManager != null && !parentMenuManager.getNodeName().equals("None"));
						}
					} // End of menu manager List looping
						// Bind treeTypeMenuMap with a role

				roleWiseTreeMenuMap.put(appRole.getRoleName(), treeTypeMenuMap);

			} // End of webAccessRightsList looping

		}
		// printTreeStructureOfAllRoles(roleWiseTreeMenuMap);
		log.info("     MenuManagerServiceImpl :==> getRoleWiseMenuCollectionMap ==> Ended");
		return roleWiseTreeMenuMap;
	}

	private void collectNodeOrLeafTreeTypeWise(MenuManager menuManager, Map<String, List<MenuDTO>> treeTypeMenuMap) {

		List<MenuDTO> menuCollection = null;
		// Prepare node or leaf details
		MenuDTO menuDTO = new MenuDTO();
		if (menuManager != null && menuManager.getParentNodeManager() != null
				&& menuManager.getParentNodeManager().getNodeName() != null)
			menuDTO = new MenuDTO(menuManager.getId() + "", menuManager.getNodeName(),
					menuManager.getParentNodeManager().getId() + "", menuManager.getPageMaster().getBaseurl(),
					menuManager.getNodeType(), menuManager.getImgUrl());
		if (menuManager != null && menuManager.getTreeMenuTypeMaster() != null && treeTypeMenuMap != null
				&& menuManager.getTreeMenuTypeMaster() != null
				&& treeTypeMenuMap.get(menuManager.getTreeMenuTypeMaster().getName()) != null) {
			// This will update tree map entry
			menuCollection = treeTypeMenuMap.get(menuManager.getTreeMenuTypeMaster().getName());
			menuCollection.add(menuDTO);
		} else {
			// This will insert a new entry in the tree map
			menuCollection = new ArrayList<MenuDTO>();
			menuCollection.add(menuDTO);
			if (treeTypeMenuMap != null && menuManager != null && menuManager.getTreeMenuTypeMaster() != null)
				treeTypeMenuMap.put(menuManager.getTreeMenuTypeMaster().getName(), menuCollection);
		}
	}// End of collectNodeOrLeafTreeTypeWise

	private boolean checkThisNodeHasAllReadyBeenAddedOrNot(MenuManager menuManager,
			Map<String, List<MenuDTO>> treeTypeMenuMap) {
		// log.info(" MenuManagerServiceImpl :==> checkThisNodeHasAllReadyBeenAddedOrNot
		// ==> Started");
		boolean isMenuDTOAllReadyCollected = false;
		List<MenuDTO> menuCollection = null;
		for (Entry<String, List<MenuDTO>> treeTypeMenuEntry : treeTypeMenuMap.entrySet()) {
			// log.info("Tree Menu type = " + treeTypeMenuEntry.getKey());
			menuCollection = treeTypeMenuEntry.getValue();
			for (MenuDTO oldMenuDTOAllReadyCollected : menuCollection) {
				/*
				 * log.info("\t\t\tOld Node["+oldMenuDTOAllReadyCollected.getName()+"] id["
				 * +oldMenuDTOAllReadyCollected.getId()
				 * +"] New Node ["+menuManager.getNodeName()+"] id ["+menuManager.getId()
				 * +"]  Is Matched = "
				 * +oldMenuDTOAllReadyCollected.getId().equals(menuManager.getId()+"") );
				 */
				if (oldMenuDTOAllReadyCollected.getId().equals(menuManager.getId() + "")) {
					/*
					 * log.info("Old node name = "+oldMenuDTOAllReadyCollected.getName()
					 * +"\nNew node name ="+menuManager.getNodeName()+" Discard it.");
					 */
					isMenuDTOAllReadyCollected = true;

				}
				if (isMenuDTOAllReadyCollected)
					break;
			}
		} // End of treeTypeMenuMap looping
			// log.info(" MenuManagerServiceImpl :==> checkThisNodeHasAllReadyBeenAddedOrNot
			// ==> Ended");
		return isMenuDTOAllReadyCollected;
	}// End of checkThisNodeHasAllReadyBeenAddedOrNot

	private void printTreeStructureOfAllRoles(Map<String, Map<String, List<MenuDTO>>> roleWiseTreeMenuMap) {

		// Print all role wise tree map
		log.info("\tRole No. in RoleMap = " + roleWiseTreeMenuMap.size());
		for (Entry<String, Map<String, List<MenuDTO>>> roleWiseTreeTypeMenuEntry : roleWiseTreeMenuMap.entrySet()) {

			System.out.print("\tMenu Access rights for [ROLE => " + roleWiseTreeTypeMenuEntry.getKey() + "]");
			Map<String, List<MenuDTO>> treeTypeMenuMapLocal = roleWiseTreeTypeMenuEntry.getValue();

			log.info("\tMenu Access rights for [" + treeTypeMenuMapLocal.size() + "] tree type.");
			for (Entry<String, List<MenuDTO>> treeTypeMenuEntry : treeTypeMenuMapLocal.entrySet()) {
				{
					log.info(" On [" + treeTypeMenuEntry.getKey() + "]");
					List<MenuDTO> menuCollection = treeTypeMenuEntry.getValue();
					log.info("\t\tNode or Leaf tree-depth [" + menuCollection.size() + "] level");
					int i = 1;
					for (MenuDTO menuDTO : menuCollection) {
						log.info("\t\t [" + (i++) + "]");
						log.info("\t\t\tNode Type = " + menuDTO.getNodeType());
						log.info("\t\t\tParent Node ID = " + menuDTO.getParent_id());
						log.info("\t\t\tNode Id = " + menuDTO.getId());
						log.info("\t\t\tNode Name = " + menuDTO.getName());
						log.info("\t\t\tUrl = " + menuDTO.getLeafUrl());
						log.info("\t\t\tImage Url = " + menuDTO.getNodeImgUrl());
					} // Tree node or leaf level looping
				} // Tree Menu Type level looping
			} // Role level looping
		} // End of appRoleList looping
	}// End of printTreeStructureOfAllRoles

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentValue, String parentId, Object fieldValue,
			String fieldName) throws UnsupportedOperationException, CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean FirstChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean SecondChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ThirdChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

}
