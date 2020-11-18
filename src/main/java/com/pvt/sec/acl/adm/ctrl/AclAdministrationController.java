/**
 * 
 */
package com.pvt.sec.acl.adm.ctrl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.config.exception.common.CustomRuntimeException;
import com.google.gson.Gson;
import com.pvt.sec.acl.adm.dto.Aclrights;
import com.pvt.sec.acl.adm.dto.ChangeOwnerDTO;
import com.pvt.sec.acl.adm.dto.SharedRolePermissionsDTO;
import com.pvt.sec.acl.adm.dto.SharedUserPermissionsDTO;
import com.pvt.sec.acl.adm.service.AclAdministrationService;
import com.pvt.sec.acl.adm.service.CustomSecurityService;
import com.pvt.sec.acl.adm.service.PermissionContextService;
import com.pvt.sec.rbac.service.AppRoleService;
import com.pvt.sec.rbac.service.UserAccountService;
import com.share.JsonResponse;
import com.share.NameValue;
import com.share.NameValueM;
import com.share.JsonResp.JsonRes;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping("/acl_admin")
public class AclAdministrationController  {
	protected static Logger logger = LoggerFactory.getLogger(AclAdministrationController.class);

	@Autowired
	PermissionContextService permissionContextService;
	@Autowired
	CustomSecurityService customSecurityService;
	@Autowired
	AppRoleService roleService;
	
	@Autowired
	UserAccountService userAccountService;

	@Autowired
	AclAdministrationService aclAdministrationService;

	String currentObjectOwner = "";// Will be used while stuffing it into model to fetch it on jsp.
	// will be set in support method

	/**
	 * Load object-details for permission administration .
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String loadAllPermissionsDefinedOnThisObject(@ModelAttribute Aclrights aclrights,
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "domain", required = true) String domain, Model model)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		logger.debug("Received request to show permissions defined on this object.");		
		Object post = aclAdministrationService.prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domain, id);

		List<Aclrights> userRoleAccessList = aclAdministrationService.getPermissionListOfOtherUsersForGrid(post);
		model.addAttribute("userRoleAccessList", userRoleAccessList);

		// Start setting required values in model for accessing it on jsp
		String username;
		Collection<? extends GrantedAuthority> rolenames = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			rolenames = ((UserDetails) principal).getAuthorities();
		} else {
			username = principal.toString().trim();
		}

		String objectOwner = customSecurityService.getOwnerOfObject(post).trim();
		
		if (username.equals(objectOwner))
			model.addAttribute("isObjectOwner", true);
		else
			model.addAttribute("isObjectOwner", false);
		// Will be used on jsp.
		model.addAttribute("selectedObjectId", id);
		model.addAttribute("domain", domain);
		model.addAttribute("username", username);
		model.addAttribute("rolenames",rolenames);
		model.addAttribute("hasAdministrationRights", true);
		return "acl/acl_administration";
	}

	/**
	 * This will load permission list in grid
	 */
	@RequestMapping(value = "/populatePermissionGrid", method = RequestMethod.GET)
	@ResponseBody
	public String populatePermissionGrid(@RequestParam Long id, @RequestParam String domainClass,
			HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		Object post = aclAdministrationService.prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domainClass,
				id);

		JsonResponse jsonResponse = new JsonResponse();
		List<Aclrights> permissionListForGrid = aclAdministrationService.getPermissionListOfOtherUsersForGrid(post);
		jsonResponse.setPermissionListForGrid(permissionListForGrid);

		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");

		return new Gson().toJson(permissionListForGrid);
	}

	/**
	 * This method will load the current user's acl-permission list.
	 * 
	 **/
	@RequestMapping(value = "/loadMyPermissionList", method = RequestMethod.GET)
	@ResponseBody
	public String loadMyPermissionList(@RequestParam String domainName, @RequestParam Long domainId,
			HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionList = aclAdministrationService.loadMyPermissionList(domainName, domainId);
		jsonResponse.setComboList(permissionList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will load permission list without pre-selection
	 **/
	@RequestMapping(value = "/loadPermissionListDefinedOnDomain", method = RequestMethod.GET)
	@ResponseBody
	public String loadPermissionListDefinedOnDomain(@RequestParam String domainClass, HttpServletRequest request,
			HttpServletResponse response) {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionList = aclAdministrationService.getPermissionListDefinedOnDomain(domainClass);
		jsonResponse.setComboList(permissionList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}// loadPermissionListDefinedOnDomain

	/**
	 * This will load all roles available in the database.
	 * @throws CustomRuntimeException 
	 **/
	@RequestMapping(value = "/roleList", method = RequestMethod.GET)
	@ResponseBody
	public String roleList(HttpServletRequest request, HttpServletResponse response) throws CustomRuntimeException {
		JsonRes jsonResponse = new JsonRes();
		List<NameValueM> roleList = roleService.getAppRoleList();
		jsonResponse.setComboList(roleList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will load all users available within a specific role.
	 * @throws CustomRuntimeException 
	 * @throws NumberFormatException 
	 **/
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	@ResponseBody
	public String userList(@RequestParam String roleId, HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, CustomRuntimeException {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> roleList = userAccountService.getAppAdminUserListForACL(Integer.valueOf(roleId));
		jsonResponse.setComboList(roleList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will be called for loading permissions of a role or a user for editing.
	 **/
	@RequestMapping(value = "/editUserOrRolePermission", method = RequestMethod.GET)
	@ResponseBody
	public String editUserOrRolePermission(@RequestParam Long id, @RequestParam String userOrRoleName,
			@RequestParam String domain, @RequestParam Boolean isPrincipal, HttpServletRequest request,
			HttpServletResponse response) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionList = aclAdministrationService.getUserOrRoleSpecificPermissionList(domain, id,
				userOrRoleName, isPrincipal);

		jsonResponse.setComboList(permissionList);

		if (!isPrincipal)
			jsonResponse.setEditRoleId(roleService.getRoleId(userOrRoleName) + "");
		else
			jsonResponse.setEditUserId(userOrRoleName);

		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/loadCustomBasedPermissions_forLoggedInUser", method = RequestMethod.GET)
	@ResponseBody
	public String loadCustomBasedPermissions_forLoggedInUser(@RequestParam Integer contextId,
			@RequestParam String domainClass, @RequestParam String domainId, @RequestParam Boolean isPrincipal,
			@RequestParam String userOrRoleName, HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionList = aclAdministrationService.loadCustomBasedPermissions_forLoggedInUser(contextId,
				domainClass, domainId, isPrincipal, userOrRoleName);

		jsonResponse.setComboList(permissionList);

		if (!isPrincipal)
			jsonResponse.setEditRoleId(roleService.getRoleId(userOrRoleName) + "");
		else
			jsonResponse.setEditUserId(userOrRoleName);

		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will be called for loading object owner permission while object
	 * ownership changing.
	 **/
	@RequestMapping(value = "/getPermissionListOfObjectOwner", method = RequestMethod.GET)
	@ResponseBody
	public String getPermissionListOfObjectOwner(@RequestParam Long id, @RequestParam String userOrRoleName,
			@RequestParam String domain, @RequestParam Boolean isPrincipal, HttpServletRequest request,
			HttpServletResponse response) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
      
		Object objectWithId = aclAdministrationService.prepareDomainObjectDynamicallyUsingIdAndClassNameWithPkg(domain,
				id);

		List<NameValue> permissionList = aclAdministrationService.getPermissionListOfObjectOwner(objectWithId);

		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setComboList(permissionList);
		String objectOwner = customSecurityService.getOwnerOfObject(objectWithId);
		jsonResponse.setObjectOwner(objectOwner);

		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);

	}// End of getUserSpecificPermissionListAsJSON

	/**
	 * This will be called for deleting all permissions defined for a role or a user
	 * on object.
	 **/
	@RequestMapping(value = "/deleteUserOrRolepermission", method = RequestMethod.GET)
	@ResponseBody
	public String deleteUserOrRolepermission(@RequestParam Integer id, @RequestParam String domainClassName,
			@RequestParam String userOrRoleName, @RequestParam boolean isPrincipal, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JsonResponse jsonResponse = new JsonResponse();
		aclAdministrationService.deleteUserOrRolePermission(id, domainClassName, userOrRoleName, isPrincipal);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("Selected record have been deleted.");
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will load all roles available in the database.
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
	 * This will custom permission for sharing permissions
	 **/
	@RequestMapping(value = "/loadCustomBasedPermissions_forSharingPermission", method = RequestMethod.GET)
	@ResponseBody
	public String loadCustomBasedPermissions_forSharingPermission(@RequestParam String contextId,
			@RequestParam String domainClass, @RequestParam Long domainId, HttpServletRequest request,
			HttpServletResponse response) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionCustomContextList = aclAdministrationService
				.loadCustomBasedPermissions_forSharingPermission(contextId, domainClass, domainId);
		jsonResponse.setComboList(permissionCustomContextList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will load custom permissions of owner for changing owner.
	 **/
	@RequestMapping(value = "/loadCustomBasedPermissions_forSeeingChangeOwnerPermission", method = RequestMethod.GET)
	@ResponseBody
	public String loadCustomBasedPermissions_forSeeingChangeOwnerPermission(@RequestParam String contextId,
			@RequestParam String domainClass, @RequestParam Long domainId, HttpServletRequest request,
			HttpServletResponse response) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionCustomContextList = aclAdministrationService
				.loadCustomBasedPermissions_forSeeingChangeOwnerPermission(contextId, domainClass, domainId);
		jsonResponse.setComboList(permissionCustomContextList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will load custom permissions of a user or a role for editing .
	 **/
	@RequestMapping(value = "/loadCustomBasedPermissions_forEditingPermission", method = RequestMethod.GET)
	@ResponseBody
	public String loadCustomBasedPermissions_forEditingPermission(@RequestParam String contextId,
			@RequestParam String domainClass, @RequestParam Long domainId, @RequestParam boolean isPrincipal,
			@RequestParam String userOrRoleName,

			HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionCustomContextList = aclAdministrationService
				.loadCustomBasedPermissions_forEditingPermission(contextId, domainClass, domainId, isPrincipal,
						userOrRoleName);
		jsonResponse.setComboList(permissionCustomContextList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/loadPermissionsForSharingBasedOnSelectedUser", method = RequestMethod.GET)
	@ResponseBody
	public String loadPermissionsForSharingBasedOnSelectedUser(@RequestParam String roleId, @RequestParam String userId,
			@RequestParam Long contextId, @RequestParam String domainClass, HttpServletRequest request,
			HttpServletResponse response) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionCustomContextList = aclAdministrationService
				.loadPermissionsForSharingBasedOnSelectedUser(roleId, userId, contextId, domainClass);
		jsonResponse.setComboList(permissionCustomContextList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/loadPermissionsForSharingBasedOnSelectedRole", method = RequestMethod.GET)
	@ResponseBody
	public String loadPermissionsForSharingBasedOnSelectedRole(@RequestParam String roleId,
			@RequestParam Long contextId, @RequestParam String domainClass, HttpServletRequest request,
			HttpServletResponse response) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		JsonResponse jsonResponse = new JsonResponse();
		List<NameValue> permissionCustomContextList = aclAdministrationService
				.loadPermissionsForSharingBasedOnSelectedRole(roleId, contextId, domainClass);
		jsonResponse.setComboList(permissionCustomContextList);
		jsonResponse.setStatus(true);
		jsonResponse.setStatusMsg("");
		return new Gson().toJson(jsonResponse);
	}

	// ###########################################################################//
	// ###### Save shared/Update role or user permissions.Change-Ownerships ######//
	// ###########################################################################//

	/**
	 * This will be used for saving change ownership.
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 **/
	@RequestMapping(value = "/saveChangeOwnerships", method = RequestMethod.POST)
	@ResponseBody
	public String saveChangeOwnerships(@RequestBody @Valid ChangeOwnerDTO changeOwnerDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		JsonResponse jsonResponse = new JsonResponse();
		if (result.hasErrors()) {
			// Get error message
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg("");
			jsonResponse.setFieldErrMsgMap(errors);
		} else {
			boolean isOwnerChanged = aclAdministrationService.saveChangeOwnerships(changeOwnerDTO);
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("Owner has been changed successfully.");
		}
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will be used for saving/updating shared permissions of a user
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 **/
	@RequestMapping(value = "/shareOrUpdateUserPermission", method = RequestMethod.POST)
	@ResponseBody
	public String shareOrUpdateUserPermission(@RequestBody @Valid SharedUserPermissionsDTO sharedUserPermissionsDTO,
			BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		JsonResponse jsonResponse = new JsonResponse();
		if (result.hasErrors()) {
			// Get error message
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg("");
			jsonResponse.setFieldErrMsgMap(errors);
		} else {
			if (sharedUserPermissionsDTO.getActionMode().equals("add")) {
				boolean isPermissionSaved = aclAdministrationService.shareUserPermission(sharedUserPermissionsDTO);
				jsonResponse.setStatusMsg("Permissions shared successfully.");
			}
			if (sharedUserPermissionsDTO.getActionMode().equals("edit")) {
				boolean isPermissionUpdated = aclAdministrationService.updateUserPermission(sharedUserPermissionsDTO);
				jsonResponse.setStatusMsg("Permissions updated successfully.");
			}
			jsonResponse.setStatus(true);
		}
		return new Gson().toJson(jsonResponse);
	}

	/**
	 * This will be used for saving shared permissions of a role.
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 **/
	@RequestMapping(value = "/shareOrUpdateRolePermission", method = RequestMethod.POST)
	@ResponseBody
	public String shareOrUpdateRolePermission(@RequestBody @Valid SharedRolePermissionsDTO sharedRolePermissionsDTO,
			BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		JsonResponse jsonResponse = new JsonResponse();
		if (result.hasErrors()) {
			// Get error message
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg("");
			jsonResponse.setFieldErrMsgMap(errors);
		} else {
			if (sharedRolePermissionsDTO.getActionMode().equals("add")) {
				boolean isPermissionSaved = aclAdministrationService.shareRolePermission(sharedRolePermissionsDTO);
				jsonResponse.setStatusMsg("Permissions shared successfully with a role.");
			}
			if (sharedRolePermissionsDTO.getActionMode().equals("edit")) {
				boolean isPermissionUpdated = aclAdministrationService.updateRolePermission(sharedRolePermissionsDTO);
				jsonResponse.setStatusMsg("Permissions updated successfully with a role.");
			}
			jsonResponse.setStatus(true);

		}
		return new Gson().toJson(jsonResponse);
	}

	/*
	 * This will check ownership permission.
	 **/
	@RequestMapping(value = "/checkCurrentUserPermissionForChangingOwnership", method = RequestMethod.GET)
	@ResponseBody
	public String checkCurrentUserPermissionForChangingOwnership(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResponse jsonResponse = new JsonResponse();
		Collection<? extends GrantedAuthority> rolenames = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		rolenames = ((UserDetails) principal).getAuthorities();
		boolean hasRoleSuperAdmin = false;
		Object rolesArr[] = rolenames.toArray();
		String roleName;
		for (int i = 0; i < rolesArr.length; i++) {
			roleName = rolesArr[i].toString();
			if (roleName.equals("ROLE_SUPER_ADMIN"))
				hasRoleSuperAdmin = true;
		}
		if (hasRoleSuperAdmin) {
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("You have permission for changinging ownership.");
		} else {
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(
					"<ul><li style='color:red; font-weight:bold;'>You don't have permission for changinging ownership.</li>"
							+ "<li style='color:green; font-weight:bold;'>Only a user with role ROLE_SUPER_ADMIN can change ownership.</li>"
							+ "<li style='color:red; font-weight:bold;'>An object-owner with ADMINISTRATIVE permission can do other administrative tasks.</li>"
							+ "</ul>");
		}

		return new Gson().toJson(jsonResponse);
	}

	/*
	 * This will check ownership permission.
	 **/
	@RequestMapping(value = "/checkWheatherThisUserHasRightsOfDeletingCompleteACL", method = RequestMethod.GET)
	@ResponseBody
	public String checkWheatherThisUserHasRightsOfDeletingCompleteACL(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResponse jsonResponse = new JsonResponse();
		Collection<? extends GrantedAuthority> rolenames = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		rolenames = ((UserDetails) principal).getAuthorities();
		boolean hasRoleSuperAdmin = false;
		Object rolesArr[] = rolenames.toArray();
		String roleName;
		for (int i = 0; i < rolesArr.length; i++) {
			roleName = rolesArr[i].toString();
			if (roleName.equals("ROLE_SUPER_ADMIN"))
				hasRoleSuperAdmin = true;
		}
		if (hasRoleSuperAdmin) {
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("You have permission for changinging ownership.");
		} else {
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(
					"<ul><li style='color:red; font-weight:bold;'>You don't have permission for deleting complete ACL.</li>"
							+ "<li style='color:green; font-weight:bold;'>Only a user with role ROLE_SUPER_ADMIN can delete complete ACL.</li>"
							+ "<li style='color:red; font-weight:bold;'>An object-owner with ADMINISTRATIVE permission can do other administrative tasks.</li>"
							+ "</ul>");
		}

		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "/deleteAllAclsExceptOwnerAndSuperAdminAcls", method = RequestMethod.GET)
	@ResponseBody
	public String deleteAllAclsExceptOwnerAndSuperAdminAcls(@RequestParam String domainClass, @RequestParam String id,
			HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		JsonResponse jsonResponse = new JsonResponse();
		boolean hasCompleteAclBeenDeleted = false;
		try {
			hasCompleteAclBeenDeleted = aclAdministrationService.deleteAllAclsExceptOwnerAndSuperAdminAcls(domainClass,
					id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (hasCompleteAclBeenDeleted) {
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg(
					"All ACLs except Owner's ACl and ROLE_SUPER_ADMIN's ACL have been deleted successfully.");

		} else {
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg("ACLs have not been deleted. Contact support.");

		}

		return new Gson().toJson(jsonResponse);
	}// End of deleteCompleteAclOfThisObject

}
