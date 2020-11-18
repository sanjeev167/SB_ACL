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

import com.share.JsonResp.JsonRes;
import com.share.NameValueM;
import com.pvt.sec.env.service.DepartmentService;
import com.pvt.sec.rbac.dto.UserCategoryDTO;
import com.pvt.sec.rbac.service.UserCategoryService;

import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.google.gson.Gson;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */

@Controller
@RequestMapping(value = "/sec/userCategory/")
public class UserCategoryController  {

	static final Logger log = LoggerFactory.getLogger(UserCategoryController.class);
	@Autowired
	UserCategoryService userCategoryService;

	@Autowired
	DepartmentService departmentService;
	

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listUserCategory(Model model) {
		log.info("UserCategoryController :==> listUserCategory :==> Started");
		String target = "/sec/userCategory";
		log.info("UserCategoryController :==> listUserCategory :==> Ended");

		return target;
	}// End of open

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listUserCategoryPaginated(HttpServletRequest request, HttpServletResponse response,
			String categoryName, String departmentNameId) {

		log.info("UserCategoryController :==> listUserCategoryPaginated :==> Started");
		DataTableResults<UserCategoryDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";

			if (!departmentNameId.isEmpty() && !departmentNameId.equals(""))
				whereClauseForBaseQuery = "  uc.department_id=" + Integer.parseInt(departmentNameId);
			if (!categoryName.isEmpty())
				whereClauseForBaseQuery = "  uc.name Like %" + categoryName + "%";
			if (!departmentNameId.isEmpty() && !categoryName.isEmpty())
				whereClauseForBaseQuery = "  uc.department_id=" + Integer.parseInt(departmentNameId)
						+ " and uc.name Like '%" + categoryName + "%'";
			dataTableResult = userCategoryService.loadGrid(request, whereClauseForBaseQuery);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("UserCategoryController :==> listUserCategoryPaginated :==> Ended");

		return new Gson().toJson(dataTableResult);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {
		log.info("UserCategoryController :==> getRecord :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			jsonResponse.setFormObject(userCategoryService.getReordById(id));
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

		log.info("UserCategoryController :==> getRecord :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid UserCategoryDTO userCategoryDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		log.info("UserCategoryController :==> saveAndUpdateRecord :==> Started");
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
				jsonResponse.setFormObject(userCategoryService.saveAndUpdate(userCategoryDTO));
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
		log.info("UserCategoryController :==> saveAndUpdateRecord :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecordById(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {
		log.info("UserCategoryController :==> deleteRecordById :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			jsonResponse.setFormObject(userCategoryService.deleteRecordById(id));
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
		log.info("UserCategoryController :==> deleteRecordById :==> Ended");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelectedRecords(@RequestBody Integer[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("UserCategoryController :==> deleteSelectedRecords :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			userCategoryService.deleteMultipleRecords(recordIdArray);
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

		log.info("UserCategoryController :==> deleteSelectedRecords :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public String userCategoryList(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {
		log.info("UserCategoryController :==> userCategoryList :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			List<NameValueM> userCategoryList = userCategoryService.getUserCategoryList(id);
			jsonResponse.setComboList(userCategoryList);
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
		log.info("UserCategoryController :==> userCategoryList :==> Ended");
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
	

}// End of RoleMasterController
