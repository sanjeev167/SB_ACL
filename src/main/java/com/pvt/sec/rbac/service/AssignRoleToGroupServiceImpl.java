/**
 * 
 */
package com.pvt.sec.rbac.service;

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

import com.pvt.sec.env.repo.DepartmentRepository;
import com.pvt.sec.rbac.dto.AppGroupRoleDTO;
import com.pvt.sec.rbac.entity.AppGroupRole;
import com.pvt.sec.rbac.repo.AppGroupRepository;
import com.pvt.sec.rbac.repo.AppGroupRoleRepository;
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
public class AssignRoleToGroupServiceImpl implements AssignRoleToGroupService {

	static final Logger log = LoggerFactory.getLogger(AssignRoleToGroupServiceImpl.class);
	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppPersistentConstant.APP_JPA_UNIT)
	private EntityManager entityManager;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	AppGroupRepository appGroupRepository;
	@Autowired
	AppRoleRepository appRoleRepository;
	@Autowired
	AppGroupRoleRepository appGroupRoleRepository;

	@Override
	public DataTableResults<AppGroupRoleDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {

		log.info("     AssignRoleToGroupServiceImpl :==> loadGrid ==> Started");

		DataTableResults<AppGroupRoleDTO> dataTableResult = null;
		try {
			DataTableRequest<AppGroupRole> dataTableInRQ = new DataTableRequest<AppGroupRole>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;

			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "select arg.id as ID," + "dm.name as departmentName," + "ag.group_name as groupName,"
						+ "ar.role_name as roleName,"
						+ "(SELECT COUNT(1) FROM app_group_role arg, app_group ag , app_role ar, department_master dm "
						+ " where arg.app_group_id=ag.id and arg.app_role_id=ar.id  and ar.department_id=dm.id ) AS totalrecords "
						+ "from app_group_role arg, app_group ag , app_role ar,department_master dm where arg.app_group_id=ag.id and arg.app_role_id=ar.id and ar.department_id=dm.id ";

			else
				baseQuery = "select arg.id as ID," + "dm.name as departmentName," + "ag.group_name as groupName,"
						+ "ar.role_name as roleName,"
						+ "(SELECT COUNT(1) FROM app_group_role arg, app_group ag , app_role ar, department_master dm "
						+ " where arg.app_group_id=ag.id and arg.app_role_id=ar.id  and ar.department_id=dm.id and "
						+ whereClauseForBaseQuery + ") AS totalrecords "
						+ "from app_group_role arg, app_group ag , app_role ar,department_master dm where arg.app_group_id=ag.id and arg.app_role_id=ar.id and ar.department_id=dm.id and "
						+ whereClauseForBaseQuery;

			// log.info("baseQuery ="+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "AppGroupRoleDTOMapping");

			@SuppressWarnings("unchecked")
			List<AppGroupRoleDTO> appGroupRoleList = query.getResultList();

			dataTableResult = new DataTableResults<AppGroupRoleDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(appGroupRoleList);
			if (!AppUtil.isObjectEmpty(appGroupRoleList)) {
				dataTableResult.setRecordsTotal(appGroupRoleList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(appGroupRoleList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(appGroupRoleList.size()));
				}
			}

		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AssignRoleToGroupServiceImpl :==> loadGrid ==> Ended");

		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public AppGroupRoleDTO getReordById(Integer id) throws CustomRuntimeException {
		log.info("     AssignRoleToGroupServiceImpl :==> getReordById ==> Started");
		AppGroupRoleDTO appGroupRoleDTO = null;
		try {
			AppGroupRole appGroupRole = appGroupRoleRepository.getOne(id);
			appGroupRoleDTO = new AppGroupRoleDTO();
			appGroupRoleDTO.setId(appGroupRole.getId());
			appGroupRoleDTO.setDepartmentNameId(appGroupRole.getAppGroup().getDepartmentMaster().getId() + "");
			appGroupRoleDTO.setGroupNameId(appGroupRole.getAppGroup().getId() + "");
			appGroupRoleDTO.setRoleNameId(appGroupRole.getAppRole().getId() + "");
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AssignRoleToGroupServiceImpl :==> getReordById ==> Ended");
		return appGroupRoleDTO;
	}

	@Override
	public AppGroupRoleDTO saveAndUpdate(AppGroupRoleDTO appGroupRoleDTO) throws CustomRuntimeException {
		log.info("     AssignRoleToGroupServiceImpl :==> saveAndUpdate ==> Started");
		AppGroupRole appGroupRole = null;
		AppGroupRoleDTO appGroupRoleDTONew = null;
		try {
			if (appGroupRoleDTO.getId() != null)
				appGroupRole = appGroupRoleRepository.getOne(appGroupRoleDTO.getId());
			else
				appGroupRole = new AppGroupRole();
			appGroupRole.setId(appGroupRoleDTO.getId());
			appGroupRole.setAppGroup(appGroupRepository.getOne(Integer.parseInt(appGroupRoleDTO.getGroupNameId())));
			appGroupRole.setAppRole(appRoleRepository.getOne(Integer.parseInt(appGroupRoleDTO.getRoleNameId())));
			AppGroupRole returnedAppGroupRole = appGroupRoleRepository.saveAndFlush(appGroupRole);
			appGroupRoleDTONew = new AppGroupRoleDTO(returnedAppGroupRole.getId(),
					returnedAppGroupRole.getAppGroup().getDepartmentMaster().getId() + "",
					returnedAppGroupRole.getAppGroup().getId() + "", returnedAppGroupRole.getAppRole().getId() + "");
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AssignRoleToGroupServiceImpl :==> saveAndUpdate ==> Ended");
		return appGroupRoleDTONew;
	}

	@Override
	public boolean deleteRecordById(Integer id) throws CustomRuntimeException {

		log.info("     AssignRoleToGroupServiceImpl :==> deleteRecordById ==> Started");
		boolean isDeleted = true;
		try {
			appGroupRoleRepository.deleteById(id);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AssignRoleToGroupServiceImpl :==> deleteRecordById ==> Ended");
		return isDeleted;
	}

	
	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {
		log.info("     AssignRoleToGroupServiceImpl :==> deleteMultipleRecords ==> Started");
		boolean isDeletedSelected = true;
		try {
			appGroupRoleRepository.deleteAppGroupRoleWithIds(recordIdArray);
		} catch (Exception ex) {
			isDeletedSelected = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AssignRoleToGroupServiceImpl :==> deleteMultipleRecords ==> Ended");
		return isDeletedSelected;
	}

	@Override
	public boolean FirstChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		log.info("     AssignRoleToGroupServiceImpl :==> FirstChildValueWithParentIdExists ==> Started");
		boolean recordFound = false;
		try {
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("groupNameId")&&!fieldName.equals("roleNameId") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.appGroupRoleRepository.existsByGroupIdAndRoleName(Integer.parseInt(parentIdValue+""),
						Integer.parseInt(fieldValue+""));    			
				log.info("Add : Record found = "+recordFound);
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.appGroupRoleRepository.existsByGroupIdAndRoleNameExceptThisId(Integer.parseInt(parentIdValue+""),
						Integer.parseInt(fieldValue+""),Integer.parseInt(idValue.toString()));  
				log.info("Edit : Record found = "+recordFound);
				
			}
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AssignRoleToGroupServiceImpl :==> FirstChildValueWithParentIdExists ==> Ended");
		return recordFound;
	}

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		log.info("     AssignRoleToGroupServiceImpl :==> FieldValueWithParentIdAndChildExists ==> Started");
		// TODO Auto-generated method stub
		log.info("     AssignRoleToGroupServiceImpl :==> FieldValueWithParentIdAndChildExists ==> Ended");
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

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentValue, String parentId, Object fieldValue,
			String fieldName) throws UnsupportedOperationException, CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

}