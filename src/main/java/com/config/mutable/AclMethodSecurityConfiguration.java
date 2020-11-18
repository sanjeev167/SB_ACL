/**
 * 
 */
package com.config.mutable;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.EhCacheBasedAclCache;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.config.exception.common.CustomRuntimeException;
import com.config.mutable.constants.ROLES;
import com.pvt.sec.rbac.service.RoleHierarchyService;


/**
 * @author Sanjeev
 *
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AclMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
	
	protected static Logger logger = LoggerFactory.getLogger(AclMethodSecurityConfiguration.class);
	
	@Autowired
	@Qualifier("aclDataSource")
	DataSource dataSource;	
	//@Autowired
	///RoleHierarchyService roleHierarchyService;	
	
	
	//Not being used right now
	@Bean
	public ObjectIdentityRetrievalStrategyImpl objectIdentityRetrievalStrategy() {
		return new ObjectIdentityRetrievalStrategyImpl();
	}
	//Not being used right now
	@Bean
	public SidRetrievalStrategy sidRetrievalStrategy() {
		return new SidRetrievalStrategyImpl();
	}
	
	
    /**
     * permissionGrantingStrategy() will be used by aclCache()
     * **/
	@Bean
	public DefaultPermissionGrantingStrategy permissionGrantingStrategy() {
		ConsoleAuditLogger consoleAuditLogger = new ConsoleAuditLogger();
		return new DefaultPermissionGrantingStrategy(consoleAuditLogger);
	}
	 
	/**
	 * aclCacheManager() is being used by aclEhCacheFactoryBean()
	 * **/
	@Bean
    public EhCacheManagerFactoryBean aclCacheManager() {
        return new EhCacheManagerFactoryBean();
    }
	
	
	/**
	 * EhCacheBasedAclCache is using aclEhCacheFactoryBean and aclEhCacheFactoryBean() is utilized by aclCache()
	 **/
	
	@Bean
    public EhCacheFactoryBean aclEhCacheFactoryBean() {
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(aclCacheManager().getObject());
        ehCacheFactoryBean.setCacheName("aclCache");
        return ehCacheFactoryBean;
    }
	
	
	/**
	 * Here we declare Spring Security's default implementation of AclCache
	 * interface. It's purpose is to lessen database lookups.
	 **/
	@Bean
    public EhCacheBasedAclCache aclCache() {
        return new EhCacheBasedAclCache(aclEhCacheFactoryBean().getObject(), permissionGrantingStrategy(), aclAuthorizationStrategy());
    }
	
	
	/**
	 * aclCache is using aclAuthorizationStrategy Strategy used by AclImpl to
	 * determine whether a principal is permitted to call administrative methods on
	 * the AclImpl.
	 * 
	 * AclAuthorizationStrategyImpl is the default implementation of
	 * AclAuthorizationStrategy. Notice the constructor accepts three arguments.
	 * Based on the Spring Security API, constructor signature is as follows:
	 * 
	 * auths - an array of GrantedAuthoritys that have special permissions (index 0
	 * is the authority needed to change ownership, index 1 is the authority needed
	 * to modify auditing details, index 2 is the authority needed to change other
	 * ACL and ACE details)
	 **/
	AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(
        		new SimpleGrantedAuthority(ROLES.ROLE_SUPER_ADMIN),//index 0
                new SimpleGrantedAuthority(ROLES.ROLE_SUPER_ADMIN),//index 1 
                new SimpleGrantedAuthority(ROLES.ROLE_SUPER_ADMIN));//index 2
    }

	
	/**
	 * the purpose of a lookup strategy is to provide an optimized lookup when querying the database.
	 **/
	LookupStrategy lookupStrategy() {
		BasicLookupStrategy basicLookupStrategy=new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(), new ConsoleAuditLogger());
		basicLookupStrategy.setPermissionFactory(customPermissionFactory());		
        return basicLookupStrategy;   
	}

	
	/**
	 * AclPermissionEvaluator will use this aclService while evaluating permission
	 **/
	@Bean
    JdbcMutableAclService aclService() {
        JdbcMutableAclService service = new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
        service.setClassIdentityQuery("select currval(pg_get_serial_sequence('acl_class', 'id'))");
        service.setSidIdentityQuery("select currval(pg_get_serial_sequence('acl_sid', 'id'))");
        
        return service;
    }
	
	/**
	 * customPermissionFactory is required be configured here so that AclPermissionEvaluator could be told to evalute these custom permission along with base 
	 * permissions. 
	 **/
		@Bean
		public CustomPermissionFactory customPermissionFactory(){		
			return new CustomPermissionFactory();
		}

	/**
	 * defaultMethodSecurityExpressionHandler has been configured for using permission evaluator as AclPermissionEvaluator.
	 * 
	 **/
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();		
		AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(aclService());//Initializing with a configured aclService()		
		permissionEvaluator.setPermissionFactory(customPermissionFactory());//Initializing with a configured customPermissionFactory()		 
		expressionHandler.setPermissionEvaluator(permissionEvaluator);//Telling expressionHandler to use this permission evaluator
		expressionHandler.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(aclService()));// Telling expressionHandler to use this AclPermissionCacheOptimizer
		return expressionHandler;
	}
	
}







/* Problem of lazily initialization of rolehierarchy entity. Will be corrected latter.
 * Role hierarchy in ACL will not work unless it is implemented.
 */

/*
RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
String roles;
try {
	roles = RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyService.getRoleHierarchyConfiguredForSecurity());
	roleHierarchy.setHierarchy(roles);
} catch (CustomRuntimeException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}		
expressionHandler.setRoleHierarchy(roleHierarchy);
*/
