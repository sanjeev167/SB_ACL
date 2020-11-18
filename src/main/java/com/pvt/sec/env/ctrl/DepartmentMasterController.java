/**
 * 
 */
package com.pvt.sec.env.ctrl;

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
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.base.ctrl.BaseController;
import com.pvt.sec.env.dto.DepartmentMasterDTO;
import com.pvt.sec.env.service.DepartmentService;
import com.share.NameValueM;
import com.share.JsonResp.JsonRes;
import com.share.grid_pagination.DataTableResults;


/**
 * @author Sanjeev
 *
 */

@Controller
@RequestMapping(value = "/sec/env/department/")
public class DepartmentMasterController  extends BaseController{
	
	static final Logger log = LoggerFactory.getLogger(DepartmentMasterController.class);
		
	@Autowired
	DepartmentService departmentService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listDepartment(Model model) {		
		log.info("DepartmentMasterController :==> listDepartment :==> Started");		
		String target = "/env/dept_master";		
		log.info("DepartmentMasterController :==> listDepartment :==> Ended");
		return target;
	}

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listAppContextPaginated(HttpServletRequest request, HttpServletResponse response,	String departmentName) {
		
		log.info("DepartmentMasterController :==> listAppContextPaginated :==> Started");
		
		DataTableResults<DepartmentMasterDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";
			if (!departmentName.isEmpty() && !departmentName.equals(""))
				whereClauseForBaseQuery = "where name LIKE '%" + departmentName + "%'";
			dataTableResult = departmentService.loadGrid(request, whereClauseForBaseQuery);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		
		log.info("DepartmentMasterController :==> listAppContextPaginated :==> Started");
		
		return new Gson().toJson(dataTableResult);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		log.info("DepartmentMasterController :==> getRecord :==> Started");
		
		JsonRes jsonResponse = new JsonRes();
		try {
			jsonResponse.setFormObject(departmentService.getReordById(id));
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("Record found.");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		
		log.info("DepartmentMasterController :==> getRecord :==> Ended");
		
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid DepartmentMasterDTO departmentMasterDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {
		
		log.info("DepartmentMasterController :==> saveAndUpdateRecord :==> Started");
		
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
				jsonResponse.setFormObject(departmentService.saveAndUpdate(departmentMasterDTO));
				jsonResponse.setStatus(true);
				jsonResponse.setStatusMsg("Record has been saved or updated successfully.");
			}
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
		
		log.info("DepartmentMasterController :==> saveAndUpdateRecord :==> Ended");
		
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecordById(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		log.info("DepartmentMasterController :==> deleteRecordById :==> Started");
		
		JsonRes jsonResponse = new JsonRes();
		try {
			jsonResponse.setFormObject(departmentService.deleteOneRecord(id));
			jsonResponse.setStatusMsg("Record has been deleted successfully.");
			jsonResponse.setStatus(true);
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
		
		log.info("DepartmentMasterController :==> deleteRecordById :==> Ended");
		
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelectedRecords(@RequestBody Integer[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) {
		
		log.info("DepartmentMasterController :==> deleteSelectedRecords :==> Started");
		
		JsonRes jsonResponse = new JsonRes();
		try {
			departmentService.deleteMultipleRecords(recordIdArray);
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("All selected records have been deleted.");
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
		
		log.info("DepartmentMasterController :==> deleteSelectedRecords :==> Ended");
		
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
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

}// End of DepartmentMasterController
