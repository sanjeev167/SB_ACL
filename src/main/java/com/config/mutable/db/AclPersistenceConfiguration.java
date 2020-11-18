/**
 * 
 */
package com.config.mutable.db;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Sanjeev
 *
 */
@Configuration
@EnableTransactionManagement
//Load to Environment
//(@see resources/datasource-cfg.properties).

@PropertySource({ "classpath:datasource-cfg.properties" })
@EnableJpaRepositories(basePackages = {
		AclPersistentConstant.PKG_REPO_ACL_MONITOR }, 
        entityManagerFactoryRef = "aclEntityManager", 
        transactionManagerRef = "acl_tm")

public class AclPersistenceConfiguration {

	@Autowired
	private Environment env;// Contains Properties loaded by @PropertySources

	// ACL data source configuration
	@Bean(name = "aclDataSource")
	public DataSource aclDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name.acl"));
		dataSource.setUrl(env.getProperty("spring.datasource.url.acl"));
		dataSource.setUsername(env.getProperty("spring.datasource.username.acl"));
		dataSource.setPassword(env.getProperty("spring.datasource.password.acl"));
		return dataSource;
	}

	// ACL aclSecurityEntityManager bean
	@Bean (name = "aclEntityManager")
	public LocalContainerEntityManagerFactoryBean aclEntityManager() {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(aclDataSource());
		// Scan Entities in Package:
		em.setPackagesToScan(AclPersistentConstant.ACL_MONITOR_PKG_ENTITIES_ARRAY);
		em.setPersistenceUnitName(AclPersistentConstant.JPA_UNIT_ACL_MONITOR); // Important !!

		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		// JPA & Hibernate
		final HashMap<String, Object> jpaProperties = new HashMap<String, Object>();

		// Configures the used database dialect. This allows Hibernate to create SQL
		// that is optimized for the used database.
		jpaProperties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));

		// If the value of this property is true, Hibernate writes all SQL
		// statements to the console.
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));

		// If the value of this property is true, Hibernate will format the SQL
		// that is written to the console.
		jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));

		// Solved Error: PostGres createClob() is not yet implemented.
		// PostGres Only:
		jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", false);

		em.setJpaPropertyMap(jpaProperties);
		em.afterPropertiesSet();

		return em;
	}

	
	
	@Bean(name = "acl_tm")
	public PlatformTransactionManager aclTransactionManager() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(aclEntityManager().getObject());
		return transactionManager;
	}

}// End of PersistenceAclAutoConfiguration
