/**
 * 
 */
package com.pvt.sec.rbac.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.config.exception.common.CustomRuntimeException;
import com.config.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.pvt.sec.rbac.dto.RoleHierarchyDTO;
import com.share.NameValueM;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */

public interface RoleHierarchyService extends FieldValueWithParentIdExists{
	
	public DataTableResults<RoleHierarchyDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;
	

    public RoleHierarchyDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public RoleHierarchyDTO saveAndUpdate(RoleHierarchyDTO menuManagerDTO)throws CustomRuntimeException;

	public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

	boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
	
	
	public List<NameValueM> getRoleList()throws CustomRuntimeException;
	public List<NameValueM> getTreeParentNodeList()throws CustomRuntimeException;
	
	
	public ArrayList<Object> getRoleHierarchyLevelWiseStructure()throws CustomRuntimeException;
	
	public Integer getMinId()throws CustomRuntimeException;
	
	public Map<String, List<String>> getRoleHierarchyConfiguredForSecurity()throws CustomRuntimeException;
	
		
}
