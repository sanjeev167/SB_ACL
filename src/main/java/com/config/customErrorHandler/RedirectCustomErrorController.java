/**
 * 
 */
package com.config.customErrorHandler;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Sanjeev
 *
 */

@Controller
public class RedirectCustomErrorController implements ErrorController {

	/**
	 * If you want to replace white lable error page , override getErrorPath()
	 * method available in the default ErrorController and return that path on which
	 * you have written your code to show an error page.
	 **/

	private static final String PATH = "/error";// Here we are considering the path that spring is using.But it can be
												// changed.

	@GetMapping(value = PATH)
	private String error() {
		System.out.println("##  Error found");
		return "error/common";
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}

	/**
	 * Following methods have been written for showing customized
	 * message-error-pages. When these method will be used, the above will not be
	 * used for any such path which have been defined below.Only those error
	 * which have not yet been defined will be tackled by the above code, as the default
	 * error path has been set in CustomizeErrorPages as /error.
	 **/

	@GetMapping(value = "/400")
	private String error_400() {
		System.out.println("## 400 Error found");
		
		return "redirect:/error/400";
	}

	@GetMapping(value = "/403")
	private String error_403() {
		System.out.println("##  403 Error found");
		return "redirect:/error/403";
	}

	@GetMapping(value = "/404")
	private String error_404() {
		System.out.println("##  Error 404 found");
		return "redirect:/error/404";
	}

	@GetMapping(value = "/500")
	private String error_500() {
		System.out.println("## Error 500 found");
		return "redirect:/error/500";
	}

}// End of AppCustomErrorController
