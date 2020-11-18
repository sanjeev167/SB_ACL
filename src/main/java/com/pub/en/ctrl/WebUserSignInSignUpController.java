/**
 * 
 */
package com.pub.en.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.rbac.dto.WebUserDTO;
import com.pvt.sec.rbac.service.WebUserAccountService;

/**
 * @author Sanjeev
 *
 */


//@SessionAttributes({ "currentUser" })
@Controller
@RequestMapping("/pub/user")
public class WebUserSignInSignUpController {

	static final Logger log = LoggerFactory.getLogger(WebUserSignInSignUpController.class);

	@Autowired
	WebUserAccountService webUserAccountService;

	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)	
		public String getLoginPage(@RequestParam(value="error", required=false) boolean error, 
				                   @RequestParam(value="logout", required=false) boolean logout, 
				                   @RequestParam(value="authenticate", required=false) boolean authenticate,			
				                   @RequestParam(value="invalid", required=false) boolean invalid,			
				                   @RequestParam(value="expired", required=false) boolean expired,	 
				                  ModelMap model) {			
		
		if (error)
			model.addAttribute("errorMessge", "Either Username or Password is incorrect !!");
		if (logout)
			model.addAttribute("logout", "You have been logged out successfully !!");
		if (authenticate)
			model.addAttribute("authenticate", "First authenticate yourself !!");
		if (expired)
			model.addAttribute("expired", "Your current session has expired !!");
		if (invalid)
			model.addAttribute("invalid", "Your session is invalid !!");
		
		
		return "pub/user/login";
	}

	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("register()");
		ModelAndView mv = new ModelAndView("pub/user/webUserRegister");
		mv.addObject("webUserDTO", new WebUserDTO());
		return mv;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(@Valid WebUserDTO webUserDTO, BindingResult result, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("WebUserSignInSignUpController :==> saveAndUpdateRecord :==> Started");
		ModelAndView mv = new ModelAndView("pub/user/webUserRegister");
		//
		try {
			if (result.hasErrors()) {
				// Get error message
			} else {
				// Implement business logic to save record into database
				webUserAccountService.saveAndUpdate(webUserDTO);
				model.addAttribute("message", "You have been registered successfully !!");
			}
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		mv.addObject("webUserDTO", webUserDTO);
		log.info("WebUserSignInSignUpController :==> saveAndUpdateRecord :==> Ended");
		return mv;

	}

	@RequestMapping(value = "/registerfb", method = RequestMethod.GET)
	public ModelAndView registerf(@Valid WebUserDTO webUserDTO, BindingResult result, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("WebUserSignInSignUpController :==> saveAndUpdateRecord :==> Started");
		ModelAndView mv = new ModelAndView("pub/user/webUserRegister");
		try {
			log.info("Facebook Signup");
			webUserAccountService.saveAndUpdate(webUserDTO);
			model.addAttribute("message", "You have been registered successfully !!");

		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;

		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		mv.addObject("webUserDTO", webUserDTO);
		log.info("WebUserSignInSignUpController :==> saveAndUpdateRecord :==> Ended");
		return mv;

	}

	@RequestMapping(value = "/registergg", method = RequestMethod.GET)
	public ModelAndView registergg(@Valid WebUserDTO webUserDTO, BindingResult result, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("WebUserSignInSignUpController :==> saveAndUpdateRecord :==> Started");
		ModelAndView mv = new ModelAndView("pub/user/webUserRegister");
		try {
			log.info("Facebook Signup");
			webUserAccountService.saveAndUpdate(webUserDTO);
			model.addAttribute("message", "You have been registered successfully !!");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;

		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		mv.addObject("webUserDTO", webUserDTO);
		log.info("WebUserSignInSignUpController :==> saveAndUpdateRecord :==> Ended");
		return mv;
	}

	@RequestMapping(value = "/fgotpwd", method = RequestMethod.GET)
	public String fgotpwd(Model model) {
		log.info("fgotpwd()");
		return "pub/user/fgotpwd";
	}

	@RequestMapping(value = "/fgotpwd", method = RequestMethod.POST)
	public String sendFgotpwd(Model model) {
		log.info("fgotpwd()");

		return "pub/user/fgotpwd";
	}
	
	

}// End of WebUserSignInSignUpController