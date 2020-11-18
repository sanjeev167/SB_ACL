/**
 * 
 */
package com.pvt.sec.acl.adm.ctrl;

import java.util.ArrayList;
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
import com.pvt.sec.acl.adm.dto.OwnerPermissionDTO;
import com.pvt.sec.acl.adm.dto.PermissionGridDto;
import com.pvt.sec.acl.adm.dto.RolePermissionDTO;
import com.pvt.sec.acl.adm.service.AclPolicyDefinedOnDomainService;
import com.pvt.sec.acl.adm.service.DomainClassService;
import com.pvt.sec.acl.adm.service.PermissionOnDomainService;
import com.share.JsonResponse;
import com.share.NameValue;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping("/domain_acl")

public class DomainAclPolicyController {

	@Autowired
	DomainClassService domainClassService;

	@Autowired
	PermissionOnDomainService permissionOnDomainService;

	@Autowired
	AclPolicyDefinedOnDomainService aclPolicyDefinedOnDomainService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String update_policy() {
		return "acl/domain_acl_policy";
	}
	
	@RequestMapping(value = "/domainList", method = RequestMethod.GET)
	@ResponseBody
	public String domainList(HttpServletRequest request, HttpServletResponse response) {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> domainList = domainClassService.getDomainList();
		jsonResponse.setComboList(domainList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/loadDomainAndContextBasedAvailablePermission_roleSection", method = RequestMethod.GET)
	@ResponseBody
    public String loadDomainAndContextBasedAvailablePermission_roleSection(@RequestParam Long domainId,
    		@RequestParam String contextId,
    		HttpServletRequest request, HttpServletResponse response) {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionListOnDomain = aclPolicyDefinedOnDomainService.loadDomainAndContextBasedAvailablePermission_roleSection(domainId,contextId);		
		jsonResponse.setComboList(permissionListOnDomain);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	
	
	@RequestMapping(value = "/loadPermissionWith_Domain_Role_PermissionContext_roleSection", method = RequestMethod.GET)
	@ResponseBody
	public String loadPermissionWith_Domain_Role_PermissionContext_roleSection(@RequestParam Long domainId,
			@RequestParam Integer roleId, @RequestParam Integer contextId, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionListOnDomain = aclPolicyDefinedOnDomainService
				.loadPermissionWith_Domain_Role_PermissionContext_roleSection(domainId, roleId, contextId);
		jsonResponse.setComboList(permissionListOnDomain);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/permissionPolicyDefinedForAllDomain", method = RequestMethod.GET)
	@ResponseBody
	public String permissionPolicyDefinedForAllDomain(HttpServletRequest request, HttpServletResponse response) {

		JsonResponse jsonResponse = new JsonResponse();
		List<PermissionGridDto> permissionGridDtoList = aclPolicyDefinedOnDomainService
				.getAclPolicyDefinedOnAllDomain();

		jsonResponse.setPermissionGridDtoList(permissionGridDtoList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		//System.out.println("Sanjeev = "+new Gson().toJson(permissionGridDtoList));
		return new Gson().toJson(permissionGridDtoList);
	}

	@RequestMapping(value = "/loadPermissionWithSelectedGridRow", method = RequestMethod.GET)
	@ResponseBody
	public String loadPermissionWithSelectedGridRow(
			@RequestParam String domainPolicyId,@RequestParam String domainClassName,
			HttpServletRequest request, HttpServletResponse response) {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionGridDtoList = aclPolicyDefinedOnDomainService
				.loadPermissionWithSelectedDomainPolicyId(domainPolicyId,domainClassName);

		jsonResponse.setComboList(permissionGridDtoList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/loadDomainComboWithSelectedGridRow", method = RequestMethod.GET)
	@ResponseBody
	public String loadDomainComboWithSelectedGridRow(
			@RequestParam String domainClassName,

			HttpServletRequest request, HttpServletResponse response) {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> domainList = aclPolicyDefinedOnDomainService
				.loadDomainComboWithSelectedDomainOfGrid(domainClassName);

		jsonResponse.setComboList(domainList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/loadRoleComboWithSelectedRoleOfGrid", method = RequestMethod.GET)
	@ResponseBody
	public String loadRoleComboWithSelectedRoleOfGrid(
			@RequestParam  String ownerOrRole, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> roleList = aclPolicyDefinedOnDomainService.loadRoleComboWithSelectedRoleOfGrid(ownerOrRole);

		jsonResponse.setComboList(roleList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	
	@RequestMapping(value = "/saveOwnerPermissionPolicy", method = RequestMethod.POST)
	@ResponseBody
	public String saveOwnerPermissionPolicy(@RequestBody @Valid OwnerPermissionDTO ownerPermissionDTO,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResponse = new JsonResponse();

		if (result.hasErrors()) {
			// Get error message
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg("");
			jsonResponse.setFieldErrMsgMap(errors);
			
		} else {
			boolean isSaved = aclPolicyDefinedOnDomainService.saveOwnerPermissionPolicy(ownerPermissionDTO);

			jsonResponse.setStatus(isSaved);
			if (isSaved)
				jsonResponse.setStatusMsg("Owner-policies saved successfully.");
			else
				jsonResponse.setStatusMsg("Owner-policies not saved.");
		}
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/updateOwnerPermissionPolicy", method = RequestMethod.POST)
	@ResponseBody
	public String updateOwnerPermissionPolicy(@RequestBody @Valid OwnerPermissionDTO ownerPermissionDTO,
            BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResponse = new JsonResponse();
		
		if (result.hasErrors()) {
			// Get error message
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));			
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg("");
			jsonResponse.setFieldErrMsgMap(errors);			
		}else {
			boolean isSaved = aclPolicyDefinedOnDomainService.updateOwnerPermissionPolicy(ownerPermissionDTO);

			jsonResponse.setStatus(isSaved);
			if (isSaved)
				jsonResponse.setStatusMsg("Exisiting Owner-policies updated successfully.");
			else
				jsonResponse.setStatusMsg("Exisiting Owner-policies not updated.");
		}	
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/saveRolePermissionPolicy", method = RequestMethod.POST)
	@ResponseBody
	public String saveRolePermissionPolicy(@RequestBody @Valid RolePermissionDTO rolePermissionDTO,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResponse = new JsonResponse();
		
		if (result.hasErrors()) {
			// Get error message
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));			
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg("");
			jsonResponse.setFieldErrMsgMap(errors);			
		}else {
			boolean isSaved = aclPolicyDefinedOnDomainService.saveRolePermissionPolicy(rolePermissionDTO);			
			jsonResponse.setStatus(isSaved);
			if (isSaved)
				jsonResponse.setStatusMsg("Role-policies saved successfully.");
			else
				jsonResponse.setStatusMsg("Role-policies not saved.");			
		}		
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/updateRolePermissionPolicy", method = RequestMethod.POST)
	@ResponseBody
	public String updateRolePermissionPolicy(@RequestBody @Valid RolePermissionDTO rolePermissionDTO,
			BindingResult result,
			HttpServletRequest request,
			HttpServletResponse response) {

		JsonResponse jsonResponse = new JsonResponse();
		if (result.hasErrors()) {
			// Get error message
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));			
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg("");
			jsonResponse.setFieldErrMsgMap(errors);			
		}else {
			boolean isSaved = aclPolicyDefinedOnDomainService.updateRolePermissionPolicy(rolePermissionDTO);
			jsonResponse.setStatus(isSaved);
			if (isSaved)
				jsonResponse.setStatusMsg("Existing Role-policies updated successfully.");
			else
				jsonResponse.setStatusMsg("Existing Role-policies not updated.");
		}
		
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/deletePermissionPolicy", method = RequestMethod.GET)
	@ResponseBody
	public String deletePermissionPolicy(
			@RequestParam String domainPolicyId, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResponse jsonResponse = new JsonResponse();
		boolean isSaved = aclPolicyDefinedOnDomainService.deletePermissionPolicy(domainPolicyId);
		// jsonResponse.setComboList(roleList);
		jsonResponse.setStatus(isSaved);
		if (isSaved)
			jsonResponse.setStatusMsg("Selected-Policy deleted successfully.");
		else
			jsonResponse.setStatusMsg("Selected-Policy not deleted.");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/loadOwnerPermissionBasedOnDomainAndContextId", method = RequestMethod.GET)
	@ResponseBody
	public String loadOwnerPermissionBasedOnDomainAndContextId(
			@RequestParam String domainId,
			@RequestParam boolean withDomainFlag,
			@RequestParam String contextId,			               
			HttpServletRequest request, HttpServletResponse response) {
		    JsonResponse jsonResponse = new JsonResponse();		
			List<NameValue> permissionCustomContextList;
			if (withDomainFlag) {
				permissionCustomContextList = aclPolicyDefinedOnDomainService
						.loadOwnerPermissionBasedOnDomainAndContextId(domainId, contextId,true);
			} else {
				
				permissionCustomContextList = aclPolicyDefinedOnDomainService
						.loadAvailableDomainPermission(domainId,contextId);
			}
			jsonResponse.setComboList(permissionCustomContextList);
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("");
		
		return new Gson().toJson(jsonResponse);
	}	
	
	@RequestMapping(value = { "/loadContextBasedNoPrePermission_roleSection",
			"/loadContextBasedNoPrePermission_ownerSection" }, method = RequestMethod.GET)
	@ResponseBody
	public String loadContextBasedNoPrePermission_roleSection(
			@RequestParam String domainId,			
			@RequestParam String contextId,
			HttpServletRequest request, HttpServletResponse response) {		
		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionCustomContextList;
		permissionCustomContextList = aclPolicyDefinedOnDomainService
				.loadAvailableDomainPermission(domainId,contextId);

		jsonResponse.setComboList(permissionCustomContextList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}
	
	@RequestMapping(value = "/zeroPermission", method = RequestMethod.GET)
	@ResponseBody
	public String zeroPermission(HttpServletRequest request,HttpServletResponse response) {
		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionCustomContextList=new ArrayList<NameValue>();		
		jsonResponse.setComboList(permissionCustomContextList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("No record found.");
		return new Gson().toJson(jsonResponse);
	}
	
}
