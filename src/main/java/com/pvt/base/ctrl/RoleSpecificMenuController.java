/**
 * 
 */
package com.pvt.base.ctrl;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sanjeev
 *
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.google.gson.Gson;
import com.pvt.adm.auth.service.CustomAdminUserDetailsService;
import com.pvt.base.service.UserRolesBasedTreeService;
import com.pvt.sec.env.service.MenuManagerService;
import com.share.JsonResp.JsonRes;




@Controller
public class RoleSpecificMenuController {
	
	private static final Logger log = LogManager.getLogger(RoleSpecificMenuController.class);

	@Autowired
	MenuManagerService menuManagerService;
	//UserDetailsService customAdministrativeUserDetailsService;
	
	@Autowired
	UserRolesBasedTreeService userRolesBasedTreeService;
	@Autowired
	CustomAdminUserDetailsService customAdminUserDetailsService;

	@RequestMapping(value = "getSpecificTreeTypeStructureForLoggedInUser", method = RequestMethod.GET)
	@ResponseBody
	public String getSpecificTreeTypeStructureForLoggedInUser(@RequestParam Integer id,HttpServletRequest request, HttpServletResponse response) {

		log.info("RoleSpecificMenuController :==> getSpecificTreeTypeStructureForLoggedInUser :==> Started");
		JsonRes jsonResponse = new JsonRes();
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
				  SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		
		try {			
			jsonResponse.setFormObject(userRolesBasedTreeService.getMenuListForLoggedInUser(authorities));
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

		log.info("RoleSpecificMenuController :==> getSpecificTreeTypeStructureForLoggedInUser :==> Ended");	
		return new Gson().toJson(jsonResponse);
	}
		
	private void validatePrinciple(Object principal) {

		// if (!(principal instanceof PdfUserDetails)) {
		// throw new IllegalArgumentException("Principal can not be null!");
		// }
	}

}// End of RoleSpecificMenuController