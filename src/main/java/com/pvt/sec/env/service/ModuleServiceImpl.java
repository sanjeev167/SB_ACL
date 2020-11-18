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
import com.pvt.sec.env.dto.ModuleMasterDTO;
import com.pvt.sec.env.entity.ModuleMaster;
import com.pvt.sec.env.repo.DepartmentRepository;
import com.pvt.sec.env.repo.ModuleRepository;
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
public class ModuleServiceImpl implements ModuleService {

	static final Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);

	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppPersistentConstant.APP_JPA_UNIT)
	private EntityManager entityManager;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	// Using constructor mapping
	@Override
	public DataTableResults<ModuleMasterDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {

		log.info("     ModuleServiceImpl :==> loadGrid ==> Started");
		
		DataTableResults<ModuleMasterDTO> dataTableResult=null;
		try {
			DataTableRequest<ModuleMaster> dataTableInRQ = new DataTableRequest<ModuleMaster>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;
			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "SELECT mm.id as id, dm.name as departmentName, mm.name as moduleName ,  (SELECT COUNT(1) FROM module_master mm "
						+ ") AS totalrecords  FROM module_master mm , department_master dm "
						+ "where mm.department_id=dm.id ";
			else
				baseQuery = "SELECT mm.id as id, dm.name as departmentName, mm.name as moduleName ,  (SELECT COUNT(1) FROM module_master mm where "
						+ whereClauseForBaseQuery + ") AS totalrecords  FROM module_master mm , department_master dm "
						+ "where mm.department_id=dm.id and " + whereClauseForBaseQuery;
			// log.info("baseQuery = "+baseQuery);

			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "ModuleMasterDTOMapping");

			@SuppressWarnings("unchecked")
			List<ModuleMasterDTO> moduleMasterList = query.getResultList();

			dataTableResult = new DataTableResults<ModuleMasterDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(moduleMasterList);
			if (!AppUtil.isObjectEmpty(moduleMasterList)) {
				dataTableResult.setRecordsTotal(moduleMasterList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(moduleMasterList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(moduleMasterList.size()));
				}
			}

		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		
		log.info("     ModuleServiceImpl :==> loadGrid ==> Ended");

		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public ModuleMasterDTO getReordById(Integer id) throws CustomRuntimeException {

		/*
		 * List<StateMasterDTO> stateMasterDTOs = entityManager
		 * .createQuery("select new com.pvt.masters.dto.StateMasterDTO(" +
		 * " sm.id as id, " + " cm.name as countryName, " + " cm.id as countryId, " +
		 * " sm.name as stateName )" + " from StateMaster sm, " + " CountryMaster cm " +
		 * " where sm.id =:id  " + " and sm.country_id=cm.id ", StateMasterDTO.class)
		 * .setParameter("id", id).getResultList();
		 * 
		 * return stateMasterDTOs.get(0);
		 */
		log.info("     ModuleServiceImpl :==> getReordById ==> Started");

		ModuleMasterDTO moduleMasterDTO;
		try {
			ModuleMaster moduleMaster = moduleRepository.getOne(id);
			moduleMasterDTO = new ModuleMasterDTO();
			moduleMasterDTO.setId(moduleMaster.getId());
			moduleMasterDTO.setModuleName(moduleMaster.getName());
			moduleMasterDTO.setDepartmentId(moduleMaster.getDepartmentMaster().getId() + "");
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     ModuleServiceImpl :==> getReordById ==> Ended");

		return moduleMasterDTO;
	}

	@Override
	public ModuleMasterDTO saveAndUpdate(ModuleMasterDTO moduleMasterDTO) throws CustomRuntimeException {

		log.info("     ModuleServiceImpl :==> saveAndUpdate ==> Started");

		ModuleMaster moduleMaster;
		ModuleMasterDTO moduleMasterDTONew;
		try {
			if (moduleMasterDTO.getId() != null)
				moduleMaster = moduleRepository.getOne(moduleMasterDTO.getId());
			else
				moduleMaster = new ModuleMaster();

			moduleMaster.setId(moduleMasterDTO.getId());
			moduleMaster.setName(moduleMasterDTO.getModuleName());
			moduleMaster.setDepartmentMaster(
					departmentRepository.getOne(Integer.parseInt(moduleMasterDTO.getDepartmentId())));

			ModuleMaster returnedModuleMaster = moduleRepository.saveAndFlush(moduleMaster);

			moduleMasterDTONew = new ModuleMasterDTO(returnedModuleMaster.getId(),
					returnedModuleMaster.getDepartmentMaster().getName(),
					returnedModuleMaster.getDepartmentMaster().getId(), returnedModuleMaster.getName());

		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     ModuleServiceImpl :==> saveAndUpdate ==> Ended");

		return moduleMasterDTONew;
	}

	@Override
	public boolean deleteOneRecord(Integer id) throws CustomRuntimeException {

		log.info("     ModuleServiceImpl :==> deleteOneRecord ==> Started");
		try {
			moduleRepository.deleteById(id);
			;
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     ModuleServiceImpl :==> deleteOneRecord ==> Ended");

		return true;
	}

	
	@Override
	public void deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {
		log.info("     ModuleServiceImpl :==> deleteMultipleRecords ==> Started");
		try {
			moduleRepository.deleteModuleWithIds(recordIdArray);
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     ModuleServiceImpl :==> deleteMultipleRecords ==> Ended");
	}

	@Override
	public List<NameValueM> getModuleList(Integer id) throws CustomRuntimeException {

		log.info("     ModuleServiceImpl :==> getModuleList ==> Started");

		List<NameValueM> moduleList = new ArrayList<NameValueM>();
		NameValueM nameValue;
		try {
			List<ModuleMaster> moduleMasterList = departmentRepository.getOne(id).getModuleMasters();
			for (ModuleMaster moduleMaster : moduleMasterList) {
				nameValue = new NameValueM(moduleMaster.getId(), moduleMaster.getName());
				moduleList.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     ModuleServiceImpl :==> getModuleList ==> Ended");

		return moduleList;
	}

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName,Object idValue, String idFieldName) throws CustomRuntimeException {

		log.info("     ModuleServiceImpl :==> FieldValueWithParentIdAndChildExists ==> Started");

		boolean recordFound = false;
		try {
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("departmentId")&&!fieldName.equals("moduleName") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.moduleRepository.existsByDepartmentIdAndModuleName(Integer.parseInt(parentIdValue+""),
						fieldValue.toString());    				
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.moduleRepository.existsByDepartmentIdAndModuleNameExceptThisId(Integer.parseInt(parentIdValue+""),
						fieldValue.toString(),Integer.parseInt(idValue.toString()));  
				
			}
			
			
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     ModuleServiceImpl :==> FieldValueWithParentIdAndChildExists ==> Ended");

		return recordFound;

	}

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentValue, String parentId, Object fieldValue,
			String fieldName) throws UnsupportedOperationException, CustomRuntimeException {
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
