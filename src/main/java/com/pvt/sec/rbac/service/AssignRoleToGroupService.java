/**
 * 
 */
package com.pvt.sec.rbac.service;

import javax.servlet.http.HttpServletRequest;

import com.config.exception.common.CustomRuntimeException;
import com.config.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.pvt.sec.rbac.dto.AppGroupRoleDTO;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AssignRoleToGroupService extends FieldValueWithParentIdExists{
public DataTableResults<AppGroupRoleDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;
	
    public AppGroupRoleDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public AppGroupRoleDTO saveAndUpdate(AppGroupRoleDTO appGroupRoleDTO)throws CustomRuntimeException;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

	public boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;

}
