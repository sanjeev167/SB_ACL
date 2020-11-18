/**
 * 
 */
package com.pvt.sec.acl.monitor.service;

import javax.servlet.http.HttpServletRequest;

import com.pvt.sec.acl.monitor.dto.AclSidDTO;
import com.config.exception.common.CustomRuntimeException;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AclSidService {

	DataTableResults<AclSidDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)throws CustomRuntimeException ;

}
