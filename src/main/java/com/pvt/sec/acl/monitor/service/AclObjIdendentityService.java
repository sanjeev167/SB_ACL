/**
 * 
 */
package com.pvt.sec.acl.monitor.service;

import javax.servlet.http.HttpServletRequest;


import com.config.exception.common.CustomRuntimeException;
import com.pvt.sec.acl.monitor.dto.AclObjectDTO;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AclObjIdendentityService {

	DataTableResults<AclObjectDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)throws CustomRuntimeException ;

}
