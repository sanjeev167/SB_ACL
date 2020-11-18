/**
 * 
 */
package com.pvt.sec.rbac.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.config.exception.common.CustomRuntimeException;
import com.config.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.pvt.sec.rbac.dto.AppGroupDTO;
import com.share.NameValueM;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AppGroupService extends FieldValueWithParentIdExists{
	
	public DataTableResults<AppGroupDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;

    public AppGroupDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public AppGroupDTO saveAndUpdate(AppGroupDTO appGroupDTO)throws CustomRuntimeException;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

	boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
	public List<NameValueM> getAppGroupList(Integer id)throws CustomRuntimeException;
}
