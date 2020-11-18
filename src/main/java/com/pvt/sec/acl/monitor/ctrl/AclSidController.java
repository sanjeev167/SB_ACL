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

import com.pvt.sec.acl.monitor.dto.AclSidDTO;
import com.pvt.sec.acl.monitor.service.AclSidService;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.google.gson.Gson;
import com.share.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping(value = "/acl/monitor/sid/")
public class AclSidController {

	static final Logger log = LoggerFactory.getLogger(AclSidController.class);
	@Autowired
	AclSidService aclSidService;

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listAclSidPaginated(HttpServletRequest request, HttpServletResponse response, String sid) {

		log.info("AclSidController :==> listAclSidPaginated :==> Started");
		DataTableResults<AclSidDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";
			if (!sid.isEmpty())
				whereClauseForBaseQuery = "where sid LIKE '%" + sid + "%'";
			dataTableResult = aclSidService.loadGrid(request, whereClauseForBaseQuery);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("AclSidController :==> listAclSidPaginated :==> Ended");
		return new Gson().toJson(dataTableResult);
	}

}// End of AclSidAndClassController
