/**
 * 
 */
package com.pvt.sec.rbac.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.share.NameValueM;
import com.config.exception.common.CustomRuntimeException;
import com.config.validation.interfaceForServices.FieldValueExists;
import com.pvt.sec.rbac.dto.WebUserDTO;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface WebUserAccountService extends FieldValueExists{
	
	    public DataTableResults<WebUserDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;

	    public WebUserDTO getReordById(Long id)throws CustomRuntimeException;
	    
	    public WebUserDTO saveAndUpdate(WebUserDTO webUserDTO)throws CustomRuntimeException;

		public boolean deleteRecordById(Long id)throws CustomRuntimeException;

		public boolean deleteMultipleRecords(Long[] recordIdArray)throws CustomRuntimeException;
		public List<NameValueM> getWebUserList(Long id)throws CustomRuntimeException;
}
