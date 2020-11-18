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

import com.pvt.sec.acl.monitor.dto.AclEntryDTO;
import com.pvt.sec.acl.monitor.entity.AclEntry;
import com.pvt.sec.acl.monitor.repo.AclEntryRepository;

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
public class AclEntryServiceImpl implements AclEntryService{
	
	static final Logger log = LoggerFactory.getLogger(AclEntryServiceImpl.class);
	/** The entity manager. */
	@Autowired
   @PersistenceContext(unitName= AclPersistentConstant.JPA_UNIT_ACL_MONITOR)
	private EntityManager entityManager;

	@Autowired
	AclEntryRepository aclEntryRepository;

	// Using constructor mapping
	@Override
	public DataTableResults<AclEntryDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery) throws CustomRuntimeException {
		log.info("     AclEntryServiceImpl :==> loadGrid ==> Started");
		
		DataTableResults<AclEntryDTO> dataTableResult=null;
		try {
		DataTableRequest<AclEntry> dataTableInRQ = new DataTableRequest<AclEntry>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		
		String baseQuery = "SELECT id, acl_object_identity, ace_order, sid,mask, granting, audit_success, audit_failure,"
				+ " (SELECT COUNT(1) FROM acl_entry "+whereClauseForBaseQuery+") AS totalrecords  FROM acl_entry "+whereClauseForBaseQuery;
		
		String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
		//log.info("paginatedQuery = "+paginatedQuery);
		Query query = entityManager.createNativeQuery(paginatedQuery, "AclEntryDTOMapping");
		@SuppressWarnings("unchecked")
		List<AclEntryDTO> aclEntryList = query.getResultList();
		dataTableResult = new DataTableResults<AclEntryDTO>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(aclEntryList);
		if (!AppUtil.isObjectEmpty(aclEntryList)) {
			dataTableResult.setRecordsTotal(aclEntryList.get(0).getTotalrecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(aclEntryList.get(0).getTotalrecords().toString());
			} else {
				dataTableResult.setRecordsFiltered(Integer.toString(aclEntryList.size()));
			}
		}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AclEntryServiceImpl :==> loadGrid ==> Ended");
		return dataTableResult;
	}

}
