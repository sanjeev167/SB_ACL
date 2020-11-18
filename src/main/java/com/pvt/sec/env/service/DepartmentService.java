/**
 * 
 */
package com.pvt.sec.env.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.config.exception.common.CustomRuntimeException;
import com.pvt.sec.env.dto.DepartmentMasterDTO;

import com.config.validation.interfaceForServices.FieldValueExists;
import com.share.NameValueM;
import com.share.grid_pagination.DataTableResults;


/**
 * @author Sanjeev
 *
 */
public interface DepartmentService extends FieldValueExists{
	
  public DataTableResults<DepartmentMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery) throws CustomRuntimeException;

    public DepartmentMasterDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public DepartmentMasterDTO saveAndUpdate(DepartmentMasterDTO departmentMasterDTO)throws CustomRuntimeException;

	public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

	void deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;

	public List<NameValueM> getDepartmentList()throws CustomRuntimeException;
    
  
}
