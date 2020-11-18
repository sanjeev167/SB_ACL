/**
 * 
 */
package com.pvt.sec.env.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.config.exception.common.CustomRuntimeException;
import com.pvt.sec.env.dto.PageMasterDTO;
import com.config.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.share.NameValueM;
import com.share.grid_pagination.DataTableResults;



/**
 * @author Sanjeev
 *
 */
public interface PageService extends FieldValueWithParentIdExists{
	public DataTableResults<PageMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;
	
	public DataTableResults<PageMasterDTO> loadGridForRbacCMP(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;
	
	
	public DataTableResults<PageMasterDTO> loadGridFor_RoleAssignedAndUnassigned(HttpServletRequest request,String moduleId,String roleId,String pageViewCondition)throws CustomRuntimeException;
	
	
	
    public PageMasterDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public PageMasterDTO saveAndUpdate(PageMasterDTO pageMasterDTO)throws CustomRuntimeException;

	public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

	void deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
	
	public List<NameValueM> getPageList(Integer id)throws CustomRuntimeException;
	public List<NameValueM> getListBaseUrl(Integer id)throws CustomRuntimeException;
	
	
	
}
