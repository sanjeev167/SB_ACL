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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.config.db.AppPersistentConstant;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.env.dto.DepartmentMasterDTO;
import com.pvt.sec.env.entity.DepartmentMaster;
import com.pvt.sec.env.repo.DepartmentRepository;

import com.share.AppUtil;
import com.share.NameValueM;
import com.share.grid_pagination.DataTableRequest;
import com.share.grid_pagination.DataTableResults;
import com.share.grid_pagination.PaginationCriteria;
@Service
@Transactional("appTransactionManager")
public class DepartmentServiceImpl implements DepartmentService {

	static final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);
	
	/** The entity manager. */
	@Autowired
   @PersistenceContext( unitName= AppPersistentConstant.APP_JPA_UNIT)
	private EntityManager entityManager;

	@Autowired
	DepartmentRepository departmentRepository;

	// Using constructor mapping
	@Override
	public DataTableResults<DepartmentMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery) throws CustomRuntimeException {
		
		log.info("     DepartmentServiceImpl :==> loadGrid ==> Started");
		
		DataTableResults<DepartmentMasterDTO> dataTableResult=null;
		try {
		DataTableRequest<DepartmentMaster> dataTableInRQ = new DataTableRequest<DepartmentMaster>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		
		String baseQuery = "SELECT id as id, name as departmentName, "
				+ " (SELECT COUNT(1) FROM department_master "+whereClauseForBaseQuery+") AS totalrecords  FROM department_master "+whereClauseForBaseQuery;
		
		//log.info("baseQuery = "+baseQuery);
		String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
		Query query = entityManager.createNativeQuery(paginatedQuery, "DepartmentMasterDTOMapping");
		@SuppressWarnings("unchecked")
		List<DepartmentMasterDTO> departmentMasterList = query.getResultList();
		dataTableResult = new DataTableResults<DepartmentMasterDTO>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(departmentMasterList);
		if (!AppUtil.isObjectEmpty(departmentMasterList)) {
			dataTableResult.setRecordsTotal(departmentMasterList.get(0).getTotalrecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(departmentMasterList.get(0).getTotalrecords().toString());
			} else {
				dataTableResult.setRecordsFiltered(Integer.toString(departmentMasterList.size()));
			}
		}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DepartmentServiceImpl :==> loadGrid ==> Ended");
		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public DepartmentMasterDTO getReordById(Integer id) throws CustomRuntimeException {
		log.info("     DepartmentServiceImpl :==> getReordById ==> Started");
		List<DepartmentMasterDTO> departmentMasterDTOs;
		try {
		departmentMasterDTOs = entityManager
				.createQuery("select new com.pvt.sec.env.dto.DepartmentMasterDTO(" 
				                                                                + " d.id, "
								                                                + " d.name as departmentName ) " 
								                                                + " from DepartmentMaster d " 
								                                                + " where d.id =:id",
								                                                DepartmentMasterDTO.class)
				                                                                .setParameter("id", id).getResultList();	
		}catch (Exception ex) {	throw ExceptionApplicationUtility.wrapRunTimeException(ex);	}

		log.info("     DepartmentServiceImpl :==> getReordById ==> Ended");
		return departmentMasterDTOs.get(0);
	}

	@Override
	public DepartmentMasterDTO saveAndUpdate(DepartmentMasterDTO departmentMasterDTO) throws CustomRuntimeException {
		log.info("     DepartmentServiceImpl :==> saveAndUpdate ==> Started");
		DepartmentMaster departmentMaster;
		DepartmentMasterDTO departmentMasterDTONew;
		try {
		if(departmentMasterDTO.getId()!=null)
			departmentMaster =departmentRepository.getOne(departmentMasterDTO.getId());
		else
		    departmentMaster =new DepartmentMaster();
			departmentMaster.setId(departmentMasterDTO.getId());
			departmentMaster.setName(departmentMasterDTO.getDepartmentName()); 
		
			DepartmentMaster returnedDepartmentMaster = departmentRepository.saveAndFlush(departmentMaster);
		
		    departmentMasterDTONew=new DepartmentMasterDTO(returnedDepartmentMaster.getId(), 
					                                                     returnedDepartmentMaster.getName());
		 }catch (Exception ex) {
				throw ExceptionApplicationUtility.wrapRunTimeException(ex);
			}
			log.info("     DepartmentServiceImpl :==> saveAndUpdate ==> Ended");
			return departmentMasterDTONew;
	}

	@Override
	public boolean deleteOneRecord(Integer id) throws CustomRuntimeException {
		log.info("     DepartmentServiceImpl :==> deleteOneRecord ==> Started");
		try {
		departmentRepository.deleteById(id);
		}catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DepartmentServiceImpl :==> deleteOneRecord ==> Ended");
		return true;
	}
	
	
	@Override
	public void deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {
		log.info("     DepartmentServiceImpl :==> deleteMultipleRecords ==> Started");
		try {
		departmentRepository.deleteDepartmentWithIds(recordIdArray);
		}catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DepartmentServiceImpl :==> deleteMultipleRecords ==> Ended");
	}

	@Override
	public List<NameValueM> getDepartmentList() throws CustomRuntimeException {
		log.info("     DepartmentServiceImpl :==> getDepartmentList ==> Started");
		List<NameValueM> departmentList;
		try {
		 departmentList=new ArrayList<NameValueM>();		
		 NameValueM nameValue;
		List<DepartmentMaster> departmentMasterList=departmentRepository.findAll();		
		for(DepartmentMaster departmentMaster:departmentMasterList) {
			nameValue=new NameValueM(departmentMaster.getId(),departmentMaster.getName());
			departmentList.add(nameValue);
			}	
		}catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DepartmentServiceImpl :==> getDepartmentList ==> Ended");
		return departmentList;
	}

	@Override
	public boolean fieldValueExists(Object fieldValue, String fieldName, Object idValue, String idFieldName)	throws  CustomRuntimeException {
		log.info("     DepartmentServiceImpl :==> fieldValueExists ==> Started");
		 boolean recordFound=false;
		try {
			if (!fieldName.equals("departmentName")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}
			if (!idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}
			if (fieldValue == null) {
				return false;
			}
			//This is an add case
			if(idValue==null) {
				recordFound=this.departmentRepository.existsByDepartmentName(fieldValue.toString()); 				
			}
			else {				
				recordFound=this.departmentRepository.existsByDepartmentNameExceptThisId(fieldValue.toString(),Integer.parseInt(idValue.toString()));				
				
				//if record found other than this id, it means that there is some other record which has this name. Unique check 
				//should be applied otherwise this check should be avoided.			
			}			
			  
		}catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
       log.info("     DepartmentServiceImpl :==> fieldValueExists ==> Ended");
        return recordFound;
	}

	@Override
	public boolean fieldValueExists(Object value, String fieldName) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	

}
