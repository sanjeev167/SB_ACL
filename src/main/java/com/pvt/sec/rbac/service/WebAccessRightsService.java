/**
 * 
 */
package com.pvt.sec.rbac.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.config.exception.common.CustomRuntimeException;
import com.pvt.sec.rbac.dto.WebAccessRightsDTO;
import com.pvt.sec.rbac.entity.AppRole;
import com.pvt.sec.rbac.entity.WebAccessRights;
import com.share.NameValueM;
import com.share.grid_pagination.DataTableResults;



/**
 * @author Sanjeev
 *   
 */
public interface WebAccessRightsService {
	
	public DataTableResults<WebAccessRightsDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)throws CustomRuntimeException;

	public WebAccessRightsDTO loadContextBasedRolesWithAlreadyAssignedPages(Integer pageId)throws CustomRuntimeException;

	public boolean saveAndUpdate(String accessRightsRbacId,String pageId,String roleId)throws CustomRuntimeException;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

	public boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;

	
	public List<NameValueM> getRoleListHavingRightsOnThisPage(List<WebAccessRights> accessRightsRbacsList)throws CustomRuntimeException;

	public List<NameValueM> getRoleListInDepartment(List<AppRole> roleListInDepartment)throws CustomRuntimeException;
		
	
	
	
	
	public WebAccessRightsDTO loadRoleWithPageAccessCountAfterAssignmentAndRemoval(Integer roleId)throws CustomRuntimeException;
	
	
	public WebAccessRightsDTO savePageAssignment(Integer pageId,String []recordIdArray)throws CustomRuntimeException;
	
	public WebAccessRightsDTO removePageAssignment(Integer roleId,Integer []pageIdArray)throws CustomRuntimeException;
	
	
	
	
}
