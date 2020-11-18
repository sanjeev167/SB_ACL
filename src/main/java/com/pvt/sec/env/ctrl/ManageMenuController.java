/**
 * 
 */
package com.pvt.sec.env.ctrl;

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
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.env.dto.MenuDTO;
import com.pvt.sec.env.dto.MenuManagerDTO;
import com.pvt.sec.env.service.DepartmentService;
import com.pvt.sec.env.service.MenuManagerService;
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
@RequestMapping(value = "/sec/env/manage_menu/")
public class ManageMenuController {

	static final Logger log = LoggerFactory.getLogger(ManageMenuController.class);
	
	@Autowired
	PageService pageService;
    
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	ModuleService moduleService;
	@Autowired
	MenuManagerService menuManagerService;

	@RequestMapping(value = {"","open"}, method = RequestMethod.GET)
	public String listTreeMenu(Model model) {
		log.info("ManageMenuController :==> listTreeMenu :==> Started");
		String target = "/env/manage_menu";
		log.info("ManageMenuController :==> listTreeMenu :==> End");
		return target;
	}
	
	

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listMenuManagerPaginated(HttpServletRequest request, HttpServletResponse response, 
			String departmentMasterId,
			String treeMenuTypeId){
		log.info("ManageMenuController :==> listMenuManagerPaginated :==> Started");
		DataTableResults<MenuManagerDTO> dataTableResults=null;
		try {
			String whereClauseForBaseQuery = "";

			if (!departmentMasterId.isEmpty() && !departmentMasterId.equals("")&&treeMenuTypeId.isEmpty() && treeMenuTypeId.equals(""))
				whereClauseForBaseQuery =  "  parent.department_id=" + Integer.parseInt(departmentMasterId);

			if (!treeMenuTypeId.isEmpty() && !treeMenuTypeId.equals("")&& departmentMasterId.isEmpty() && departmentMasterId.equals(""))
				whereClauseForBaseQuery =  "  parent.tree_menu_type_id=" + Integer.parseInt(treeMenuTypeId);

			if (!treeMenuTypeId.isEmpty() && !treeMenuTypeId.equals("")&& !departmentMasterId.isEmpty() && !departmentMasterId.equals(""))
				whereClauseForBaseQuery =  "  parent.department_id=" + Integer.parseInt(departmentMasterId)+
						                   " and parent.tree_menu_type_id=" + Integer.parseInt(treeMenuTypeId);


			dataTableResults=menuManagerService.loadGrid(request, whereClauseForBaseQuery);
		}
		catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("ManageMenuController :==> listMenuManagerPaginated :==> End");
		return new Gson().toJson(dataTableResults);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response){
		log.info("ManageMenuController :==> getRecord :==> Started");
		JsonRes jsonResponse = new JsonRes();
		
		try {
			jsonResponse.setFormObject(menuManagerService.getReordById(id));
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
		log.info("ManageMenuController :==> getRecord :==> End");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid MenuManagerDTO menuManagerDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {
		
		log.info("ManageMenuController :==> saveAndUpdateRecord :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			if (result.hasErrors()) {
				// Get error message
				Map<String, String> errors = result.getFieldErrors().stream()
						.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
				jsonResponse.setStatus(false);
				jsonResponse.setStatusMsg("Error found");
				jsonResponse.setFieldErrMsgMap(errors);
				//log.info("errors = "+errors);
			} else {
				// Implement business logic to save record into database
				jsonResponse.setFormObject(menuManagerService.saveAndUpdate(menuManagerDTO));
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
		log.info("ManageMenuController :==> saveAndUpdateRecord :==> End");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("ManageMenuController :==> deleteRecord :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {
			jsonResponse.setStatus(menuManagerService.deleteOneRecord(id));
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
		log.info("ManageMenuController :==> deleteRecord :==> End");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelected(@RequestBody Integer[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("ManageMenuController :==> deleteSelected :==> Started");
		JsonRes jsonResponse = new JsonRes();
		try {			
			jsonResponse.setStatus(menuManagerService.deleteMultipleRecords(recordIdArray));
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
		log.info("ManageMenuController :==> deleteSelected :==> End");
		return new Gson().toJson(jsonResponse);

	}
	

	@RequestMapping(value = "listTreeMenuType", method = RequestMethod.GET)
	@ResponseBody
	public String listTreeMenuType(HttpServletRequest request, HttpServletResponse response) {

		log.info("ManageMenuController :==> listTreeMenuType :==> Started");
		
		JsonRes jsonResponse = new JsonRes();
		try {
			List<NameValueM> moduleList = menuManagerService.getListTreeMenuType();
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

		log.info("ManageMenuController :==> listTreeMenuType :==> Ended");
		
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "listTreeParentNode", method = RequestMethod.GET)
	@ResponseBody
	public String listTreeParentNode(HttpServletRequest request, HttpServletResponse response) {

		log.info("ManageMenuController :==> listTreeParentNode :==> Started");
		
		JsonRes jsonResponse = new JsonRes();
		try {
			List<NameValueM> moduleList = menuManagerService.getListTreeParentNode();
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

		log.info("ManageMenuController :==> listTreeParentNode :==> Ended");
		
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "getSpecificTreeTypeStructure", method = RequestMethod.GET)
	@ResponseBody
	public String getSpecificTreeTypeStructure(@RequestParam Integer id,HttpServletRequest request, HttpServletResponse response) {

		log.info("ManageMenuController :==> listTreeParentNode :==> Started");
		
		JsonRes jsonResponse = new JsonRes();
		try {
			ArrayList<MenuDTO> treeMenuList= menuManagerService.getSpecificTreeTypeStructure(id);
			jsonResponse.setFormObject(treeMenuList);
			jsonResponse.setRecordId(menuManagerService.getMinId()+"");
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

		log.info("ManageMenuController :==> listTreeParentNode :==> Ended");		
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


	@RequestMapping(value = "page/list", method = RequestMethod.GET)
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

	@RequestMapping(value = "page/listBaseUrl", method = RequestMethod.GET)
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

	
}//End of ManageMenuController
