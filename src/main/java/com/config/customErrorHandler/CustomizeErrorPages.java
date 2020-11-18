/**
 * 
 */
package com.config.customErrorHandler;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Sanjeev
 *
 */
@Component
public class CustomizeErrorPages implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>{

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {		
		// TODO Auto-generated method stub
		factory.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
		factory.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
		factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));		
		factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
		
		//This is a default error path. It will be used for all those errors which have not been configured above
		factory.addErrorPages(new ErrorPage("/error"));
		
	}

}
