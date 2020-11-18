/**
 * 
 */
package com.pvt.sec.rbac.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.config.db.AppPersistentConstant;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.env.entity.PageMaster;
import com.pvt.sec.env.repo.DepartmentRepository;
import com.pvt.sec.env.repo.ModuleRepository;
import com.pvt.sec.env.repo.PageRepository;
import com.pvt.sec.rbac.dto.WebAccessRightsDTO;
import com.pvt.sec.rbac.entity.AppRole;
import com.pvt.sec.rbac.entity.RoleHierarchy;
import com.pvt.sec.rbac.entity.WebAccessRights;
import com.pvt.sec.rbac.repo.AppRoleRepository;
import com.pvt.sec.rbac.repo.RoleHierarchyRepository;
import com.pvt.sec.rbac.repo.WebAccessRightsRepository;
import com.share.AppUtil;
import com.share.NameValueM;
import com.share.grid_pagination.DataTableRequest;
import com.share.grid_pagination.DataTableResults;
import com.share.grid_pagination.PaginationCriteria;


/**
 * @author Sanjeev
 *
 */
@Service
@Transactional("appTransactionManager")
public class WebAccessRightsServiceImpl implements WebAccessRightsService {
	
	static final Logger log = LoggerFactory.getLogger(WebAccessRightsServiceImpl.class);

	/** The entity manager. */
	@Autowired
    @PersistenceContext( unitName= AppPersistentConstant.APP_JPA_UNIT)
	private EntityManager entityManager;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	PageRepository pageRepository;
	
	@Autowired
	AppRoleRepository appRoleRepository;
	
	@Autowired
	RoleHierarchyRepository roleHierarchyRepository;
	
	@Autowired
	WebAccessRightsRepository webAccessRightsRepository;
	
	
	
	@Override
	public DataTableResults<WebAccessRightsDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery) throws CustomRuntimeException {
		log.info("     RbacAccessRightsServiceImpl :==> loadGrid ==> Started");
		
		DataTableResults<WebAccessRightsDTO> dataTableResult=null;
		try {
		DataTableRequest<WebAccessRights> dataTableInRQ = new DataTableRequest<WebAccessRights>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		String baseQuery;

		if (whereClauseForBaseQuery.equals(""))
			baseQuery = "select om.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
					+ " pm.name as pageName," + " pm.baseurl as baseurl," + " om.name as opName,"
					+ " om.sortname as opSortName," + " om.opurl as opUrl, "
					+ " (SELECT COUNT(1) FROM page_master pm, module_master mm , department_master dm, operation_master om "
					+ " where om.page_id=pm.id  and pm.module_id=mm.id and mm.department_id=dm.id  "
					+ " ) AS totalrecords"
					+ " from page_master pm, module_master mm , department_master dm, operation_master om "
					+ " where om.page_id=pm.id  and pm.module_id=mm.id and mm.department_id=dm.id ";

		else
			baseQuery = "select om.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
					+ " pm.name as pageName," + " pm.baseurl as baseurl," + " om.name as opName,"
					+ " om.sortname as opSortName," + " om.opurl as opUrl, "
					+ " (SELECT COUNT(1) FROM page_master pm, module_master mm , department_master dm, operation_master om "
					+ " where om.page_id=pm.id  and pm.module_id=mm.id and mm.department_id=dm.id and  "
					+ whereClauseForBaseQuery + " ) AS totalrecords"
					+ " from page_master pm, module_master mm , department_master dm, operation_master om "
					+ " where om.page_id=pm.id  and pm.module_id=mm.id and mm.department_id=dm.id  and "
					+ whereClauseForBaseQuery;

		// log.info("baseQuery ="+baseQuery);
		String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
		Query query = entityManager.createNativeQuery(paginatedQuery, "AccessRightsRbacDTOMapping");

		@SuppressWarnings("unchecked")
		List<WebAccessRightsDTO> webAccessRightsList = query.getResultList();

		dataTableResult = new DataTableResults<WebAccessRightsDTO>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(webAccessRightsList);
		if (!AppUtil.isObjectEmpty(webAccessRightsList)) {
			dataTableResult.setRecordsTotal(webAccessRightsList.get(0).getTotalrecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(webAccessRightsList.get(0).getTotalrecords().toString());
			} else {
				dataTableResult.setRecordsFiltered(Integer.toString(webAccessRightsList.size()));
			}
		}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     RbacAccessRightsServiceImpl :==> loadGrid ==> Ended");
		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	
	@Override
	public WebAccessRightsDTO loadContextBasedRolesWithAlreadyAssignedPages(Integer pageId) throws CustomRuntimeException {
		log.info("     RbacAccessRightsServiceImpl :==> loadContextBasedRolesWithAlreadyAssignedPages ==> Started");
		
		WebAccessRightsDTO webAccessRightsDTO = null;		
		try {
		PageMaster pageMaster = pageRepository.getOne(pageId);
		webAccessRightsDTO = new WebAccessRightsDTO();
		webAccessRightsDTO.setDepartmentNameId(pageMaster.getModuleMaster().getDepartmentMaster().getId() + "");
		webAccessRightsDTO.setDepartmentName(pageMaster.getModuleMaster().getDepartmentMaster().getName());

		webAccessRightsDTO.setModuleNameId(pageMaster.getModuleMaster().getId() + "");
		webAccessRightsDTO.setModuleName(pageMaster.getModuleMaster().getName());

		webAccessRightsDTO.setPageNameId(pageMaster.getId() + "");
		webAccessRightsDTO.setPageName(pageMaster.getName());
		// Set all the roles available in the department with name and id
		webAccessRightsDTO.setRoleListInDepartment(
				getRoleListInDepartment(pageMaster.getModuleMaster().getDepartmentMaster().getAppRoles()));
		} catch (Exception ex) {ex.printStackTrace();
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     RbacAccessRightsServiceImpl :==> loadContextBasedRolesWithAlreadyAssignedPages ==> Ended");
		return webAccessRightsDTO;
	}

	
	@Override
	public List<NameValueM> getRoleListHavingRightsOnThisPage(List<WebAccessRights> webAccessRightsList) throws CustomRuntimeException {
		log.info("     RbacAccessRightsServiceImpl :==> getRoleListHavingRightsOnThisPage ==> Started");
		List<NameValueM> roleListWithNameAndId = new ArrayList<NameValueM>();
		NameValueM nameValueM=null;
		try {
		for (WebAccessRights webAccessRights : webAccessRightsList) {
			nameValueM = new NameValueM(webAccessRights.getAppRole().getId(),
					webAccessRights.getAppRole().getRoleName());
			roleListWithNameAndId.add(nameValueM);
		}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     RbacAccessRightsServiceImpl :==> getRoleListHavingRightsOnThisPage ==> Ended");
		return roleListWithNameAndId;
	}

	
	@Override
	public List<NameValueM> getRoleListInDepartment(List<AppRole> roleListInDepartment) throws CustomRuntimeException {
		log.info("     RbacAccessRightsServiceImpl :==> getRoleListInDepartment ==> Started");
		List<NameValueM> roleListWithNameAndId = new ArrayList<NameValueM>();
		try {
			
		SecurityContextHolder.getContext().getAuthentication().getAuthorities()
		.forEach(authority->{		
				String roleAsHead=authority.getAuthority().split("ROLE_")[1];
				
				//Load RoleHierarchy row by its head name
				RoleHierarchy roleHierarchy=roleHierarchyRepository.getByHead(roleAsHead);
				//For fetching super admin access rights
				//int parentId=roleHierarchy.getRoleHierarchy().getId();
				//int parentId=roleHierarchy.getId();
				
				///
				roleHierarchyRepository.childrenInRoleInHierarchy(roleHierarchy.getId())
				.forEach(roleHierarchyLocal->{
					//Check whether this role has already been collected or not
					if(!checkWhetherThisRoleIsAlreadyCollectedOrNot(roleListWithNameAndId,roleHierarchyLocal.getAppRole().getRoleName()))
					roleListWithNameAndId.add(new NameValueM(roleHierarchyLocal.getAppRole().getId(), 
						       roleHierarchyLocal.getAppRole().getRoleName(),
						       roleHierarchyLocal.getAppRole().getWebAccessRights().size()));
				});
			////
		});
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     RbacAccessRightsServiceImpl :==> getRoleListInDepartment ==> Ended");
		return roleListWithNameAndId;
	}

	
	private boolean checkWhetherThisRoleIsAlreadyCollectedOrNot(List<NameValueM> roleListWithNameAndId,
			String roleName) {
		// TODO Auto-generated method stub
		boolean isAlreadyInserted=false;
		for(int i=0;i<roleListWithNameAndId.size();i++) {
			if(roleListWithNameAndId.get(i).getName().equals(roleName)) {
				 isAlreadyInserted=true;break;
			}
		}
		
		
		
		return isAlreadyInserted;
	}

	@Override
	public boolean saveAndUpdate(String accessRightsRbacId,String pageId,String roleId) throws CustomRuntimeException {	
		log.info("     RbacAccessRightsServiceImpl :==> saveAndUpdate ==> Started");
		WebAccessRights webAccessRights=null;		
		boolean isSaved=true;
		try {
		if (accessRightsRbacId.equals("")) {			
			//New  Access rights
			webAccessRights = new WebAccessRights();
			webAccessRights.setPageMaster(pageRepository.getOne(Integer.parseInt(pageId)));
			webAccessRights.setAppRole(appRoleRepository.getOne(Integer.parseInt(roleId)));
			webAccessRightsRepository.saveAndFlush(webAccessRights);
		}			
		else {
			//Old Access rights			
			;
		}			
		} catch (Exception ex) {
			isSaved=false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     RbacAccessRightsServiceImpl :==> saveAndUpdate ==> Ended");
		return isSaved;
	}
	   
    public boolean matchExistingIdWithComingId(Integer idExisting,String recordIdArray[]) throws CustomRuntimeException {
	   log.info("     RbacAccessRightsServiceImpl :==> matchExistingIdWithComingId ==> Started");
	   
	   boolean comparedStatus=false;
	   try {
	   for(String id:recordIdArray) {
		   if(Integer.parseInt(id)==idExisting)
			   comparedStatus=true;
	   }
	   } catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
	   log.info("     RbacAccessRightsServiceImpl :==> matchExistingIdWithComingId ==> Ended");
	   return comparedStatus;
	   
   }

   
	@Override
	public boolean deleteRecordById(Integer id) throws CustomRuntimeException {
		log.info("     RbacAccessRightsServiceImpl :==> deleteRecordById ==> Started");
		boolean isDeleted=true;
		try {
		webAccessRightsRepository.deleteById(id);
		} catch (Exception ex) {
			isDeleted=false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     RbacAccessRightsServiceImpl :==> deleteRecordById ==> Ended");
		return isDeleted;
	}

	
	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {
		log.info("     RbacAccessRightsServiceImpl :==> deleteMultipleRecords ==> Started");
		boolean isDeleted=true;
		try {
		webAccessRightsRepository.deleteOperationWithIds(recordIdArray);
	} catch (Exception ex) {
		isDeleted=false;
		throw ExceptionApplicationUtility.wrapRunTimeException(ex);
	}
		log.info("     RbacAccessRightsServiceImpl :==> deleteMultipleRecords ==> Ended");
		
		return isDeleted;
	}

	
	@Override
	public WebAccessRightsDTO loadRoleWithPageAccessCountAfterAssignmentAndRemoval(Integer roleId) throws CustomRuntimeException {
		log.info("     RbacAccessRightsServiceImpl :==> loadRoleWithPageAccessCountAfterAssignmentAndRemoval ==> Started");
		
		String roleName=appRoleRepository.getOne(roleId).getRoleName();
		WebAccessRightsDTO webAccessRightsDTO = new WebAccessRightsDTO();
		webAccessRightsDTO.setRoleName(roleName);		
		List<NameValueM> allAssignedPagesToThisRole = new ArrayList<NameValueM>();			
		try {
			List<WebAccessRights> webAccessRightsList = webAccessRightsRepository.loadRoleWithPageAccessCountAfterAssignmentAndRemoval(roleId);
			
			webAccessRightsList.forEach((webAccessRights) -> {
				allAssignedPagesToThisRole.add(new NameValueM(webAccessRights.getPageMaster().getId(),webAccessRights.getPageMaster().getName(),"checked"));
			});
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		webAccessRightsDTO.setAllAssignedPagesToThisRole(allAssignedPagesToThisRole);
		log.info("     RbacAccessRightsServiceImpl :==> loadRoleWithPageAccessCountAfterAssignmentAndRemoval ==> Ended");
		return webAccessRightsDTO;
	}

	
	@Override
	public WebAccessRightsDTO savePageAssignment(Integer pageId, String[] roleIdArray)
			throws CustomRuntimeException {
		log.info("     RbacAccessRightsServiceImpl :==> savePageAssignment ==> Started");

		WebAccessRightsDTO webAccessRightsDTO = new WebAccessRightsDTO();
		List<NameValueM> roleListWithNameAndId = new ArrayList<NameValueM>();
		try {
			WebAccessRights webAccessRights;
			for (int i = 0; i < roleIdArray.length; i++) {				
				int size=webAccessRightsRepository.loadUniqueRoleAndPageCombination( Integer.valueOf(roleIdArray[i]), pageId).size();
				if(size==0) {
				webAccessRights = new WebAccessRights();	
				webAccessRights.setAppRole(appRoleRepository.getOne(Integer.parseInt(roleIdArray[i])));
				webAccessRights.setPageMaster(pageRepository.getOne(pageId));
				webAccessRightsRepository.save(webAccessRights);
				}
			}
			List<AppRole> contextSpecificRoleList = pageRepository.getOne(pageId).getModuleMaster()
					.getDepartmentMaster().getAppRoles();
			contextSpecificRoleList.forEach(appRole -> {
				// Take out already assigned page count
				int pageCount = appRole.getWebAccessRights().size();
				NameValueM nameValueM = new NameValueM(appRole.getId(), appRole.getRoleName(), pageCount);
				pageCount = 0;
				roleListWithNameAndId.add(nameValueM);
			});

			webAccessRightsDTO.setRoleListInDepartment(roleListWithNameAndId);
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     RbacAccessRightsServiceImpl :==> savePageAssignment ==> Ended");
		return webAccessRightsDTO;
	}

	
	@Override
	public WebAccessRightsDTO removePageAssignment(Integer roleId, Integer[] pageIdArray)
			throws CustomRuntimeException {
		log.info("     RbacAccessRightsServiceImpl :==> removePageAssignment ==> Started");
	
		WebAccessRightsDTO webAccessRightsDTO = new WebAccessRightsDTO();
		List<NameValueM> roleListWithNameAndId = new ArrayList<NameValueM>();
		try {
			webAccessRightsRepository.deleteRecordWithPageIdAndRoleId(roleId,pageIdArray);	
			
			List<AppRole> contextSpecificRoleList = appRoleRepository.getOne(roleId)
					.getDepartmentMaster().getAppRoles();
			contextSpecificRoleList.forEach(appRole -> {
				// Take out already assigned page count
				int pageCount = appRole.getWebAccessRights().size();
				NameValueM nameValueM = new NameValueM(appRole.getId(), appRole.getRoleName(), pageCount);
				pageCount = 0;
				roleListWithNameAndId.add(nameValueM);
			});

			 webAccessRightsDTO.setRoleListInDepartment(roleListWithNameAndId);
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     RbacAccessRightsServiceImpl :==> removePageAssignment ==> Ended");
		return webAccessRightsDTO;
	}
}
