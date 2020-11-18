/**
 * 
 */
package com.pub.en.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Sanjeev
 *
 */

@Controller
public class HomeController {
	protected static Logger logger = LoggerFactory.getLogger(HomeController.class);
	 @GetMapping({"/", "/home"})
	//@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String hoem(Model model) {
		logger.debug("Received request to home page");		
		return "home";
	}
}
