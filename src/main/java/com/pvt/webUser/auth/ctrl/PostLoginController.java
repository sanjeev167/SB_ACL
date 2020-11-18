/**
 * 
 */
package com.pvt.webUser.auth.ctrl;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pub.en.ctrl.WebUserSignInSignUpController;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping(value = "/pvt/user/")
public class PostLoginController {
	static final Logger log = LoggerFactory.getLogger(WebUserSignInSignUpController.class);

	
	// After a successful login the control can be transfered anywhere as per
		// business need.
		@RequestMapping(value = "postLogin", method = RequestMethod.GET)
		public String postLogin(Model model, HttpSession session) {

			log.info("postLogin()");

			// read principal out of security context and set it to session
			UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
					.getContext().getAuthentication();

			validatePrinciple(authentication.getPrincipal());

			// User loggedInUser = ((PdfUserDetails)
			// authentication.getPrincipal()).getUserDetails();

			// model.addAttribute("currentUser", loggedInUser.getUsername());

			// session.setAttribute("userId", loggedInUser.getId());

			// return "redirect:/wallPage";
			return "pvt/user/dbPage";
		}
		
		private void validatePrinciple(Object principal) {

			// if (!(principal instanceof PdfUserDetails)) {
			// throw new IllegalArgumentException("Principal can not be null!");
			// }
		}

}
