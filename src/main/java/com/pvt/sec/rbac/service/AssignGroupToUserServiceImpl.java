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

import com.pvt.sec.rbac.repo.AppAdminUserRepository;
import com.pvt.sec.env.repo.DepartmentRepository;
import com.pvt.sec.rbac.dto.AppUserGroupDTO;
import com.pvt.sec.rbac.entity.AppGroupRole;
import com.pvt.sec.rbac.entity.UserGroup;
import com.pvt.sec.rbac.repo.AppGroupRepository;
import com.pvt.sec.rbac.repo.UserGroupRepository;
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
public class AssignGroupToUserServiceImpl implements AssignGroupToUserService {

	static final Logger log = LoggerFactory.getLogger(AssignGroupToUserServiceImpl.class);

	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppPersistentConstant.APP_JPA_UNIT)
	private EntityManager entityManager;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	AppGroupRepository appGroupRepository;
	@Autowired
	AppAdminUserRepository appAdminUserRepository;
	@Autowired
	UserGroupRepository userGroupRepository;

	@Override
	public DataTableResults<AppUserGroupDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {

		log.info("     AssignGroupToUserServiceImpl :==> loadGrid ==> Started");
		DataTableResults<AppUserGroupDTO> dataTableResult = null;
		try {
			DataTableRequest<AppGroupRole> dataTableInRQ = new DataTableRequest<AppGroupRole>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;

			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "select ug.id as ID," + "dm.name as departmentName," + "ag.group_name as groupName,"
						+ "uc.name as categoryName," + "ur.email as userName,"
						+ "(SELECT COUNT(1) FROM user_group ug, app_group ag ,user_category uc , user_reg ur, department_master dm "
						+ " where ug.app_group_id=ag.id and ug.user_id=ur.id and ur.user_category_id=uc.id and ag.department_id=dm.id ) AS totalrecords "
						+ "FROM user_group ug, app_group ag ,user_category uc , user_reg ur, department_master dm where ug.app_group_id=ag.id and ug.user_id=ur.id  and ur.user_category_id=uc.id and ag.department_id=dm.id ";

			else
				baseQuery = "select ug.id as ID," + "dm.name as departmentName," + "ag.group_name as groupName,"
						+ "uc.name as categoryName," + "ur.email as userName,"
						+ "(SELECT COUNT(1) FROM user_group ug, app_group ag ,user_category uc , user_reg ur, department_master dm "
						+ " where ug.app_group_id=ag.id and ug.user_id=ur.id and ur.user_category_id=uc.id and ag.department_id=dm.id  and "
						+ whereClauseForBaseQuery + ") AS totalrecords "
						+ "FROM user_group ug, app_group ag ,user_category uc , user_reg ur, department_master dm where ug.app_group_id=ag.id and ug.user_id=ur.id  and ur.user_category_id=uc.id and ag.department_id=dm.id  and "
						+ whereClauseForBaseQuery;

			// log.info("baseQuery ="+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "AppUserGroupDTOMapping");

			@SuppressWarnings("unchecked")
			List<AppUserGroupDTO> appUserGroupList = query.getResultList();

			dataTableResult = new DataTableResults<AppUserGroupDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(appUserGroupList);
			if (!AppUtil.isObjectEmpty(appUserGroupList)) {
				dataTableResult.setRecordsTotal(appUserGroupList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(appUserGroupList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(appUserGroupList.size()));
				}
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AssignGroupToUserServiceImpl :==> loadGrid ==> Ended");

		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public AppUserGroupDTO getReordById(Integer id) throws CustomRuntimeException {

		log.info("     AssignGroupToUserServiceImpl :==> getReordById ==> Started");
		AppUserGroupDTO appUserGroupDTO = null;
		try {
			UserGroup userGroup = userGroupRepository.getOne(id);
			appUserGroupDTO = new AppUserGroupDTO();
			appUserGroupDTO.setId(userGroup.getId());
			appUserGroupDTO.setDepartmentNameId(userGroup.getAppGroup().getDepartmentMaster().getId() + "");
			appUserGroupDTO.setGroupNameId(userGroup.getAppGroup().getId() + "");
			appUserGroupDTO.setCategoryNameId(userGroup.getUserReg().getUserCategory().getId() + "");
			appUserGroupDTO.setUserNameId(userGroup.getUserReg().getId() + "");
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AssignGroupToUserServiceImpl :==> getReordById ==> Ended");

		return appUserGroupDTO;
	}

	@Override
	public AppUserGroupDTO saveAndUpdate(AppUserGroupDTO appUserGroupDTO) throws CustomRuntimeException {

		log.info("     AssignGroupToUserServiceImpl :==> saveAndUpdate ==> Started");

		UserGroup userGroup = null;
		AppUserGroupDTO appUserGroupDTONew = null;
		try {
			if (appUserGroupDTO.getId() != null)
				userGroup = userGroupRepository.getOne(appUserGroupDTO.getId());
			else {
				userGroup = new UserGroup();
				userGroup.setId(appUserGroupDTO.getId());
			}

			userGroup.setAppGroup(appGroupRepository.getOne(Integer.parseInt(appUserGroupDTO.getGroupNameId())));
			userGroup.setUserReg(appAdminUserRepository.getOne(Integer.parseInt(appUserGroupDTO.getUserNameId())));

			UserGroup returnedUserGroup = userGroupRepository.saveAndFlush(userGroup);
			appUserGroupDTONew = new AppUserGroupDTO(returnedUserGroup.getId(),
					returnedUserGroup.getAppGroup().getDepartmentMaster().getId() + "",
					returnedUserGroup.getAppGroup().getId() + "",
					returnedUserGroup.getUserReg().getUserCategory().getId() + "",
					returnedUserGroup.getUserReg().getId() + "");
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     AssignGroupToUserServiceImpl :==> saveAndUpdate ==> Ended");

		return appUserGroupDTONew;
	}

	@Override
	public boolean deleteRecordById(Integer id) throws CustomRuntimeException {

		log.info("     AssignGroupToUserServiceImpl :==> deleteRecordById ==> Started");
		boolean isDeleted = true;
		try {
			userGroupRepository.deleteById(id);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AssignGroupToUserServiceImpl :==> deleteRecordById ==> Ended");

		return isDeleted;
	}

	
	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {

		log.info("     AssignGroupToUserServiceImpl :==> deleteMultipleRecords ==> Started");
		boolean isDeletedSelected = true;
		try {
			userGroupRepository.deleteWithIds(recordIdArray);
		} catch (Exception ex) {
			isDeletedSelected = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AssignGroupToUserServiceImpl :==> deleteMultipleRecords ==> Ended");
		return isDeletedSelected;
	}

	@Override
	public boolean FirstChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {

		log.info("     AssignGroupToUserServiceImpl :==> FirstChildValueWithParentIdExists ==> Started");
		boolean recordFound = false;
		try {			
			Assert.notNull(parentId);Assert.notNull(fieldName);
			if (!parentId.equals("groupNameId")&&!fieldName.equals("userNameId") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}
			if (parentIdValue == null && fieldValue==null) {
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.userGroupRepository.existsByGroupIdAndUserId(Integer.parseInt(parentIdValue+""),
						Integer.parseInt(fieldValue+""));    			
				
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.userGroupRepository.existsByGroupIdAndUserIdExceptThisId(Integer.parseInt(parentIdValue.toString()),
						Integer.parseInt(fieldValue.toString()),Integer.parseInt(idValue.toString()));  
								
			}
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AssignGroupToUserServiceImpl :==> FirstChildValueWithParentIdExists ==> Ended");
		return recordFound;
	}

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		log.info("     AssignGroupToUserServiceImpl :==> FieldValueWithParentIdAndChildExists ==> Started");
		// TODO Auto-generated method stub
		log.info("     AssignGroupToUserServiceImpl :==> FieldValueWithParentIdAndChildExists ==> Ended");
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