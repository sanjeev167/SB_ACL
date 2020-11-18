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

import com.pvt.sec.rbac.repo.AppAdminUserRepository;
import com.pvt.sec.rbac.repo.AppRoleRepository;
import com.share.NameValueM;
import com.pvt.sec.env.repo.DepartmentRepository;
import com.pvt.sec.rbac.dto.AppAdminUserDTO;
import com.pvt.sec.rbac.entity.AppGroupRole;
import com.pvt.sec.rbac.entity.UserGroup;
import com.pvt.sec.rbac.entity.UserReg;
import com.pvt.sec.rbac.repo.UserCategoryRepository;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.share.grid_pagination.DataTableRequest;
import com.share.grid_pagination.DataTableResults;
import com.share.grid_pagination.PaginationCriteria;
import com.config.db.AppPersistentConstant;
import com.share.AppUtil;
import com.share.NameValue;

/**
 * @author Sanjeev
 *
 */
@Service
@Transactional("appTransactionManager")
public class UserAccountServiceImpl implements UserAccountService {

	static final Logger log = LoggerFactory.getLogger(UserAccountServiceImpl.class);

	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppPersistentConstant.APP_JPA_UNIT)
	private EntityManager entityManager;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	UserCategoryRepository userCategoryRepository;

	@Autowired
	AppAdminUserRepository appAdminUserRepository;
	
	@Autowired
	AppRoleRepository appRoleRepository;
	

	// Using constructor mapping
	@Override
	public DataTableResults<AppAdminUserDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery) throws CustomRuntimeException {

		log.info("     UserAccountServiceImpl :==> loadGrid ==> Started");
		DataTableResults<AppAdminUserDTO> dataTableResult = null;
		try {
			DataTableRequest<UserReg> dataTableInRQ = new DataTableRequest<UserReg>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;
			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "select ur.id as ID," + " dm.name as departmentName," + " uc.name as categoryName,"
						+ " ur.name as name,"
						+ " ur.email as userLoginId,"
						+ " (SELECT COUNT(1) FROM user_reg ur, user_category uc , department_master dm where ur.user_category_id=uc.id and uc.department_id=dm.id ) AS totalrecords"
						+ " FROM user_reg ur, user_category uc , department_master dm"
						+ " where ur.user_category_id=uc.id and uc.department_id=dm.id ";
			else
				baseQuery = "select ur.id as ID," + " dm.name as departmentName," + " uc.name as categoryName,"
						+ " ur.name as name,"
						+ " ur.email as userLoginId,"
						+ " (SELECT COUNT(1) FROM user_reg ur, user_category uc , department_master dm where ur.user_category_id=uc.id and uc.department_id=dm.id and "
						+ whereClauseForBaseQuery + " ) AS totalrecords"
						+ " FROM user_reg ur, user_category uc , department_master dm"
						+ " where ur.user_category_id=uc.id and uc.department_id=dm.id and " + whereClauseForBaseQuery;

			log.info("baseQuery ="+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "AppAdminUserDTOMapping");

			@SuppressWarnings("unchecked")
			List<AppAdminUserDTO> appAdminUserList = query.getResultList();

			dataTableResult = new DataTableResults<AppAdminUserDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(appAdminUserList);
			if (!AppUtil.isObjectEmpty(appAdminUserList)) {
				dataTableResult.setRecordsTotal(appAdminUserList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(appAdminUserList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(appAdminUserList.size()));
				}
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserAccountServiceImpl :==> loadGrid ==> Ended");
		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public AppAdminUserDTO getReordById(Integer id) throws CustomRuntimeException {
		log.info("     UserAccountServiceImpl :==> getReordById ==> Started");
		AppAdminUserDTO appAdminUserDTO = null;
		try {
			UserReg userReg = appAdminUserRepository.getOne(id);
			appAdminUserDTO = new AppAdminUserDTO();
			appAdminUserDTO.setId(userReg.getId());
			appAdminUserDTO.setDepartmentNameId(userReg.getUserCategory().getDepartmentMaster().getId() + "");
			appAdminUserDTO.setCategoryNameId(userReg.getUserCategory().getId() + "");
			appAdminUserDTO.setName(userReg.getName());
			appAdminUserDTO.setUserLoginId(userReg.getEmail());
			appAdminUserDTO.setPassword(userReg.getPassword());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserAccountServiceImpl :==> getReordById ==> Ended");
		return appAdminUserDTO;
	}

	@Override
	public AppAdminUserDTO saveAndUpdate(AppAdminUserDTO appAdminUserDTO) throws CustomRuntimeException {
		log.info("     UserAccountServiceImpl :==> saveAndUpdate ==> Started");

		UserReg userReg = null;
		AppAdminUserDTO appAdminUserDTONew = null;
		try {
			if (appAdminUserDTO.getId() != null)
				userReg = appAdminUserRepository.getOne(appAdminUserDTO.getId());
			else
				userReg = new UserReg();

			userReg.setId(appAdminUserDTO.getId());
			userReg.setName(appAdminUserDTO.getName());
			userReg.setEmail(appAdminUserDTO.getUserLoginId());
			userReg.setUserCategory(
					userCategoryRepository.getOne(Integer.parseInt(appAdminUserDTO.getCategoryNameId())));
			userReg.setPassword(appAdminUserDTO.getPassword());
			UserReg returnedUserReg = appAdminUserRepository.saveAndFlush(userReg);

			appAdminUserDTONew = new AppAdminUserDTO(returnedUserReg.getId(),
					returnedUserReg.getUserCategory().getDepartmentMaster().getId() + "",
					
					returnedUserReg.getUserCategory().getId() + "",returnedUserReg.getName(), returnedUserReg.getEmail(),
					returnedUserReg.getPassword());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserAccountServiceImpl :==> saveAndUpdate ==> Ended");

		return appAdminUserDTONew;
	}

	@Override
	public boolean deleteRecordById(Integer id) throws CustomRuntimeException {
		log.info("     UserAccountServiceImpl :==> deleteRecordById ==> Started");
		boolean isDeleted = true;
		try {
			appAdminUserRepository.deleteById(id);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserAccountServiceImpl :==> deleteRecordById ==> Ended");
		return isDeleted;
	}

	
	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {

		log.info("     UserAccountServiceImpl :==> deleteMultipleRecords ==> Started");
		boolean isDeleted = true;
		try {
			appAdminUserRepository.deleteAppAdminUserWithIds(recordIdArray);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserAccountServiceImpl :==> deleteMultipleRecords ==> Ended");
		return isDeleted;
	}

	@Override
	public List<NameValueM> getAppAdminUserList(Integer id) throws CustomRuntimeException {
		log.info("     UserAccountServiceImpl :==> getAppAdminUserList ==> Started");
		List<NameValueM> userRegListNew = new ArrayList<NameValueM>();
		NameValueM nameValue = null;
		try {
			List<UserReg> userRegList = userCategoryRepository.getOne(id).getUserRegs();
			for (UserReg userReg : userRegList) {
				nameValue = new NameValueM(userReg.getId(), userReg.getEmail());
				userRegListNew.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserAccountServiceImpl :==> getAppAdminUserList ==> Ended");
		return userRegListNew;
	}

	@Override
	public List<NameValue> getAppAdminUserListForACL(Integer id) throws CustomRuntimeException {
		log.info("     UserAccountServiceImpl :==> getAppAdminUserList ==> Started");
		List<NameValue> userRegListNew = new ArrayList<NameValue>();
		NameValue nameValue = null;
		
		try {
			List<AppGroupRole> groupRoleList =appRoleRepository.getOne(id).getAppGroupRoles(); 
								
			for (AppGroupRole appGroupRole : groupRoleList) {
				List<UserGroup>UserGroups=appGroupRole.getAppGroup().getUserGroups();
				for(UserGroup userGroup:UserGroups) {	
					if(userGroup.getUserReg()!=null) {//Checked For admin users only.
					//For web users, it should be implemented differently. 
					//Additional parameter app-context needs to be received	
					nameValue = new NameValue(userGroup.getUserReg().getId(), userGroup.getUserReg().getEmail());	
					userRegListNew.add(nameValue);}
				}				
			}
		} catch (Exception ex) {ex.printStackTrace();
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserAccountServiceImpl :==> getAppAdminUserList ==> Ended");
		return userRegListNew;
	}
	

	@Override
	public boolean fieldValueExists(Object value, String fieldName) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fieldValueExists(Object loginIdValue, String loginIdName, Object idValue, String id)
			throws CustomRuntimeException {
		log.info("     UserAccountServiceImpl :==> fieldValueExists ==> Started");

		boolean recordFound = false;
		try {
			Assert.notNull(loginIdName);Assert.notNull(id);
			if (!loginIdName.equals("userLoginId")&& !id.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (loginIdValue == null ) {
				return false;
			}
			if(!loginIdValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.appAdminUserRepository.existsByUserLoginId(loginIdValue.toString());    			
				
			}			
			if(!loginIdValue.equals("")&& idValue!=null) { 
				//Case of editing existing one				
				recordFound=this.appAdminUserRepository.existsByUserLoginIdExceptThisId(loginIdValue.toString(),Integer.parseInt(idValue.toString()));  
								
			}		
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     UserAccountServiceImpl :==> fieldValueExists ==> Ended");
		return recordFound;
	}

}
