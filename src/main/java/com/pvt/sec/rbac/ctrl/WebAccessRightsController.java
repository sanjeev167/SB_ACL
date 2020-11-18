
package com.pvt.sec.rbac.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.env.dto.PageMasterDTO;
import com.pvt.sec.env.service.PageService;
import com.pvt.sec.rbac.service.WebAccessRightsService;
import com.share.JsonResponse;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */

@Controller
@RequestMapping(value = "/pvt/sec/webAccessRiights/")
public class WebAccessRightsController {

	static final Logger log = LoggerFactory.getLogger(WebAccessRightsController.class);

	@Autowired
	PageService pageService;

	@Autowired
	WebAccessRightsService webAccessRightsService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listRbacAccessRights(Model model) {
		log.info("WebAccessRightsController :==> listRbacAccessRights :==> Started");
		String target = "/sec/web_access_rights";
		log.info("WebAccessRightsController :==> listRbacAccessRights :==> Ended");
		return target;
	}

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listPagePaginated(HttpServletRequest request, HttpServletResponse response, String departmentNameId,
			String moduleNameId, String pageNameId, String roleNameId, String pageViewCondition) {

		log.info("WebAccessRightsController :==> listPagePaginated :==> Started");
		DataTableResults<PageMasterDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";
			if (pageViewCondition.equals("B")) {
				if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && moduleNameId.isEmpty()
						&& moduleNameId.equals("") && pageNameId.isEmpty() && pageNameId.equals(""))
					whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId);

				if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && !moduleNameId.isEmpty()
						&& !moduleNameId.equals("") && pageNameId.isEmpty() && pageNameId.equals(""))
					whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId) + " and pm.module_id="
							+ Integer.parseInt(moduleNameId);
				if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && !moduleNameId.isEmpty()
						&& !moduleNameId.equals("") && !pageNameId.isEmpty() && !pageNameId.equals(""))
					whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId) + " and pm.module_id="
							+ Integer.parseInt(moduleNameId) + " and pm.id=" + Integer.parseInt(pageNameId);

				dataTableResult = pageService.loadGridForRbacCMP(request, whereClauseForBaseQuery);
			} else {
				dataTableResult = pageService.loadGridFor_RoleAssignedAndUnassigned(request, moduleNameId, roleNameId,
						pageViewCondition);
			}
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("WebAccessRightsController :==> listPagePaginated :==> Ended");

		return new Gson().toJson(dataTableResult);
	}

	@RequestMapping(value = "loadContextBasedRolesWithAlreadyAssignedPages", method = RequestMethod.GET)
	@ResponseBody
	public String loadContextBasedRolesWithAlreadyAssignedPages(@RequestParam Integer pageId, HttpServletRequest request,
			HttpServletResponse response) {

		log.info("WebAccessRightsController :==> loadContextBasedRolesWithAlreadyAssignedPages :==> Started");
		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(webAccessRightsService.loadContextBasedRolesWithAlreadyAssignedPages(pageId));
			jsonResponse.setStatus(true);
			// jsonResponse.setStatusMsg("Record found.");
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

		log.info("WebAccessRightsController :==> loadContextBasedRolesWithAlreadyAssignedPages :==> Started");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "getAllThePagesAssignedToRole", method = RequestMethod.GET)
	@ResponseBody
	public String getAllThePagesAssignedToRole(@RequestParam Integer roleId, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("WebAccessRightsController :==> getAllThePagesAssignedToRole :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(webAccessRightsService.loadRoleWithPageAccessCountAfterAssignmentAndRemoval(roleId));
			jsonResponse.setStatus(true);
			// jsonResponse.setStatusMsg("Record found.");
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
		log.info("WebAccessRightsController :==> getAllThePagesAssignedToRole :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "loadRoleWithPageAccessCountAfterAssignmentAndRemoval", method = RequestMethod.GET)
	@ResponseBody
	public String loadRoleWithPageAccessCountAfterAssignmentAndRemoval(@RequestParam Integer appContextId, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("WebAccessRightsController :==> loadRoleWithPageAccessCountAfterAssignmentAndRemoval :==> Started");
        
		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(webAccessRightsService.loadRoleWithPageAccessCountAfterAssignmentAndRemoval(appContextId));

			// loadRolesWithPageAccessRights(appContextId));
			jsonResponse.setStatus(true);
			// jsonResponse.setStatusMsg("Record found.");
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
		log.info("WebAccessRightsController :==> loadRoleWithPageAccessCountAfterAssignmentAndRemoval :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "savePageAssignment", method = RequestMethod.POST)
	@ResponseBody
	public String savePageAssignment(@RequestParam String pageId, @RequestParam String[] roleIdArray,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("WebAccessRightsController :==> savePageAssignment :==> Started");
		log.info("Sanjeev   = " + roleIdArray.toString());
		JsonResponse jsonResponse = new JsonResponse();
		try {
			// Implement business logic to save record into database
			jsonResponse
					.setFormObject(webAccessRightsService.savePageAssignment(Integer.parseInt(pageId), roleIdArray));
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("Updated successfully.");
			// AppConstants.securityUpdate=true;//It is required to set here for updating
			// the access rights
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

		log.info("WebAccessRightsController :==> savePageAssignment :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "removePageAssignment", method = RequestMethod.POST)
	@ResponseBody
	public String removePageAssignment(@RequestParam Integer roleId, @RequestParam Integer[] pageIdArray,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("WebAccessRightsController :==> removePageAssignment :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			// Implement business logic to save record into database
			jsonResponse.setFormObject(webAccessRightsService.removePageAssignment(roleId, pageIdArray));
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("Removed successfully.");

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

		log.info("WebAccessRightsController :==> removePageAssignment :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

}// End of StateMasterController
