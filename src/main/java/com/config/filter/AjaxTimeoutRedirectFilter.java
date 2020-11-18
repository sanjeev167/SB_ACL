/**
 * 
 */
package com.config.filter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;


/**
 * @author Sanjeev
 *
 */
public class AjaxTimeoutRedirectFilter extends GenericFilterBean {
	
	static final Logger log=LoggerFactory.getLogger(AjaxTimeoutRedirectFilter.class);
	private int customSessionExpiredErrorCode;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		//log.info("AjaxTimeoutRedirectFilter [Session-Check-in doFilter] :==>Start");	
		//log.info("\tAjaxTimeoutRedirectFilter [Coming-URL-in doFilter]:==>"+req.getRequestURI());
		if (req.getSession().getAttribute("SPRING_SECURITY_CONTEXT") == null) {
			
			if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {				
				//log.info("\t\tAjaxTimeoutRedirectFilter [Session-Expired (Ajax-Call-Detected) ]");		
				res.setStatus(this.customSessionExpiredErrorCode);				
				return;//Return Ajax response with a code
			} else {
				//Let it get into other filters chain. 
				//log.info("AjaxTimeoutRedirectFilter [Session-Expired (Non-Ajax-Call-Detected ) ] :==>Getting into other filters.");
				chain.doFilter(req, res);
			}
		} else {
			//Session not expired.Let it get into other filters chain.
			//log.info("AjaxTimeoutRedirectFilter [ Session-Alive-Detected ]");
			chain.doFilter(req, res);
		}
		
		log.info("AjaxTimeoutRedirectFilter:==> doFilter :==> End");		
	}

	public int getCustomSessionExpiredErrorCode() {
		return customSessionExpiredErrorCode;
	}

	public void setCustomSessionExpiredErrorCode(int customSessionExpiredErrorCode) {
		this.customSessionExpiredErrorCode = customSessionExpiredErrorCode;
	}
	
	
	
}