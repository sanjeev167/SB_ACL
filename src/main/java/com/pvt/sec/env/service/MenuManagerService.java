/**
 * 
 */
package com.pvt.sec.env.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.config.exception.common.CustomRuntimeException;
import com.config.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.pvt.sec.env.dto.MenuDTO;
import com.pvt.sec.env.dto.MenuManagerDTO;
import com.share.NameValueM;
import com.share.grid_pagination.DataTableResults;


/**
 * @author Sanjeev
 *
 */

public interface MenuManagerService extends FieldValueWithParentIdExists{
	
	public DataTableResults<MenuManagerDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;
	

    public MenuManagerDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public MenuManagerDTO saveAndUpdate(MenuManagerDTO menuManagerDTO)throws CustomRuntimeException;

	public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

	boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
	
	public List<NameValueM> getListTreeMenuType()throws CustomRuntimeException;
	public List<NameValueM> getListTreeParentNode()throws CustomRuntimeException;
	public ArrayList<MenuDTO> getSpecificTreeTypeStructure(Integer specificTreeTypeId )throws CustomRuntimeException;
	public Map<String, Map<String, List<MenuDTO>>>  getRoleWiseMenuCollectionMap()throws CustomRuntimeException;
	public Integer getMinId()throws CustomRuntimeException;
	
}
