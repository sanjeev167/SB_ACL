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
import com.pvt.sec.env.dto.PageMasterDTO;
import com.pvt.sec.env.service.DepartmentService;
import com.pvt.sec.env.service.ModuleService;
import com.pvt.sec.env.service.PageService;
import com.share.NameValueM;
import com.share.JsonResp.JsonRes;
import com.share.grid_pagination.DataTableResults;
/**
 * @author Sanjeev
 *
 */

@Controller
@RequestMapping(value = "/sec/env/page/")
public class PageMasterController extends BaseController{
	
	static final Logger log = LoggerFactory.getLogger(PageMasterController.class);
	
	@Autowired
	PageService pageService;
    
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	ModuleService moduleService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listPages(Model model) {
		
		log.info("PageMasterController :==> listPages :==> Started");
		
		String target = "/env/page_master";
		
		log.info("PageMasterController :==> listPages :==> Ended");
		
		return target;
	}

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listPagePaginated(HttpServletRequest request, HttpServletResponse response, String departmentNameId,
			String moduleNameId) {

		log.info("PageMasterController :==> listPagePaginated :==> Started");
		
		DataTableResults<PageMasterDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && moduleNameId.isEmpty()
					&& moduleNameId.equals(""))
				whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId);

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && !moduleNameId.isEmpty()
					&& !moduleNameId.equals(""))
				whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId) + " and pm.module_id="
						+ Integer.parseInt(moduleNameId);
			dataTableResult = pageService.loadGrid(request, whereClauseForBaseQuery);

		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		
		log.info("PageMasterController :==> listPagePaginated :==> Ended");

		return new Gson().toJson(dataTableResult);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("PageMasterController :==> getRecord :==> Started");

		JsonRes jsonResponse = new JsonRes();
		try {
			jsonResponse.setFormObject(pageService.getReordById(id));
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

		log.info("PageMasterController :==> getRecord :==> Ended");

		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid PageMasterDTO pageMasterDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		log.info("PageMasterController :==> saveAndUpdateRecord :==> Started");

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
				jsonResponse.setFormObject(pageService.saveAndUpdate(pageMasterDTO));
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

		log.info("PageMasterController :==> saveAndUpdateRecord :==> Ended");

		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecordById(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("PageMasterController :==> deleteRecordById :==> Started");

		JsonRes jsonResponse = new JsonRes();
		try {
			jsonResponse.setFormObject(pageService.deleteOneRecord(id));
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

		log.info("PageMasterController :==> deleteRecordById :==> Ended");

		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelectedReords(@RequestBody Integer[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) {

		log.info("PageMasterController :==> deleteSelectedReords :==> Started");

		JsonRes jsonResponse = new JsonRes();
		try {
			pageService.deleteMultipleRecords(recordIdArray);
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

		log.info("PageMasterController :==> deleteSelectedReords :==> Ended");

		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public String pageList(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("PageMasterController :==> pageList :==> Started");
		
		JsonRes jsonResponse = new JsonRes();
		try {
			List<NameValueM> moduleList = pageService.getPageList(id);
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

		log.info("PageMasterController :==> pageList :==> Ended");
		
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "listBaseUrl", method = RequestMethod.GET)
	@ResponseBody
	public String listBaseUrl(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("PageMasterController :==> listBaseUrl :==> Started");
		
		JsonRes jsonResponse = new JsonRes();
		try {
			List<NameValueM> moduleList = pageService.getListBaseUrl(id);
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

		log.info("PageMasterController :==> listBaseUrl :==> Ended");
		
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
	

	@RequestMapping(value = "module/list", method = RequestMethod.GET)
	@ResponseBody public String moduleList(@RequestParam Integer id, HttpServletRequest request,	HttpServletResponse response) {

		log.info("ModuleMasterController :==> moduleList :==> Started");

		JsonRes jsonResponse=new JsonRes();		
		try {			 	
			List<NameValueM> moduleList=moduleService.getModuleList(id);	
			jsonResponse.setComboList(moduleList);			 
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

		log.info("ModuleMasterController :==> moduleList :==> Ended");

		return new Gson().toJson(jsonResponse);
	}

	
}// End of StateMasterController
