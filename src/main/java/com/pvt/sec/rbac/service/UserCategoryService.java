/**
 * 
 */
package com.pvt.sec.rbac.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.share.NameValueM;
import com.pvt.sec.rbac.dto.UserCategoryDTO;
import com.config.exception.common.CustomRuntimeException;
import com.config.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface UserCategoryService extends FieldValueWithParentIdExists{
	
	public DataTableResults<UserCategoryDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;

    public UserCategoryDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public UserCategoryDTO saveAndUpdate(UserCategoryDTO userCategoryDTO)throws CustomRuntimeException;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

	boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
	public List<NameValueM> getUserCategoryList(Integer id)throws CustomRuntimeException;
}
