/**
 * 
 */
package com.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.config.exception.common.CustomRuntimeException;
import com.config.filter.AjaxTimeoutRedirectFilter;

import com.pvt.adm.auth.service.CustomAdminUserDetailsService;
import com.pvt.sec.rbac.service.RoleHierarchyService;
import com.pvt.webUser.auth.service.CustomWebUserDetailsService;

/**
 * @author Sanjeev
 *
 */

@Configuration//Requires for method level security
@EnableWebSecurity//Requires for spring security
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	static final Logger log = LoggerFactory.getLogger(SpringSecurityConfig.class);

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	@Qualifier("appDataSource")
	private DataSource dataSource;
	
	@Autowired
	CustomAdminUserDetailsService customAdminUserDetailsService;
	
	@Autowired
	CustomWebUserDetailsService customWebUserDetailsService;	
	
	@Autowired
	RoleHierarchyService roleHierarchyService;	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public void JDBC_CustomUserDetailsService(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource)
		    .usersByUsernameQuery("select username, password, enabled from users where username=?")
	        .authoritiesByUsernameQuery("select username, authority from authorities where username=?").passwordEncoder(passwordEncoder());	
	}
			
	@Bean
	public UserDetailsService InMemory_CustomUserDetailsService() throws Exception {
		
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("Modi").password(passwordEncoder().encode("super"))
				.roles("SUPER_ADMIN").build());
		
		manager.createUser(User.withUsername("Lalu").password(passwordEncoder().encode("admin"))
				.roles("SECURITY_ADMIN").build());
		manager.createUser(User.withUsername("Nitesh").password(passwordEncoder().encode("admin"))
				.roles("SECURITY_ADMIN").build());
		
		manager.createUser(User.withUsername("Mamta").password(passwordEncoder().encode("user"))
				.roles("USER").build());
		manager.createUser(User.withUsername("Samta").password(passwordEncoder().encode("user"))
				.roles("USER").build());
		
		manager.createUser(User.withUsername("Maya").password(passwordEncoder().encode("visitor"))
				.roles("VISITOR").build());
		manager.createUser(User.withUsername("Mohni").password(passwordEncoder().encode("visitor"))
				.roles("VISITOR").build());
		
		return manager;
		}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		   //auth.userDetailsService(InMemory_CustomUserDetailsService());
		// auth.userDetailsService(JDBC_CustomUserDetailsService());
		 auth.userDetailsService(customAdminUserDetailsService);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**","*.css","*.js", "/favicon.ico",
				                   "/", "/home","/pub/**", "/error/**");		
	}
	
	/**
     * Prepare a role hierarchy 
     * **/
	@Bean(value = "customRoleHierarchy")
	public RoleHierarchy customRoleHierarchy() throws CustomRuntimeException {		
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();	String roles=null;
		
		//if(!roleHierarchyService.getRoleHierarchyConfiguredForSecurity().isEmpty())
		 roles = RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyService.getRoleHierarchyConfiguredForSecurity());	
		//System.out.println("roles = "+roles);
		//This will be used while loading role based tree when a user is loggedIn
		servletContext.setAttribute("RolesInHierarchy",roleHierarchyService.getRoleHierarchyConfiguredForSecurity());		
		//if(roles!=null)
		roleHierarchy.setHierarchy(roles);		
		return roleHierarchy;
	}
	
	/**
	 * Now prepare a webSecurityExpressionHandler using above role hierarchy
	 * **/
	@Bean
	public SecurityExpressionHandler<FilterInvocation> customRoleHierarchyExpressionHandler() throws CustomRuntimeException {
		DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		webSecurityExpressionHandler.setRoleHierarchy(customRoleHierarchy());
		return webSecurityExpressionHandler;
	}
	
	public void dynamicallyConfigureWebUrlAccessForRole(HttpSecurity http) throws Exception {		
		Map<String, ArrayList<String>> urlAndAccessRolesMap=urlAndRoleMatcherService().getWebUrlAndRoleMatcherList();        
        Iterator urlAndAccessRolesIterator;
		if (urlAndAccessRolesMap != null) {
			String url_pattern=null;	String roleList[]=null;			
			urlAndAccessRolesIterator = urlAndAccessRolesMap.entrySet().iterator();
			// Iterate through the hashmap
			while (urlAndAccessRolesIterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry mapElement = (Map.Entry) urlAndAccessRolesIterator.next();
				ArrayList<String> roleArr=(ArrayList<String>)mapElement.getValue();
				Object[] roleObjArr=roleArr.toArray();
			    roleList=Arrays.copyOf(roleObjArr, roleObjArr.length,String[].class);
				url_pattern=mapElement.getKey().toString();
				//System.out.println("Ant matcher Url => " + url_pattern + " : Roles " + mapElement.getValue().toString());				
				
				http.authorizeRequests().antMatchers(url_pattern).hasAnyRole(roleList);			
			}
		}		
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {		 
	   http.csrf().disable();
	 //http.requiresChannel().anyRequest().requiresSecure();//Redirect to https
	   
	   http.authorizeRequests().expressionHandler(customRoleHierarchyExpressionHandler());	   
	   http.addFilterAfter(ajaxTimeoutRedirectFilter(), ExceptionTranslationFilter.class);
	   
	   dynamicallyConfigureWebUrlAccessForRole(http);//Working properly.Tested 
       http.authorizeRequests()		       
	       .anyRequest().authenticated();//Rest url requests needs to be authenticated.	      
          // .accessDecisionManager(customAccessDecisionManager())//Will impact role hierarchy     
        http 
	        .exceptionHandling()
	        .accessDeniedPage("/403")	        
	        .defaultAuthenticationEntryPointFor(forceToAuthenticateIfAccessingThisPathPattern(),
	        		                            new AntPathRequestMatcher("/**")
	        		                            );	     
        http
		    .formLogin()
		    .loginPage("/pub/user/loginPage")	
		    .loginProcessingUrl("/doLogin")
		    .defaultSuccessUrl("/bulletin/showRoadmap")		    
	        .failureUrl("/pub/user/loginPage?error=true")
	        .permitAll();	      
        http
		    .logout()
		    .logoutUrl("/perform_logout")
	        .logoutSuccessUrl("/pub/user/loginPage?logout=true")
	        .clearAuthentication(true)//Will clear all the authentication details
			.invalidateHttpSession(true)		   
			.deleteCookies("JSESSIONID")
            .permitAll();           
        http
		   .sessionManagement()           
	       .invalidSessionUrl("/pub/user/loginPage?invalid=true")
	       .maximumSessions(1)
		   .maxSessionsPreventsLogin(false)
		   .expiredUrl("/pub/user/loginPage?expired=true");       
       http 
	 	    .rememberMe()
	 	    .rememberMeParameter("remember-me")
	 	    .rememberMeCookieName("pon-remember-me")
	 	    .tokenValiditySeconds(24*60*60)
	 	    .tokenRepository(tokenRepository());
	        
     // http.apply(new SpringSocialConfigurer()).signupUrl("/signup");
        
	}
		
	@Bean
	public AuthenticationEntryPoint forceToAuthenticateIfAccessingThisPathPattern() {		
		return new LoginUrlAuthenticationEntryPoint("/pub/user/loginPage?authenticate=true");
	}
	
	@Bean
	  public PersistentTokenRepository tokenRepository() {
	    JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl=new JdbcTokenRepositoryImpl();
	    jdbcTokenRepositoryImpl.setDataSource(dataSource);
	    return jdbcTokenRepositoryImpl;
	  }
	
	@Bean
	public AccessDecisionManager customAccessDecisionManager() {		
	    List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(
	        new WebExpressionVoter(),//Relying on its default implementation
	        new RoleVoter(),//Relying on its default implementation
	        new AuthenticatedVoter()//,//Relying on its default implementation
	        //This is a custom voter implementation. 
	       //,new MinuteBasedVoter()
	      //We can add any other voter as per our need
	     //, new DynamicMethodAccessVoter()
	    //Will be used for custom method access decision making	        
	    );
	    return new UnanimousBased(decisionVoters); 
	}
	
	@Bean
	public Filter ajaxTimeoutRedirectFilter() {
		AjaxTimeoutRedirectFilter atof = new AjaxTimeoutRedirectFilter();
		atof.setCustomSessionExpiredErrorCode(440);
	    return atof;
	}
	
	@Bean
	UrlAndRoleMatcherService urlAndRoleMatcherService() {
		return new UrlAndRoleMatcherServiceImpl();
	}
}
