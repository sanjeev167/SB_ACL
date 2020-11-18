/**
 * 
 */
package com.pvt.sec.rbac.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import com.share.NameValueM;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.env.service.DepartmentService;
import com.pvt.sec.rbac.dto.RoleHierarchyDTO;
import com.pvt.sec.rbac.service.RoleHierarchyService;
import com.share.JsonResp.JsonRes;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping(value = "/sec/roleHierarchy/")
public class RoleHierarchyController{
	static final Logger log = LoggerFactory.getLogger(RoleHierarchyController.class);

	@Autowired
	RoleHierarchyService roleHierarchyService;
	@Autowired
	DepartmentService departmentService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getRoleHierarchy(Model model) {
		log.info("RoleHierarchyController :==> getRoleHierarchy :==> Started");
		String target = "/sec/roleHierarchy";
		log.info("RoleHierarchyController :==> getRoleHierarchy :==> Ended");
		return target;
	}


	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listRoleHierarchyPaginated(HttpServletRequest request, HttpServletResponse response, 
			String contextId
			){
		log.info("RoleHierarchyController :==> listRoleHierarchyPaginated :==> Started");		
		DataTableResults<RoleHierarchyDTO> dataTableResults=null;
		try {
			String whereClauseForBaseQuery = "";
			if (!contextId.isEmpty() && !contextId.equals(""))
				whereClauseForBaseQuery =  "  rh.department_id=" + Integer.parseInt(contextId);		
			dataTableResults=roleHierarchyService.loadGrid(request, whereClauseForBaseQuery);
		}
		catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("RoleHierarchyController :==> listRoleHierarchyPaginated :==> End");
		return new Gson().toJson(dataTableResults);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response){
		log.info("RoleHierarchyController :==> getRecord :==> Started");
		JsonRes jsonResponse = new JsonRes();		
		try {
			jsonResponse.setFormObject(roleHierarchyService.getReordById(id));
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("Record found.");
		}catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("RoleHierarchyController :==> getRecord :==> End");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid RoleHierarchyDTO roleHierarchyDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {		
		log.info("RoleHierarchyController :==> saveAndUpdateRecord :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			if (result.hasErrors()) {
				// Get error message
				Map<String, String> errors = result.getFieldErrors().stream()
						.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
				jsonResponse.setStatus(false);
				jsonResponse.setStatusMsg("Error found");
				jsonResponse.setFieldErrMsgMap(errors);
				log.info("errors = "+errors);
			} else {
				// Implement business logic to save record into database
				jsonResponse.setFormObject(roleHierarchyService.saveAndUpdate(roleHierarchyDTO));
				jsonResponse.setStatus(true);
				jsonResponse.setStatusMsg("Record has been saved or updated successfully.");
			}
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("RoleHierarchyController :==> saveAndUpdateRecord :==> End");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("RoleHierarchyController :==> deleteRecord :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			jsonResponse.setStatus(roleHierarchyService.deleteOneRecord(id));
			jsonResponse.setStatusMsg("Record has been deleted successfully.");			
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("RoleHierarchyController :==> deleteRecord :==> End");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelected(@RequestBody Integer[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("RoleHierarchyController :==> deleteSelected :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {			
			jsonResponse.setStatus(roleHierarchyService.deleteMultipleRecords(recordIdArray));
			jsonResponse.setStatusMsg("All selected records have been deleted.");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("RoleHierarchyController :==> deleteSelected :==> End");
		return new Gson().toJson(jsonResponse);

	}
	
	@RequestMapping(value = "getTreeParentNodeList", method = RequestMethod.GET)
	@ResponseBody
	public String getTreeParentNodeList(HttpServletRequest request, HttpServletResponse response) {
		log.info("RoleHierarchyController :==> getTreeParentNodeList :==> Started");		
		JsonRes jsonResponse = new JsonRes();
		try {
			List<NameValueM> moduleList = roleHierarchyService.getTreeParentNodeList();
			jsonResponse.setComboList(moduleList);
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("All records have been fetched.");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("RoleHierarchyController :==> getTreeParentNodeList :==> Ended");		
		return new Gson().toJson(jsonResponse);
	}

	
	
	@RequestMapping(value = "getRoleList", method = RequestMethod.GET)
	@ResponseBody
	public String getRoleList(HttpServletRequest request, HttpServletResponse response) {
		log.info("RoleHierarchyController :==> getRoleList :==> Started");		
		JsonRes jsonResponse = new JsonRes();
		try {
			List<NameValueM> moduleList = roleHierarchyService.getRoleList();
			jsonResponse.setComboList(moduleList);
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("All records have been fetched.");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("RoleHierarchyController :==> getRoleList :==> Ended");		
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "getRoleHierarchyLevelWiseStructure", method = RequestMethod.GET)
	@ResponseBody
	public String getRoleHierarchyLevelWiseStructure(HttpServletRequest request, HttpServletResponse response) {
		log.info("RoleHierarchyController :==> getRoleHierarchyLevelWiseStructure :==> Started");		
		JsonRes jsonResponse = new JsonRes();
		try {
			ArrayList<Object> roleHierarchyList= roleHierarchyService.getRoleHierarchyLevelWiseStructure();
			jsonResponse.setFormObject(roleHierarchyList);			
			jsonResponse.setRecordId(roleHierarchyService.getMinId()+"");
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("All records have been fetched.");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("RoleHierarchyController :==> getRoleHierarchyLevelWiseStructure :==> Ended");		
		return new Gson().toJson(jsonResponse);
	}
	

	@RequestMapping(value = "department/list", method = RequestMethod.GET)
	@ResponseBody
	public String departmentList(HttpServletRequest request, HttpServletResponse response) {
		
		log.info("DepartmentMasterController :==> departmentList :==> Started");
		
		JsonRes jsonResponse = new JsonRes();
		try {
			List<NameValueM> departmentList = departmentService.getDepartmentList();
			jsonResponse.setComboList(departmentList);
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("All records have been fetched.");
		}
		catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		
		log.info("DepartmentMasterController :==> departmentList :==> Ended");
		
		return new Gson().toJson(jsonResponse);
	}
	
	
}//End of RoleHierarchyController
