/**
 * 
 */
package com.config.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.stereotype.Component;

/**
 * @author Sanjeev
 *
 */
@Component
public class CustomSiteMeshFilter extends ConfigurableSiteMeshFilter {
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder
				// Exclude path from decoration.
				.addExcludedPath("/resources/*")
				//.setIncludeErrorPages(true)
				// Assigning default decorator if no path specific decorator found
				// .addDecoratorPath("/*.jsp", "/WEB-INF/decorator/no_decorator.jsp")
					
				.addDecoratorPath("/", "/WEB-INF/decorator/home_decorator.jsp")				
				.addDecoratorPath("/home", "/WEB-INF/decorator/home_decorator.jsp")
				.addDecoratorPath("/pub/user/**", "/WEB-INF/decorator/home_decorator.jsp")	
				.addDecoratorPath("/error/**", "/WEB-INF/decorator/home_decorator.jsp")				
				// Map admin decorators to specific path patterns.
				
				.addDecoratorPath("/pvt/sec/webAccessRiights/**", "/WEB-INF/decorator/admin_decorator.jsp")
				.addDecoratorPath("/sec/**", "/WEB-INF/decorator/admin_decorator.jsp")
				.addDecoratorPath("/acl/monitor/tables/", "/WEB-INF/decorator/admin_decorator.jsp")
				.addDecoratorPath("/bulletin/**", "/WEB-INF/decorator/admin_decorator.jsp")
				.addDecoratorPath("/acl_admin*", "/WEB-INF/decorator/admin_decorator.jsp")
				.addDecoratorPath("/domain_acl/**", "/WEB-INF/decorator/admin_decorator.jsp")				
		        .addDecoratorPath("/domain_permission/**", "/WEB-INF/decorator/admin_decorator.jsp").create();
		
	}// End of applyCustomConfiguration
	
	

}// End of CustomSiteMeshFilter