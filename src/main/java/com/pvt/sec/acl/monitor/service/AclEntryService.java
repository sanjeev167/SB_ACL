/**
 * 
 */
package com.pvt.sec.acl.monitor.service;

import javax.servlet.http.HttpServletRequest;

import com.pvt.sec.acl.monitor.dto.AclEntryDTO;
import com.config.exception.common.CustomRuntimeException;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AclEntryService {

	DataTableResults<AclEntryDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)throws CustomRuntimeException ;

}
