/**
 * 
 */
package com.pvt.post.ctrl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pvt.post.service.GenericService;

/**
 * @author Sanjeev
 *
 */
/**
 * Handles Bulletin related requests
 */
@Controller
@RequestMapping("/bulletin")
public class BulletinController {

	protected static Logger logger = LoggerFactory.getLogger(BulletinController.class);

	@Resource(name = "adminService")
	private GenericService adminService;

	@Resource(name = "personalService")
	private GenericService personalService;

	@Resource(name = "publicService")
	private GenericService publicService;
	 

	/**
	 * Retrieves the View page.
	 * <p>
	 * This loads all authorized posts.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getViewAllPage(Model model) {
		logger.info("Received request to view all page");		
		// Retrieve items from service and add to model
		model.addAttribute("adminposts", adminService.getAll());
		
		model.addAttribute("adminDomain","com.pvt.post.domain.AdminPost");
		model.addAttribute("personalposts", personalService.getAll());
		model.addAttribute("personalDomain","com.pvt.post.domain.PersonalPost");
		model.addAttribute("publicposts", publicService.getAll());
		model.addAttribute("publicDomain","com.pvt.post.domain.PublicPost");
		// Add our current role and username
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());

		return "bulletinpage";
	}
	
	@RequestMapping(value = "/showRoadmap", method = RequestMethod.GET)
	public String showRoadmap(Model model) {
		logger.info("Received request to view showRoadmap");		
		
		return "roadmap";
	}
	
	
}
