/**
 * 
 */
package com.pvt.sec.rbac.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.share.NameValue;
import com.share.NameValueM;
import com.pvt.sec.rbac.dto.AppAdminUserDTO;
import com.config.exception.common.CustomRuntimeException;
import com.config.validation.interfaceForServices.FieldValueExists;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface UserAccountService extends FieldValueExists{
	
	    public DataTableResults<AppAdminUserDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;

	    public AppAdminUserDTO getReordById(Integer id)throws CustomRuntimeException;
	    
	    public AppAdminUserDTO saveAndUpdate(AppAdminUserDTO appAdminUserDTO)throws CustomRuntimeException;

		public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

		public boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
		public List<NameValueM> getAppAdminUserList(Integer id)throws CustomRuntimeException;

		public List<NameValue> getAppAdminUserListForACL(Integer id)throws CustomRuntimeException;


}
