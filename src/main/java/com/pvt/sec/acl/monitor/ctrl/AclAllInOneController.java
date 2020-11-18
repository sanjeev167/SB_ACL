/**
 * 
 */
package com.pvt.sec.acl.monitor.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping(value = "/acl/monitor/tables/")
public class AclAllInOneController {

	static final Logger log = LoggerFactory.getLogger(AclAllInOneController.class);
	@RequestMapping(value = "")
	public String viewAllAclTableTogather(Model model) {
		log.info("AclAllInOneController :==> viewAllAclTableTogather :==> Started");
		String target="/acl/monitor/aclAllInOne";
		log.info("AclAllInOneController :==> viewAllAclTableTogather :==> Ended");
		return target;
	}//End of openAclAllInOne
	
	
}//End of AclAllInOneController
