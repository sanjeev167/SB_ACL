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

import org.modelmapper.internal.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.share.NameValueM;
import com.pvt.sec.env.repo.DepartmentRepository;
import com.pvt.sec.rbac.dto.AppRoleDTO;
import com.pvt.sec.rbac.entity.AppGroup;
import com.pvt.sec.rbac.entity.AppGroupRole;
import com.pvt.sec.rbac.entity.AppRole;
import com.pvt.sec.rbac.entity.UserGroup;
import com.pvt.sec.rbac.entity.UserReg;
import com.pvt.sec.rbac.repo.AppGroupRepository;
import com.pvt.sec.rbac.repo.AppRoleRepository;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;

import com.share.grid_pagination.DataTableRequest;
import com.share.grid_pagination.DataTableResults;

import com.share.grid_pagination.PaginationCriteria;

import com.config.db.AppPersistentConstant;

import com.share.AppUtil;

/**
 * @author Sanjeev
 *
 */
@Service
@Transactional("appTransactionManager")
public class AppRoleServiceImpl implements AppRoleService {

	static final Logger log = LoggerFactory.getLogger(AppRoleServiceImpl.class);

	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppPersistentConstant.APP_JPA_UNIT)
	private EntityManager entityManager;

	@Autowired
	AppGroupRepository appGroupRepository;

	@Autowired
	AppRoleRepository appRoleRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	// Using constructor mapping
	@Override
	public DataTableResults<AppRoleDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> loadGrid ==> Started");
		
		DataTableResults<AppRoleDTO> dataTableResult=null;
		try {
			DataTableRequest<AppRole> dataTableInRQ = new DataTableRequest<AppRole>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;
			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "SELECT ar.id as id, dm.name as departmentName, ar.role_name as roleName ,  (SELECT COUNT(1) FROM app_role ar, department_master dm where ar.department_id=dm.id "
						+ ") AS totalrecords  FROM app_role ar , department_master dm "
						+ "where ar.department_id=dm.id ";
			else
				baseQuery = "SELECT ar.id as id, dm.name as departmentName, ar.role_name as roleName ,  (SELECT COUNT(1) FROM app_role ar, department_master dm where ar.department_id=dm.id and "
						+ whereClauseForBaseQuery + ") AS totalrecords  FROM app_role ar , department_master dm "
						+ "where ar.department_id=dm.id and " + whereClauseForBaseQuery;
			// log.info("baseQuery = "+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "AppRoleDTOMapping");

			@SuppressWarnings("unchecked")
			List<AppRoleDTO> appRoleList = query.getResultList();

			dataTableResult = new DataTableResults<AppRoleDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(appRoleList);
			if (!AppUtil.isObjectEmpty(appRoleList)) {
				dataTableResult.setRecordsTotal(appRoleList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(appRoleList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(appRoleList.size()));
				}
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		
		log.info("     AppGroupServiceImpl :==> loadGrid ==> Ended");

		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public AppRoleDTO getReordById(Integer id) throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> getReordById ==> Started");
		
		AppRoleDTO appRoleDTO = null;
		try {
			AppRole appRole = appRoleRepository.getOne(id);
			appRoleDTO = new AppRoleDTO();
			appRoleDTO.setId(appRole.getId());
			appRoleDTO.setRoleName(appRole.getRoleName());
			appRoleDTO.setDepartmentNameId(appRole.getDepartmentMaster().getId() + "");
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		
		log.info("     AppGroupServiceImpl :==> getReordById ==> Ended");
		
		return appRoleDTO;
	}

	@Override
	public AppRoleDTO saveAndUpdate(AppRoleDTO appRoleDTO) throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> saveAndUpdate ==> Started");

		AppRole appRole = null;
		AppRoleDTO appRoleDTONew = null;
		
		try {
			if (appRoleDTO.getId() != null)
				appRole = appRoleRepository.getOne(appRoleDTO.getId());
			else
				appRole = new AppRole();

			appRole.setId(appRoleDTO.getId());
			appRole.setRoleName(appRoleDTO.getRoleName());
			appRole.setDepartmentMaster(
					departmentRepository.getOne(Integer.parseInt(appRoleDTO.getDepartmentNameId())));

			AppRole returnedAppRole = appRoleRepository.saveAndFlush(appRole);

			appRoleDTONew = new AppRoleDTO(returnedAppRole.getId(), returnedAppRole.getDepartmentMaster().getName(),
					returnedAppRole.getDepartmentMaster().getId(), returnedAppRole.getRoleName());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AppGroupServiceImpl :==> saveAndUpdate ==> Ended");

		return appRoleDTONew;
	}

	@Override
	public boolean deleteRecordById(Integer id) throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> deleteOneRecord ==> Started");
		boolean isDeleted=true;
		try {
			appRoleRepository.deleteById(id);
		} catch (Exception ex) {
			isDeleted=false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AppGroupServiceImpl :==> deleteOneRecord ==> Ended");

		return isDeleted;
	}

	
	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> deleteMultipleRecords ==> Started");
		boolean isDeletedSelected=true;
		try {
			appRoleRepository.deleteAppRoleWithIds(recordIdArray);
		} catch (Exception ex) {
			isDeletedSelected=false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AppGroupServiceImpl :==> deleteMultipleRecords ==> Ended");
		
		return isDeletedSelected;
	}

	@Override
	public List<NameValueM> getAppRoleList(Integer id) throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> getAppRoleList ==> Started");

		List<NameValueM> appRoleList = new ArrayList<NameValueM>();
		NameValueM nameValue=null;
		try {
			List<AppRole> appRoleListComing = departmentRepository.getOne(id).getAppRoles();
			for (AppRole appRole : appRoleListComing) {
				nameValue = new NameValueM(appRole.getId(), appRole.getRoleName());
				appRoleList.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AppGroupServiceImpl :==> getAppRoleList ==> Ended");

		return appRoleList;
	}

	@Override
	public List<NameValueM> getAppRoleList() throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> getAppRoleList ==> Started");

		List<NameValueM> appRoleList = new ArrayList<NameValueM>();
		NameValueM nameValue=null;
		try {
			List<AppRole> appRoleListComing = appRoleRepository.findAll();
			for (AppRole appRole : appRoleListComing) {
				nameValue = new NameValueM(appRole.getId(), appRole.getRoleName());
				appRoleList.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AppGroupServiceImpl :==> getAppRoleList ==> Ended");

		return appRoleList;
	}
	
	@Override
	public boolean FirstChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws UnsupportedOperationException, CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> FirstChildValueWithParentIdExists ==> Started");
		boolean recordFound = false;
		try {
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("departmentNameId")&&!fieldName.equals("roleName") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.appRoleRepository.existsByDepartmentIdAndAppRoleName(Integer.parseInt(parentIdValue+""),
						fieldValue.toString());    			
				
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.appRoleRepository.existsByDepartmentIdAndRoleExceptThisId(Integer.parseInt(parentIdValue+""),
						fieldValue.toString(),Integer.parseInt(idValue.toString()));  
				
				
			}
			
			///
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		
		log.info("     AppGroupServiceImpl :==> FirstChildValueWithParentIdExists ==> Ended");

		return recordFound;
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
	public List<NameValueM> getAppRoleBasedUsersList(Integer selectedRoleId) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRoleId(String roleName) {
		// TODO Auto-generated method stub
		return appRoleRepository.findByRoleName(roleName);
		
	}

	
}
