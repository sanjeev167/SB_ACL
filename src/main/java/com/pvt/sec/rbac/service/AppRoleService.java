/**
 * 
 */
package com.pvt.sec.rbac.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.share.NameValue;
import com.share.NameValueM;
import com.pvt.sec.rbac.dto.AppRoleDTO;
import com.config.exception.common.CustomRuntimeException;


import com.config.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AppRoleService extends FieldValueWithParentIdExists{
	
	public DataTableResults<AppRoleDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;

    public AppRoleDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public AppRoleDTO saveAndUpdate(AppRoleDTO appRoleDTO)throws CustomRuntimeException;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

	boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
	public List<NameValueM> getAppRoleList(Integer id)throws CustomRuntimeException;
	public List<NameValueM> getAppRoleList()throws CustomRuntimeException;
	
	public List<NameValueM> getAppRoleBasedUsersList(Integer selectedRoleId)throws CustomRuntimeException;

	public String getRoleId(String userOrRoleName);
}
