/**
 * 
 */
package com.pvt.sec.acl.monitor.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pvt.sec.acl.monitor.dto.AclClassDTO;
import com.pvt.sec.acl.monitor.entity.AclClass;
import com.pvt.sec.acl.monitor.repo.AclClassRepository;

import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.config.mutable.db.AclPersistentConstant;
import com.share.grid_pagination.DataTableRequest;
import com.share.grid_pagination.DataTableResults;
import com.share.grid_pagination.PaginationCriteria;

import com.share.AppUtil;

/**
 * @author Sanjeev
 *
 */
@Service
public class AclClassServiceImpl implements AclClassService{
	
	static final Logger log = LoggerFactory.getLogger(AclClassServiceImpl.class);
	/** The entity manager. */
	@Autowired
   @PersistenceContext(unitName= AclPersistentConstant.JPA_UNIT_ACL_MONITOR)
	private EntityManager entityManager;

	@Autowired
	AclClassRepository aclClassRepository;

	// Using constructor mapping
	@Override
	public DataTableResults<AclClassDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery) throws CustomRuntimeException {
		log.info("    AclClassServiceImpl :==> loadGrid ==> Started");
		
		DataTableResults<AclClassDTO> dataTableResult=null;
		try {
		DataTableRequest<AclClass> dataTableInRQ = new DataTableRequest<AclClass>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		
		String baseQuery = "SELECT id as id, class as classWithPkg,"
				+ " (SELECT COUNT(1) FROM acl_class "+whereClauseForBaseQuery+") AS totalrecords  FROM acl_class "+whereClauseForBaseQuery;
		
		String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
		Query query = entityManager.createNativeQuery(paginatedQuery, "AclClassDTOMapping");
		@SuppressWarnings("unchecked")
		List<AclClassDTO> aclClassList = query.getResultList();
		
		
		
		dataTableResult = new DataTableResults<AclClassDTO>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(aclClassList);
		if (!AppUtil.isObjectEmpty(aclClassList)) {
			dataTableResult.setRecordsTotal(aclClassList.get(0).getTotalrecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(aclClassList.get(0).getTotalrecords().toString());
			} else {
				dataTableResult.setRecordsFiltered(Integer.toString(aclClassList.size()));
			}
		}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("    AclClassServiceImpl :==> loadGrid ==> Ended");
		return dataTableResult;
	}

	

}
