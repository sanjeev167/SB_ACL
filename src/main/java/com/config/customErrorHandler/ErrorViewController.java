/**
 * 
 */
package com.config.customErrorHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping("/error")
public class ErrorViewController {
	static final Logger log = LoggerFactory.getLogger(ErrorViewController.class);
	
	@RequestMapping(value = "/400", method = RequestMethod.GET)
 	public String get400() {
		log.info("Received request to show 400 page");		
		return "error/400";
	}
	
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
 	public String get403() {
		log.info("Received request to 403 denied page");		
		return "error/403";
	}
	
	@RequestMapping(value = "/404", method = RequestMethod.GET)
 	public String get404() {
		log.info("Received request to show 404 page");		
		return "error/404";
	}
	
	
	
	@RequestMapping(value = "/500", method = RequestMethod.GET)
 	public String get500() {
		log.info("Received request to show 500 page");	
		System.out.println("NNJNJ");
		return "error/500";
	}
}
