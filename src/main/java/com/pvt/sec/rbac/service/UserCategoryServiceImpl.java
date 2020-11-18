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
import com.pvt.sec.rbac.dto.UserCategoryDTO;
import com.pvt.sec.rbac.entity.UserCategory;
import com.pvt.sec.rbac.repo.UserCategoryRepository;
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
public class UserCategoryServiceImpl implements UserCategoryService {

	static final Logger log = LoggerFactory.getLogger(UserCategoryServiceImpl.class);

	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppPersistentConstant.APP_JPA_UNIT)
	private EntityManager entityManager;

	@Autowired
	UserCategoryRepository userCategoryRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	// Using constructor mapping
	@Override
	public DataTableResults<UserCategoryDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {

		log.info("     UserCategoryServiceImpl :==> loadGrid ==> Started");
		DataTableResults<UserCategoryDTO> dataTableResult = null;
		try {
			DataTableRequest<UserCategory> dataTableInRQ = new DataTableRequest<UserCategory>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;
			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "SELECT uc.id as id, dm.name as departmentName, uc.name as categoryName ,  (SELECT COUNT(1) FROM user_category uc, department_master dm where uc.department_id=dm.id "
						+ ") AS totalrecords  FROM user_category uc , department_master dm "
						+ "where uc.department_id=dm.id ";
			else
				baseQuery = "SELECT uc.id as id, dm.name as departmentName, uc.name as categoryName ,  (SELECT COUNT(1) FROM user_category uc, department_master dm where uc.department_id=dm.id and "
						+ whereClauseForBaseQuery + ") AS totalrecords  FROM user_category uc , department_master dm "
						+ "where uc.department_id=dm.id and " + whereClauseForBaseQuery;
			// log.info("baseQuery = "+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "UserCategoryDTOMapping");

			@SuppressWarnings("unchecked")
			List<UserCategoryDTO> appRoleList = query.getResultList();

			dataTableResult = new DataTableResults<UserCategoryDTO>();
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
		log.info("     UserCategoryServiceImpl :==> loadGrid ==> Started1");

		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public UserCategoryDTO getReordById(Integer id) throws CustomRuntimeException {
		log.info("     UserCategoryServiceImpl :==> loadGrid ==> Started");
		UserCategoryDTO userCategoryDTO = null;
		try {
			UserCategory userCategory = userCategoryRepository.getOne(id);
			userCategoryDTO = new UserCategoryDTO();
			userCategoryDTO.setId(userCategory.getId());
			userCategoryDTO.setCategoryName(userCategory.getName());
			userCategoryDTO.setDepartmentNameId(userCategory.getDepartmentMaster().getId() + "");
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserCategoryServiceImpl :==> loadGrid ==> Started1");
		return userCategoryDTO;
	}

	@Override
	public UserCategoryDTO saveAndUpdate(UserCategoryDTO userCategoryDTO) throws CustomRuntimeException {

		log.info("     UserCategoryServiceImpl :==> loadGrid ==> Started");
		UserCategoryDTO userCategoryDTONew = null;
		UserCategory userCategory = null;
		try {
			if (userCategoryDTO.getId() != null)
				userCategory = userCategoryRepository.getOne(userCategoryDTO.getId());
			else
				userCategory = new UserCategory();

			userCategory.setId(userCategoryDTO.getId());
			userCategory.setName(userCategoryDTO.getCategoryName());
			userCategory.setDepartmentMaster(
					departmentRepository.getOne(Integer.parseInt(userCategoryDTO.getDepartmentNameId())));

			UserCategory returnedUserCategory = userCategoryRepository.saveAndFlush(userCategory);

			userCategoryDTONew = new UserCategoryDTO(returnedUserCategory.getId(),
					returnedUserCategory.getDepartmentMaster().getName(),
					returnedUserCategory.getDepartmentMaster().getId(), returnedUserCategory.getName());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     UserCategoryServiceImpl :==> loadGrid ==> Started1");
		return userCategoryDTONew;
	}

	@Override
	public boolean deleteRecordById(Integer id) throws CustomRuntimeException {
		log.info("     UserCategoryServiceImpl :==> loadGrid ==> Started");
		boolean isDeleted = true;
		try {
			userCategoryRepository.deleteById(id);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserCategoryServiceImpl :==> loadGrid ==> Started1");
		return isDeleted;
	}

	
	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {
		log.info("     UserCategoryServiceImpl :==> loadGrid ==> Started");
		boolean isDeletedSelected = true;
		try {
			userCategoryRepository.deleteUserCategoryWithIds(recordIdArray);
		} catch (Exception ex) {
			isDeletedSelected = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserCategoryServiceImpl :==> loadGrid ==> Started1");
		return isDeletedSelected;
	}

	@Override
	public List<NameValueM> getUserCategoryList(Integer id) throws CustomRuntimeException {
		log.info("     UserCategoryServiceImpl :==> loadGrid ==> Started");

		List<NameValueM> userCategoryList = new ArrayList<NameValueM>();
		NameValueM nameValue = null;
		try {
			List<UserCategory> userCategoryListComing = departmentRepository.getOne(id).getUserCategories();
			for (UserCategory userCategory : userCategoryListComing) {
				nameValue = new NameValueM(userCategory.getId(), userCategory.getName());
				userCategoryList.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserCategoryServiceImpl :==> loadGrid ==> Started1");
		return userCategoryList;
	}

	@Override
	public boolean FirstChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {

		log.info("     UserCategoryServiceImpl :==> FirstChildValueWithParentIdExists ==> Started");
		boolean recordFound = false;
		try {
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("departmentNameId")&&!fieldName.equals("categoryName") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.userCategoryRepository.existsByDepartmentIdAndUserCategory(Integer.parseInt(parentIdValue+""),
						fieldValue.toString());    			
				
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.userCategoryRepository.existsByDepartmentIdAndUserCategoryExceptThisId(Integer.parseInt(parentIdValue+""),
						fieldValue.toString(),Integer.parseInt(idValue.toString()));  
				
				
			}
			
//			
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserCategoryServiceImpl :==> FirstChildValueWithParentIdExists ==> Started1");

		return recordFound;
	}

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentIdValue, String parentId, Object fieldValue,
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

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentValue, String parentId, Object fieldValue,
			String fieldName) throws UnsupportedOperationException, CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}
}
