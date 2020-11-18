/**
 * 
 */
package com.pvt.sec.rbac.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.pvt.sec.rbac.dto.WebUserDTO;
import com.pvt.sec.rbac.entity.UserGroup;
import com.pvt.sec.rbac.entity.WebUser;
import com.pvt.sec.rbac.repo.AppGroupRepository;
import com.pvt.sec.rbac.repo.UserCategoryRepository;
import com.pvt.sec.rbac.repo.UserGroupRepository;
import com.pvt.sec.rbac.repo.WebUserRepository;
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
public class WebUserAccountServiceImpl implements WebUserAccountService {

	static final Logger log = LoggerFactory.getLogger(WebUserAccountServiceImpl.class);

	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppPersistentConstant.APP_JPA_UNIT)
	private EntityManager entityManager;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	UserCategoryRepository userCategoryRepository;
	
	@Autowired
	AppGroupRepository appGroupRepository;
	
	
	@Autowired
	UserGroupRepository userGroupRepository;

	@Autowired
	WebUserRepository webUserRepository;

	// Using constructor mapping
	@Override
	public DataTableResults<WebUserDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery) throws CustomRuntimeException {

		log.info("     WebUserAccountServiceImpl :==> loadGrid ==> Started");
		DataTableResults<WebUserDTO> dataTableResult = null;
		try {
			DataTableRequest<WebUser> dataTableInRQ = new DataTableRequest<WebUser>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;
			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "select ur.id as ID," + " dm.name as departmentName," + " uc.name as categoryName,"
						+ " ur.name as name,"
						+ " ur.email as userLoginId,"
						+ " ur.authprovider as authprovider,"
						+ " ur.active_c as activeOrNot,"						
						+ " (SELECT COUNT(1) FROM web_user ur, user_category uc , department_master dm where ur.user_category_id=uc.id and uc.department_id=dm.id ) AS totalrecords"
						+ " FROM web_user ur, user_category uc , department_master dm"
						+ " where ur.user_category_id=uc.id and uc.department_id=dm.id ";
			else
				baseQuery = "select ur.id as ID," + " dm.name as departmentName," + " uc.name as categoryName,"
						+ " ur.name as name,"
						+ " ur.email as userLoginId,"
						+ " ur.authprovider as authprovider,"
						+ " ur.active_c as activeOrNot,"	
						+ " (SELECT COUNT(1) FROM web_user ur, user_category uc , department_master dm where ur.user_category_id=uc.id and uc.department_id=dm.id and "
						+ whereClauseForBaseQuery + " ) AS totalrecords"
						+ " FROM web_user ur, user_category uc , department_master dm"
						+ " where ur.user_category_id=uc.id and uc.department_id=dm.id and " + whereClauseForBaseQuery;

			//log.info("baseQuery ="+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "WebUserDTOMapping");

			@SuppressWarnings("unchecked")
			List<WebUserDTO> webUserList = query.getResultList();

			dataTableResult = new DataTableResults<WebUserDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(webUserList);
			if (!AppUtil.isObjectEmpty(webUserList)) {
				dataTableResult.setRecordsTotal(webUserList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(webUserList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(webUserList.size()));
				}
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     WebUserAccountServiceImpl :==> loadGrid ==> Ended");
		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public WebUserDTO getReordById(Long id) throws CustomRuntimeException {
		log.info("     WebUserAccountServiceImpl :==> getReordById ==> Started");
		WebUserDTO webUserDTO = null;
		try {
			WebUser webUser = webUserRepository.getOne(id);
			webUserDTO = new WebUserDTO();
			webUserDTO.setId(webUser.getId());
			webUserDTO.setDepartmentNameId(webUser.getUserCategory().getDepartmentMaster().getId() + "");
			webUserDTO.setCategoryNameId(webUser.getUserCategory().getId() + "");
			webUserDTO.setName(webUser.getName());
			webUserDTO.setUserLoginId(webUser.getEmail());
			webUserDTO.setPassword(webUser.getPassword());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     WebUserAccountServiceImpl :==> getReordById ==> Ended");
		return webUserDTO;
	}

	
	@Override
	public WebUserDTO saveAndUpdate(WebUserDTO webUserDTO) throws CustomRuntimeException {
		log.info("     WebUserAccountServiceImpl :==> saveAndUpdate ==> Started");

		WebUser webUser = null;
		WebUserDTO webUserDTONew = null;
		try {
			if (webUserDTO.getId() != null)
				webUser = webUserRepository.getOne(webUserDTO.getId());
			else
				webUser = new WebUser();

			webUser.setId(webUserDTO.getId());
			webUser.setName(webUserDTO.getName());
			webUser.setEmail(webUserDTO.getUserLoginId());
			webUser.setUserCategory(
					userCategoryRepository.getOne(Integer.parseInt(webUserDTO.getCategoryNameId())));
			webUser.setPassword(webUserDTO.getPassword());			
			webUser.setAuthprovider("Application");
			
			// create a object 
			webUser.setCreatedOn(new java.sql.Time(new Date().getTime()));				
			WebUser returnedWebUser = webUserRepository.saveAndFlush(webUser);
			
			//This is hard coded as the web users group association can not be created in advance.
			UserGroup userGroup=new UserGroup();	
			userGroup.setWebUser(returnedWebUser);
			userGroup.setAppGroup(appGroupRepository.getOne(6));
			userGroupRepository.saveAndFlush(userGroup);
			//////////////////////////////////////////////////////////////////////////
			
			webUserDTONew = new WebUserDTO(returnedWebUser.getId(),
					returnedWebUser.getUserCategory().getDepartmentMaster().getId() + "",
					
					returnedWebUser.getUserCategory().getId() + "",returnedWebUser.getName(), returnedWebUser.getEmail(),
					returnedWebUser.getPassword());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     WebUserAccountServiceImpl :==> saveAndUpdate ==> Ended");

		return webUserDTONew;
	}

	@Override
	public boolean deleteRecordById(Long id) throws CustomRuntimeException {
		log.info("     WebUserAccountServiceImpl :==> deleteRecordById ==> Started");
		boolean isDeleted = true;
		try {
			webUserRepository.deleteById(id);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     WebUserAccountServiceImpl :==> deleteRecordById ==> Ended");
		return isDeleted;
	}

	@Override
	
	public boolean deleteMultipleRecords(Long[] recordIdArray) throws CustomRuntimeException {

		log.info("     WebUserAccountServiceImpl :==> deleteMultipleRecords ==> Started");
		boolean isDeleted = true;
		try {
			webUserRepository.deleteAppAdminUserWithIds(recordIdArray);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     WebUserAccountServiceImpl :==> deleteMultipleRecords ==> Ended");
		return isDeleted;
	}

	@Override
	public List<NameValueM> getWebUserList(Long id) throws CustomRuntimeException {
		log.info("     WebUserAccountServiceImpl :==> getAppAdminUserList ==> Started");
		List<NameValueM> webUserListNew = new ArrayList<NameValueM>();
		NameValueM nameValue = null; 
		try {
			List<WebUser> webUserList = userCategoryRepository.getOne(Integer.parseInt(id+"")).getWebUsers();
			for (WebUser webUser : webUserList) {
				nameValue = new NameValueM(Integer.parseInt(webUser.getId()+""), webUser.getEmail());
				webUserListNew.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     WebUserAccountServiceImpl :==> getAppAdminUserList ==> Ended");
		return webUserListNew;
	}

	
	

	@Override
	public boolean fieldValueExists(Object value, String fieldName) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fieldValueExists(Object loginIdValue, String loginIdName, Object idValue, String id)
			throws CustomRuntimeException {
		log.info("     WebUserAccountServiceImpl :==> fieldValueExists ==> Started");

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
				recordFound=this.webUserRepository.existsByUserLoginId(loginIdValue.toString());    			
				
			}			
			if(!loginIdValue.equals("")&& idValue!=null) { 
				//Case of editing existing one				
				recordFound=this.webUserRepository.existsByUserLoginIdExceptThisId(loginIdValue.toString(),Long.parseLong(idValue.toString()));  
								
			}		
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     WebUserAccountServiceImpl :==> fieldValueExists ==> Ended");
		return recordFound;
	}

}
