/**
 * 
 */
package com.pvt.sec.rbac.ctrl;

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

import com.share.NameValueM;
import com.share.JsonResp.JsonRes;
import com.pvt.sec.env.service.DepartmentService;
import com.pvt.sec.rbac.dto.AppGroupRoleDTO;
import com.pvt.sec.rbac.service.AppGroupService;
import com.pvt.sec.rbac.service.AppRoleService;
import com.pvt.sec.rbac.service.AssignRoleToGroupService;

import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.google.gson.Gson;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */

@Controller
@RequestMapping(value = "/sec/roleToGroup/")
public class AssignRoleToGroupController  {

	static final Logger log = LoggerFactory.getLogger(AssignRoleToGroupController.class);
	@Autowired
	AppRoleService appRoleService;
	
	@Autowired
	AssignRoleToGroupService assignRoleToGroupService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	AppGroupService appGroupService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listRoleToGroup(Model model) {
		log.info("AssignRoleToGroupController :==> listRoleToGroup :==> Started");
		String target = "/sec/group-roles";
		log.info("AssignRoleToGroupController :==> listRoleToGroup :==> Ended");

		return target;
	}// End of open

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listRoleToGroupPaginated(HttpServletRequest request, HttpServletResponse response,
			String departmentNameId, String groupNameId, String roleNameId) {

		log.info("AssignRoleToGroupController :==> listRoleToGroupPaginated :==> Started");
		DataTableResults<AppGroupRoleDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && groupNameId.isEmpty()
					&& groupNameId.equals("") && roleNameId.isEmpty() && roleNameId.equals(""))
				whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId);

			if (!groupNameId.isEmpty() && !groupNameId.equals("") && !departmentNameId.isEmpty()
					&& !departmentNameId.equals("") && roleNameId.isEmpty() && roleNameId.equals(""))
				whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId) + " and arg.app_group_id="
						+ Integer.parseInt(groupNameId);

			if (groupNameId.isEmpty() && groupNameId.equals("") && !departmentNameId.isEmpty()
					&& !departmentNameId.equals("") && !roleNameId.isEmpty() && !roleNameId.equals(""))
				whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId) + " and arg.app_role_id="
						+ Integer.parseInt(roleNameId);
			if (!groupNameId.isEmpty() && !groupNameId.equals("") && !departmentNameId.isEmpty()
					&& !departmentNameId.equals("") && !roleNameId.isEmpty() && !roleNameId.equals(""))
				whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId) + " and arg.app_group_id="
						+ Integer.parseInt(groupNameId) + " and arg.app_role_id=" + Integer.parseInt(roleNameId);
			if (groupNameId.isEmpty() && groupNameId.equals("") && departmentNameId.isEmpty()
					&& departmentNameId.equals("") && roleNameId.isEmpty() && roleNameId.equals(""))
				whereClauseForBaseQuery = "";
			dataTableResult = assignRoleToGroupService.loadGrid(request, whereClauseForBaseQuery);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("AssignRoleToGroupController :==> listRoleToGroupPaginated :==> Ended");

		return new Gson().toJson(dataTableResult);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("AssignRoleToGroupController :==> getRecord :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			jsonResponse.setFormObject(assignRoleToGroupService.getReordById(id));
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("Record found.");
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
		log.info("AssignRoleToGroupController :==> getRecord :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid AppGroupRoleDTO appGroupRoleDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		log.info("AssignRoleToGroupController :==> saveAndUpdateRecord :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			if (result.hasErrors()) {
				// Get error message
				Map<String, String> errors = result.getFieldErrors().stream()
						.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
				jsonResponse.setStatus(false);
				jsonResponse.setStatusMsg("");
				jsonResponse.setFieldErrMsgMap(errors);
			} else {
				// Implement business logic to save record into database
				jsonResponse.setFormObject(assignRoleToGroupService.saveAndUpdate(appGroupRoleDTO));
				jsonResponse.setStatus(true);
				jsonResponse.setStatusMsg("Record has been saved or updated successfully.");
			}
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

		log.info("AssignRoleToGroupController :==> saveAndUpdateRecord :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecordById(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("AssignRoleToGroupController :==> deleteRecordById :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			jsonResponse.setFormObject(assignRoleToGroupService.deleteRecordById(id));
			jsonResponse.setStatusMsg("Record has been deleted successfully.");
			jsonResponse.setStatus(true);
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
		log.info("AssignRoleToGroupController :==> deleteRecordById :==> Ended");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelectedRecords(@RequestBody Integer[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) {

		log.info("AssignRoleToGroupController :==> deleteSelectedRecords :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			assignRoleToGroupService.deleteMultipleRecords(recordIdArray);
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("All selected records have been deleted.");
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
		log.info("AssignRoleToGroupController :==> deleteSelectedRecords :==> Ended");
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
	
	@RequestMapping(value = "group/list", method = RequestMethod.GET)
	@ResponseBody
	public String groupList(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("AppGroupController :==> groupList :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			List<NameValueM> roleList = appGroupService.getAppGroupList(id);
			jsonResponse.setComboList(roleList);
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
		log.info("AppGroupController :==> groupList :==> Ended");
		return new Gson().toJson(jsonResponse);
	}
	
	@RequestMapping(value = "role/list", method = RequestMethod.GET)
	@ResponseBody
	public String roleList(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("AppRoleController :==> roleList :==> Started");

		JsonRes jsonResponse = new JsonRes();
		try {
			List<NameValueM> roleList = appRoleService.getAppRoleList(id);
			jsonResponse.setComboList(roleList);
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
		log.info("AppRoleController :==> roleList :==> Ended");
		return new Gson().toJson(jsonResponse);
	}
	
}// End of GroupRoleController
