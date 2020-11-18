/**
 * 
 */
package com.pvt.sec.acl.adm.ctrl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pvt.sec.acl.adm.dto.DomainNameDTO;
import com.pvt.sec.acl.adm.dto.DomainPermissionDTO;
import com.pvt.sec.acl.adm.dto.PermissionDefinitionDTO;
import com.pvt.sec.acl.adm.dto.PermissionGridDto;
import com.pvt.sec.acl.adm.service.DomainClassService;
import com.pvt.sec.acl.adm.service.PermissionContextService;
import com.pvt.sec.acl.adm.service.PermissionOnDomainService;
import com.pvt.sec.acl.adm.service.PermissionTypeService;
import com.share.JsonResponse;
import com.share.NameValue;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping("/domain_permission")

public class DomainAndPermissionManagerController {

	
	@Autowired
	DomainClassService domainClassService;
	@Autowired
	PermissionContextService permissionContextService;

	@Autowired
	PermissionTypeService permissionTypeService;

	@Autowired
	PermissionOnDomainService permissionOnDomainService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String update_policy() {
		return "acl/domain_and_permission";
	}

	/**
	 * This will load all roles available in the database.
	 **/
	@RequestMapping(value = "/loadPermissionContextList", method = RequestMethod.GET)
	@ResponseBody
	public String loadPermissionContextList(HttpServletRequest request, HttpServletResponse response) {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionContextList = permissionContextService.loadPermissionContextList();
		jsonResponse.setComboList(permissionContextList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will load permission context combo
	 **/
	@RequestMapping(value = "/loadPermissionCustomContextCombo", method = RequestMethod.GET)
	@ResponseBody
	public String loadPermissionCustomContextCombo(HttpServletRequest request, HttpServletResponse response) {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionCustomContextList = permissionContextService.loadPermissionCustomContextList();
		jsonResponse.setComboList(permissionCustomContextList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will load .
	 **/
	@RequestMapping(value = "/loadBaseAndCustomPermissionTogatherWithPreSelection", method = RequestMethod.GET)
	@ResponseBody
	public String loadBaseAndCustomPermissionTogatherWithPreSelection(
			@RequestParam String domainId,
			boolean withDomainFlag, 
			@RequestParam String contextId,
			HttpServletRequest request, HttpServletResponse response) {
		
		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionCustomContextList;		
		if (withDomainFlag) {
			permissionCustomContextList = permissionTypeService
					.loadBaseAndCustomPermissionTogatherWithPreSelection(domainId);
			
		} else {
			permissionCustomContextList = permissionTypeService.loadBaseAndCustomPermissionTogather();
		}
		jsonResponse.setComboList(permissionCustomContextList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will load .
	 **/
	@RequestMapping(value = "/loadBaseAndCustomPermissionTogather", method = RequestMethod.GET)
	@ResponseBody
	public String loadBaseAndCustomPermissionTogather(HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionCustomContextList = permissionTypeService.loadBaseAndCustomPermissionTogather();
		jsonResponse.setComboList(permissionCustomContextList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will load .
	 **/
	@RequestMapping(value = "/permissionLoadingInDomainContainerOnDomainChange", method = RequestMethod.GET)
	@ResponseBody
	public String permissionLoadingInDomainContainerOnDomainChange(
			@RequestParam  Integer domainId,
			HttpServletRequest request, HttpServletResponse response) {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionCustomContextList;
		if (domainId != 0)
			permissionCustomContextList = permissionOnDomainService
					.getPermissionLoadingInDomainContainerOnDomainChange(domainId);
		else
			permissionCustomContextList = permissionTypeService.loadBaseAndCustomPermissionTogather();

		jsonResponse.setComboList(permissionCustomContextList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will load the grid .
	 **/
	@RequestMapping(value = "/permissionDefinedForAllDomain", method = RequestMethod.GET)
	@ResponseBody
	public String permissionDefinedForAllDomain(HttpServletRequest request, HttpServletResponse response) {
		
		JsonResponse jsonResponse = new JsonResponse();
		List<PermissionGridDto> permissionDefinedOfAllDomainList = permissionOnDomainService
				.getPermissionDefinedOfAllDomain();
		jsonResponse.setPermissionGridDtoList(permissionDefinedOfAllDomainList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(permissionDefinedOfAllDomainList);
	}
	
	/**
	 * Save new domain
	 * **/
	@RequestMapping(value = "/saveDomainDefinition", method = RequestMethod.POST)
	@ResponseBody 
	public String saveDomainDefinition (@RequestBody @Valid DomainNameDTO  domainNameDTO,BindingResult result ,HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResponse = new JsonResponse();
		if (result.hasErrors()) {
			// Get error message
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));			
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg("");
			jsonResponse.setFieldErrMsgMap(errors);
			
			
		}else {
			boolean isSavedOrUpdated = domainClassService.saveDomainDefinition(domainNameDTO);
			if(!isSavedOrUpdated) {				
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("Record Saved successfully.");
			}else {
				jsonResponse.setStatus(true);
				jsonResponse.setStatusMsg("Record already available.");
			}
		}	
		return new Gson().toJson(jsonResponse);		
	}
	
	 /** Update new domain
	 * **/
	@RequestMapping(value = "/updateExistingDomainDefinition", method = RequestMethod.POST)
	@ResponseBody 
	public String updateExistingDomainDefinition (@RequestBody @Valid DomainNameDTO  domainNameDTO,BindingResult result ,HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResponse = new JsonResponse();
		if (result.hasErrors()) {
			// Get error message
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));			
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg("");
			jsonResponse.setFieldErrMsgMap(errors);			
			
		}else {
			boolean isUpdated = domainClassService.updateExistingDomainDefinition(domainNameDTO);							
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("Record Updated successfully.");			
		}	
		return new Gson().toJson(jsonResponse);		
	}
		
	@RequestMapping(value = "/addEditDomainPermission", method = RequestMethod.POST)
	@ResponseBody
	public String addEditDomainPermission(@RequestBody @Valid DomainPermissionDTO domainPermissionDTO,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) {

		{
			JsonResponse jsonResponse = new JsonResponse();
			if (result.hasErrors()) {
				// Get error message
				Map<String, String> errors = result.getFieldErrors().stream()
						.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
				jsonResponse.setStatus(false);
				jsonResponse.setStatusMsg("");
				jsonResponse.setFieldErrMsgMap(errors);
			} else {
				boolean isSavedOrUpdate = permissionOnDomainService.saveOrUpdateDomainPermissionAssociation(domainPermissionDTO);				
				jsonResponse.setStatus(true);
				jsonResponse.setStatusMsg("Record updated successfully.");
			}
			return new Gson().toJson(jsonResponse);
		}

	}	
	
	@RequestMapping(value = "/deleteDomainPermissionAssignment", method = RequestMethod.GET)
	@ResponseBody
	public String deleteDomainPermissionAssignment(@RequestParam String domainId,
			HttpServletRequest request, HttpServletResponse response) {

		JsonResponse jsonResponse = new JsonResponse();
		boolean isSavedOrUpdate = permissionOnDomainService.deleteDomainPermissionAssignment(domainId);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("Record deleted successfully.");
		return new Gson().toJson(jsonResponse);
	}
		
	@RequestMapping(value = "/savePermissionDefinition", method = RequestMethod.POST)
	@ResponseBody
	public String savePermissionDefinition(@RequestBody @Valid PermissionDefinitionDTO PermissionDefinitionDTO,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		{
			JsonResponse jsonResponse = new JsonResponse();
			if (result.hasErrors()) {
				// Get error message
				Map<String, String> errors = result.getFieldErrors().stream()
						.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
				jsonResponse.setStatus(false);
				jsonResponse.setStatusMsg("");
				jsonResponse.setFieldErrMsgMap(errors);
			} else {
				String message = permissionOnDomainService
						.savePermissionDefinition(PermissionDefinitionDTO);
				
				jsonResponse.setStatus(true);
				jsonResponse.setStatusMsg(message);
			}
			return new Gson().toJson(jsonResponse);
		}
	}
	
	@RequestMapping(value = "/updatePermissionDefinition", method = RequestMethod.POST)
	@ResponseBody
	public String updatePermissionDefinition(@RequestBody @Valid PermissionDefinitionDTO PermissionDefinitionDTO,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		{
			JsonResponse jsonResponse = new JsonResponse();
			if (result.hasErrors()) {
				// Get error message
				Map<String, String> errors = result.getFieldErrors().stream()
						.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
				jsonResponse.setStatus(false);
				jsonResponse.setStatusMsg("");
				jsonResponse.setFieldErrMsgMap(errors);
			} else {
				String message = permissionOnDomainService
						.updatePermissionDefinition(PermissionDefinitionDTO);
				
				jsonResponse.setStatus(true);
				jsonResponse.setStatusMsg(message);
			}
			return new Gson().toJson(jsonResponse);
		}
	}
		
	@RequestMapping(value = "/deleteSelectedDomain", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelectedDomain(@RequestParam String domainName,
			HttpServletRequest request, HttpServletResponse response) {
		{
			JsonResponse jsonResponse = new JsonResponse();
			domainClassService.deleteSelectedDomain(domainName);
			jsonResponse.setStatus(true);
			String msg="Followings have been deleted successfully:--"
			          +"<br>"
			          + "<ul><li>Selected Domain</li>"
			          + "<li>Associated-Domain-Permissions and</li>"
			          + "<li>All ACLs defined on it.</li>"
			          + "</ul>";
			jsonResponse.setStatusMsg(msg);
			return new Gson().toJson(jsonResponse);
		}
	}
	
	@RequestMapping(value = "/deletePermissionDefinition", method = RequestMethod.POST)
	@ResponseBody
	public String deletePermissionDefinition(@RequestParam String recordId,
			HttpServletRequest request, HttpServletResponse response) {
		{
			JsonResponse jsonResponse = new JsonResponse();
			boolean hasRecordDeled=permissionTypeService.deletePermissionDefinition(recordId);
			jsonResponse.setStatus(true);			
			jsonResponse.setStatusMsg("Selected permission has been deleted successfully");
			return new Gson().toJson(jsonResponse);
		}
	}
}
