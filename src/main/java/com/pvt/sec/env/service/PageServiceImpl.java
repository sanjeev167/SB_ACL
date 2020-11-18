/**
 * 
 */
package com.pvt.sec.env.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.internal.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.config.db.AppPersistentConstant;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.env.dto.PageMasterDTO;
import com.pvt.sec.env.entity.PageMaster;
import com.pvt.sec.env.repo.DepartmentRepository;
import com.pvt.sec.env.repo.ModuleRepository;
import com.pvt.sec.env.repo.PageRepository;

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
public class PageServiceImpl implements PageService {

	static final Logger log = LoggerFactory.getLogger(PageServiceImpl.class);

	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppPersistentConstant.APP_JPA_UNIT)
	private EntityManager entityManager;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	PageRepository pageRepository;

	@Override
	public DataTableResults<PageMasterDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {

		log.info("     PageServiceImpl :==> loadGrid ==> Started");

		DataTableResults<PageMasterDTO> dataTableResult = null;
		try {
			DataTableRequest<PageMaster> dataTableInRQ = new DataTableRequest<PageMaster>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;

			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "select pm.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
						+ " pm.name as pageName," + " pm.baseurl as baseurl,"
						+ " (SELECT COUNT(1) FROM page_master pm, module_master mm , department_master dm where pm.module_id=mm.id and mm.department_id=dm.id ) AS totalrecords"
						+ " from page_master pm, module_master mm , department_master dm"
						+ " where pm.module_id=mm.id and mm.department_id=dm.id ";
			else
				baseQuery = "select pm.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
						+ " pm.name as pageName," + " pm.baseurl as baseurl,"
						+ " (SELECT COUNT(1) FROM page_master pm, module_master mm , department_master dm where pm.module_id=mm.id and mm.department_id=dm.id and "+ whereClauseForBaseQuery +") AS totalrecords"
						+ " from page_master pm, module_master mm , department_master dm"
						+ " where pm.module_id=mm.id and mm.department_id=dm.id and " + whereClauseForBaseQuery;
			
				

			// log.info("baseQuery ="+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "PageMasterDTOMapping");

			@SuppressWarnings("unchecked")
			List<PageMasterDTO> pageMasterList = query.getResultList();

			dataTableResult = new DataTableResults<PageMasterDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(pageMasterList);
			if (!AppUtil.isObjectEmpty(pageMasterList)) {
				dataTableResult.setRecordsTotal(pageMasterList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(pageMasterList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(pageMasterList.size()));
				}
			}

		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     PageServiceImpl :==> loadGrid ==> Ended");

		return dataTableResult;
	}

	@Override
	public DataTableResults<PageMasterDTO> loadGridForRbacCMP(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {

		log.info("     PageServiceImpl :==> loadGridForRbacCMP ==> Started");

		DataTableResults<PageMasterDTO> dataTableResult = null;
		try {
			DataTableRequest<PageMaster> dataTableInRQ = new DataTableRequest<PageMaster>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;

			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "select pm.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
						+ " pm.name as pageName," + " pm.baseurl as baseurl,"
						+ " (SELECT COUNT(1) FROM page_master pm, module_master mm , department_master dm where pm.module_id=mm.id and mm.department_id=dm.id ) AS totalrecords"
						+ " from page_master pm, module_master mm , department_master dm"
						+ " where pm.module_id=mm.id and mm.department_id=dm.id ";
			else
				baseQuery = "select pm.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
						+ " pm.name as pageName," + " pm.baseurl as baseurl,"
						+ " (SELECT COUNT(1) FROM page_master pm, module_master mm , department_master dm where pm.module_id=mm.id and mm.department_id=dm.id and "+ whereClauseForBaseQuery +") AS totalrecords"
						+ " from page_master pm, module_master mm , department_master dm"
						+ " where pm.module_id=mm.id and mm.department_id=dm.id and " + whereClauseForBaseQuery;
			
				

			// log.info("baseQuery ="+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "PageMasterDTOMapping");

			@SuppressWarnings("unchecked")
			List<PageMasterDTO> pageMasterList = query.getResultList();

			dataTableResult = new DataTableResults<PageMasterDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(pageMasterList);
			if (!AppUtil.isObjectEmpty(pageMasterList)) {
				dataTableResult.setRecordsTotal(pageMasterList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(pageMasterList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(pageMasterList.size()));
				}
			}

		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     PageServiceImpl :==> loadGridForRbacCMP ==> Ended");

		return dataTableResult;
	}

	@Override
	public DataTableResults<PageMasterDTO> loadGridFor_RoleAssignedAndUnassigned(HttpServletRequest request, 
			                               String moduleId,String roleId,String pageViewCondition)
			throws CustomRuntimeException {

		log.info("     PageServiceImpl :==> loadGridFor_RoleAssignedAndUnassigned ==> Started");

		DataTableResults<PageMasterDTO> dataTableResult = null;
		try {
			DataTableRequest<PageMaster> dataTableInRQ = new DataTableRequest<PageMaster>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String whereRoleCondition="";String whereModuleCondition="";
			if(pageViewCondition.equals("U")&& !roleId.equals("")) {
				whereRoleCondition="  and pm.id NOT IN (select page_id as id from access_rights_rbac where role_id = "
				                  + Integer.parseInt(roleId)+")";
				            }
			if(pageViewCondition.equals("A")&& !roleId.equals("")) {
				whereRoleCondition="  and pm.id IN (select page_id as id from access_rights_rbac where role_id = "
				                  + Integer.parseInt(roleId)+")";
				            }
			if(pageViewCondition.equals("U")&& !moduleId.equals("")) {
				whereModuleCondition="  and pm.module_id="						
						+ Integer.parseInt(moduleId);
						
				            }
			if(pageViewCondition.equals("A")&& !moduleId.equals("")) {
				whereModuleCondition="  and pm.module_id="						
						+ Integer.parseInt(moduleId);
				            }
			
			String baseQuery = "select pm.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
						+ " pm.name as pageName," + " pm.baseurl as baseurl,"
						+ " (SELECT COUNT(1) FROM page_master pm, module_master mm , department_master dm "
						+ " where "
						//Join condition			              
						+ " pm.module_id=mm.id "
						+ " and mm.department_id=dm.id "
						//Filter condition 
						+whereModuleCondition						
						+ whereRoleCondition				
						
						+ " ) AS totalrecords"
						+ " from page_master pm, module_master mm , department_master dm "
						+ " where "
						//Join condition			              
						+ " pm.module_id=mm.id "
						+ " and mm.department_id=dm.id "
						//Filter condition 
						+whereModuleCondition						
						+ whereRoleCondition;
			
			//log.info("baseQuery RU ="+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "PageMasterDTOMapping");

			@SuppressWarnings("unchecked")
			List<PageMasterDTO> pageMasterList = query.getResultList();

			dataTableResult = new DataTableResults<PageMasterDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(pageMasterList);
			if (!AppUtil.isObjectEmpty(pageMasterList)) {
				dataTableResult.setRecordsTotal(pageMasterList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(pageMasterList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(pageMasterList.size()));
				}
			}

		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     PageServiceImpl :==> loadGridForRbacCMP_RU ==> Ended");

		return dataTableResult;
	}

	
	
	
	// This will directly put your result into your mapped dto
	@Override	
	public PageMasterDTO getReordById(Integer id) throws CustomRuntimeException {

		log.info("     PageServiceImpl :==> getReordById ==> Started");

		PageMasterDTO pageMasterDTO = null;
		try {
			PageMaster pageMaster = pageRepository.getOne(id);
			pageMasterDTO = new PageMasterDTO();
			pageMasterDTO.setId(pageMaster.getId());
			pageMasterDTO.setDepartmentNameId(pageMaster.getModuleMaster().getDepartmentMaster().getId() + "");
			pageMasterDTO.setModuleNameId(pageMaster.getModuleMaster().getId() + "");
			pageMasterDTO.setPageName(pageMaster.getName());
			pageMasterDTO.setBaseurl(pageMaster.getBaseurl());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     PageServiceImpl :==> getReordById ==> Ended");

		return pageMasterDTO;
	}


	@Override
	public PageMasterDTO saveAndUpdate(PageMasterDTO pageMasterDTO) throws CustomRuntimeException {

		log.info("     PageServiceImpl :==> saveAndUpdate ==> Started");

		PageMaster pageMaster = null;
		PageMasterDTO pageMasterDTONew = null;
		try {
			if (pageMasterDTO.getId() != null)
				pageMaster = pageRepository.getOne(pageMasterDTO.getId());
			else
				pageMaster = new PageMaster();

			pageMaster.setId(pageMasterDTO.getId());
			pageMaster.setName(pageMasterDTO.getPageName());
			pageMaster.setBaseurl(pageMasterDTO.getBaseurl());
			pageMaster.setModuleMaster(moduleRepository.getOne(Integer.parseInt(pageMasterDTO.getModuleNameId())));
			PageMaster returnedPageMaster = pageRepository.saveAndFlush(pageMaster);
			pageMasterDTONew = new PageMasterDTO(returnedPageMaster.getId(),
					returnedPageMaster.getModuleMaster().getDepartmentMaster().getId() + "",
					returnedPageMaster.getModuleMaster().getId() + "", returnedPageMaster.getName(),
					returnedPageMaster.getBaseurl());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     PageServiceImpl :==> saveAndUpdate ==> Ended");

		return pageMasterDTONew;
	}

	@Override

	public boolean deleteOneRecord(Integer id) throws CustomRuntimeException {

		log.info("     PageServiceImpl :==> deleteOneRecord ==> Started");

		boolean isDeleted = true;
		try {
			pageRepository.deleteById(id);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     PageServiceImpl :==> deleteOneRecord ==> Ended");

		return isDeleted;
	}


	@Override
	public void deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {

		log.info("     PageServiceImpl :==> deleteMultipleRecords ==> Started");

		boolean isDeleted = true;
		try {
			pageRepository.deletePageWithIds(recordIdArray);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     PageServiceImpl :==> deleteMultipleRecords ==> Ended");

	}

	@Override
	public List<NameValueM> getPageList(Integer id) throws CustomRuntimeException {

		log.info("     PageServiceImpl :==> getPageList ==> Started");

		List<NameValueM> moduleList = new ArrayList<NameValueM>();
		NameValueM nameValue;

		try {
			List<PageMaster> pageMasterList = moduleRepository.getOne(id).getPageMasters();
			for (PageMaster pageMaster : pageMasterList) {
				nameValue = new NameValueM(pageMaster.getId(), pageMaster.getName());
				moduleList.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     PageServiceImpl :==> getPageList ==> Ended");

		return moduleList;
	}
	
	
	@Override
	public List<NameValueM> getListBaseUrl(Integer id) throws CustomRuntimeException {
		log.info("     PageServiceImpl :==> getListBaseUrl ==> Started");
		List<NameValueM> moduleList = new ArrayList<NameValueM>();
		NameValueM nameValue;
		try {
			List<PageMaster> pageMasterList = moduleRepository.getOne(id).getPageMasters();
			for (PageMaster pageMaster : pageMasterList) {				
				nameValue = new NameValueM(pageMaster.getId(), pageMaster.getName());
				moduleList.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     PageServiceImpl :==> getListBaseUrl ==> Ended");
		return moduleList;
	}
	
	
	

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentIdValue, String parentId, 
			                                            Object fieldValue,String fieldName,
			                                            Object idValue, String idFieldName) throws CustomRuntimeException {

		log.info("     PageServiceImpl :==> FieldValueWithParentIdAndChildExists ==> Started");

		boolean recordFound = false;
		try {
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("moduleNameId")&&!fieldName.equals("pageName") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.pageRepository.existsByModuleIdAndPageName(Integer.parseInt(parentIdValue+""),
						fieldValue.toString());    				
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.pageRepository.existsByModuleIdAndPageNameExceptThisId(Integer.parseInt(parentIdValue+""),
						fieldValue.toString(),Integer.parseInt(idValue.toString()));  
				
			}
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     PageServiceImpl :==> FieldValueWithParentIdAndChildExists ==> Ended");

		return recordFound;
	}

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentValue, String parentId, Object fieldValue,
			String fieldName) throws UnsupportedOperationException, CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean SecondChildValueWithParentIdExists(Object parentValue, String parentLabel, Object fieldValue,
			String fieldName,Object idValue, String idFieldName) throws CustomRuntimeException {
		boolean recordFound = false;
		try {
			Assert.notNull(parentLabel);Assert.notNull(fieldName);

			if (!parentLabel.equals("moduleNameId")&&!fieldName.equals("baseurl") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentValue == null && fieldValue==null) {
				return false;
			}
			if(!parentValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.pageRepository.existsByPageNameAndBaseUrl(Integer.parseInt(parentValue.toString()),fieldValue.toString());    				
			}			
			if(!parentValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.pageRepository.existsByPageNameAndBaseUrlExceptThisId(Integer.parseInt(parentValue.toString()),fieldValue.toString(),Integer.parseInt(idValue.toString()));  
				
			}
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		return recordFound;
	}

	

	@Override
	public boolean FirstChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
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
