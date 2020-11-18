/**
 * 
 */
package com.pvt.sec.acl.monitor.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import com.pvt.sec.acl.monitor.dto.AclClassDTO;
import com.pvt.sec.acl.monitor.service.AclClassService;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping(value = "/acl/monitor/aclClass")
public class AclClassController {

	static final Logger log = LoggerFactory.getLogger(AclClassController.class);
	@Autowired
	AclClassService aclClassService;

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listAclClassPaginated(HttpServletRequest request, 
			                            HttpServletResponse response, 
			                            String classWithPkg) {
		log.info("AclClassController :==> listAclClassPaginated :==> Started");

		DataTableResults<AclClassDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";
			if (!classWithPkg.isEmpty())
				whereClauseForBaseQuery = "where class LIKE '%" + classWithPkg + "%'";
			dataTableResult = aclClassService.loadGrid(request, whereClauseForBaseQuery);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("AclClassController :==> listAclClassPaginated :==> Ended");
		return new Gson().toJson(dataTableResult);
	}

}// End of AclSidAndClassController
