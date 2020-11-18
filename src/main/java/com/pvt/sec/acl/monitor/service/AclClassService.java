/**
 * 
 */
package com.pvt.sec.acl.monitor.service;

import javax.servlet.http.HttpServletRequest;

import com.pvt.sec.acl.monitor.dto.AclClassDTO;
import com.config.exception.common.CustomRuntimeException;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AclClassService{

	DataTableResults<AclClassDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)throws CustomRuntimeException ;

}
