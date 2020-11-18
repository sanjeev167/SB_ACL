/**
 * 
 */
package com.pvt.sec.env.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.config.exception.common.CustomRuntimeException;
import com.pvt.sec.env.dto.ModuleMasterDTO;
import com.config.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.share.NameValueM;
import com.share.grid_pagination.DataTableResults;


/**
 * @author Sanjeev
 *
 */
public interface ModuleService extends FieldValueWithParentIdExists{
	
	    public DataTableResults<ModuleMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;

	    public ModuleMasterDTO getReordById(Integer id)throws CustomRuntimeException;
	    
	    public ModuleMasterDTO saveAndUpdate(ModuleMasterDTO moduleMasterDTO)throws CustomRuntimeException;

		public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

		void deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
		public List<NameValueM> getModuleList(Integer id)throws CustomRuntimeException;
}
