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

import com.config.db.AppPersistentConstant;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.env.repo.DepartmentRepository;
import com.pvt.sec.rbac.dto.AppGroupDTO;
import com.pvt.sec.rbac.entity.AppGroup;
import com.pvt.sec.rbac.repo.AppGroupRepository;
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
public class AppGroupServiceImpl implements AppGroupService {

	static final Logger log = LoggerFactory.getLogger(AppGroupServiceImpl.class);

	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppPersistentConstant.APP_JPA_UNIT)
	private EntityManager entityManager;

	@Autowired
	AppGroupRepository appGroupRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	// Using constructor mapping
	@Override
	public DataTableResults<AppGroupDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> loadGrid ==> Started");
		DataTableResults<AppGroupDTO> dataTableResult = null;
		try {
			DataTableRequest<AppGroup> dataTableInRQ = new DataTableRequest<AppGroup>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;
			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "SELECT ag.id as id, dm.name as departmentName, ag.group_name as groupName ,  (SELECT COUNT(1) FROM app_group ag, department_master dm where ag.department_id=dm.id "
						+ ") AS totalrecords  FROM app_group ag , department_master dm "
						+ "where ag.department_id=dm.id ";
			else
				baseQuery = "SELECT ag.id as id, dm.name as departmentName, ag.group_name as groupName ,  (SELECT COUNT(1) FROM app_group ag, department_master dm where ag.department_id=dm.id and "
						+ whereClauseForBaseQuery + ") AS totalrecords  FROM app_group ag , department_master dm "
						+ "where ag.department_id=dm.id and " + whereClauseForBaseQuery;
			// log.info("baseQuery = "+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "AppGroupDTOMapping");

			@SuppressWarnings("unchecked")
			List<AppGroupDTO> appGroupList = query.getResultList();

			dataTableResult = new DataTableResults<AppGroupDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(appGroupList);
			if (!AppUtil.isObjectEmpty(appGroupList)) {
				dataTableResult.setRecordsTotal(appGroupList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(appGroupList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(appGroupList.size()));
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
	public AppGroupDTO getReordById(Integer id) throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> getReordById ==> Started");
		AppGroupDTO appGroupDTO = null;
		try {
			AppGroup appGroup = appGroupRepository.getOne(id);
			appGroupDTO = new AppGroupDTO();
			appGroupDTO.setId(appGroup.getId());
			appGroupDTO.setGroupName(appGroup.getGroupName());
			appGroupDTO.setDepartmentNameId(appGroup.getDepartmentMaster().getId() + "");
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AppGroupServiceImpl :==> getReordById ==> Ended");
		return appGroupDTO;
	}

	@Override
	public AppGroupDTO saveAndUpdate(AppGroupDTO appGroupDTO) throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> saveAndUpdate ==> Started");

		AppGroup appGroup = null;
		AppGroupDTO appGroupDTONew = null;
		try {
			if (appGroupDTO.getId() != null)
				appGroup = appGroupRepository.getOne(appGroupDTO.getId());
			else
				appGroup = new AppGroup();

			appGroup.setId(appGroupDTO.getId());
			appGroup.setGroupName(appGroupDTO.getGroupName());
			appGroup.setDepartmentMaster(
					departmentRepository.getOne(Integer.parseInt(appGroupDTO.getDepartmentNameId())));
			AppGroup returnedAppGroup = appGroupRepository.saveAndFlush(appGroup);
			appGroupDTONew = new AppGroupDTO(returnedAppGroup.getId(), returnedAppGroup.getDepartmentMaster().getName(),
					returnedAppGroup.getDepartmentMaster().getId(), returnedAppGroup.getGroupName());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AppGroupServiceImpl :==> saveAndUpdate ==> Ended");

		return appGroupDTONew;
	}

	@Override
	public boolean deleteRecordById(Integer id) throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> deleteRecordById ==> Started");
		
		boolean isDelected = true;
		try {
			appGroupRepository.deleteById(id);
		} catch (Exception ex) {
			isDelected = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		
		log.info("     AppGroupServiceImpl :==> deleteRecordById ==> Ended");

		return isDelected;
	}

	
	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> deleteMultipleRecords ==> Started");
		
		boolean isDelectedAllSelected = true;
		try {
			appGroupRepository.deleteAppGroupWithIds(recordIdArray);
		} catch (Exception ex) {
			isDelectedAllSelected = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		
		log.info("     AppGroupServiceImpl :==> deleteMultipleRecords ==> Ended");
		
		return isDelectedAllSelected;
	}

	@Override
	public List<NameValueM> getAppGroupList(Integer id) throws CustomRuntimeException {

		log.info("     AppGroupServiceImpl :==> getAppGroupList ==> Started");

		List<NameValueM> appGroupList = new ArrayList<NameValueM>();
		NameValueM nameValue = null;
		try {
			List<AppGroup> appGroupListComing = departmentRepository.getOne(id).getAppGroups();
			for (AppGroup appGroup : appGroupListComing) {
				nameValue = new NameValueM(appGroup.getId(), appGroup.getGroupName());
				appGroupList.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		
		log.info("     AppGroupServiceImpl :==> getAppGroupList ==> Ended");
		
		return appGroupList;
	}

	@Override
	public boolean FirstChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName , Object idValue, String idFieldName) throws CustomRuntimeException {
		
		log.info("     AppGroupServiceImpl :==> FirstChildValueWithParentIdExists ==> Started");
		
		boolean recordFound = false;
		try {
			
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("departmentNameId")&&!fieldName.equals("groupName") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.appGroupRepository.existsByDepartmentIdAndAppGroupName(Integer.parseInt(parentIdValue+""),
						fieldValue.toString());    			
				
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.appGroupRepository.existsByDepartmentIdAndGroupExceptThisId(Integer.parseInt(parentIdValue+""),
						fieldValue.toString(),Integer.parseInt(idValue.toString()));  
				
				
			}
			
			
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
}
