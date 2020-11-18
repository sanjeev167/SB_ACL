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

import com.google.gson.Gson;



import com.share.NameValueM;
import com.share.JsonResp.JsonRes;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.rbac.dto.WebUserDTO;
import com.pvt.sec.rbac.service.WebUserAccountService;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping(value = "/sec/webUserStats/")
public class WebUserStatsController  {
	static final Logger log = LoggerFactory.getLogger(WebUserStatsController.class);

	@Autowired
	WebUserAccountService webUserAccountService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listUserAccounts(Model model) {
		log.info("WebUserStatsController :==> listUserAccounts :==> Started");
		String target = "/sec/webUserStats";
		log.info("WebUserStatsController :==> listUserAccounts :==> Ended");
		return target;
	}

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listUserAccountPaginated(HttpServletRequest request, HttpServletResponse response,
			String departmentId, String categoryNameId) {
		log.info("WebUserStatsController :==> listUserAccountPaginated :==> Started");
		DataTableResults<WebUserDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";

			/*
			 * if (!departmentId.isEmpty()&&!departmentId.equals(""))
			 * whereClauseForBaseQuery ="  dm.id=" + Integer.parseInt(departmentId); if
			 * (!categoryNameId.isEmpty()) whereClauseForBaseQuery ="  uc.id=" +
			 * Integer.parseInt(categoryNameId);
			 * 
			 * if (!departmentId.isEmpty() && !categoryNameId.isEmpty())
			 * whereClauseForBaseQuery = "  dm.id=" + Integer.parseInt(departmentId)+
			 * " and uc.id="+Integer.parseInt(categoryNameId);
			 */
			dataTableResult = webUserAccountService.loadGrid(request, whereClauseForBaseQuery);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("WebUserStatsController :==> listUserAccountPaginated :==> Ended");
		return new Gson().toJson(dataTableResult);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) {
		log.info("WebUserStatsController :==> getRecord :==> Started");

		JsonRes jsonResponse = new JsonRes();
		try {
			jsonResponse.setFormObject(webUserAccountService.getReordById(id));
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
		log.info("WebUserStatsController :==> getRecord :==> Started");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid WebUserDTO webUserDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		log.info("WebUserStatsController :==> saveAndUpdateRecord :==> Started");
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
				jsonResponse.setFormObject(webUserAccountService.saveAndUpdate(webUserDTO));
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

		log.info("WebUserStatsController :==> saveAndUpdateRecord :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecordById(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) {
		log.info("WebUserStatsController :==> deleteRecordById :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			jsonResponse.setFormObject(webUserAccountService.deleteRecordById(id));
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

		log.info("WebUserStatsController :==> deleteRecordById :==> Ended");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelectedRecords(@RequestBody Long[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("WebUserStatsController :==> deleteSelectedRecords :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			webUserAccountService.deleteMultipleRecords(recordIdArray);
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
		log.info("WebUserStatsController :==> deleteSelectedRecords :==> Ended");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public String userAccountList(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) {
		log.info("WebUserStatsController :==> userAccountList :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			List<NameValueM> appAdminUserList = webUserAccountService.getWebUserList(id);
			jsonResponse.setComboList(appAdminUserList);
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
		log.info("WebUserStatsController :==> userAccountList :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

}// End of WebUserStatsController
