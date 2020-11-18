/**
 * 
 */
package com.config.db;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
		AppPersistentConstant.PKG_REPO_ENV_MODULE,
		AppPersistentConstant.PKG_REPO_RBAC_MODULE,		
		AppPersistentConstant.PKG_REPO_POST_MODULE,
		AppPersistentConstant.PKG_REPO_ACL_ADMIN_MODULE
        }, 
        entityManagerFactoryRef = "appEntityManager", 
        transactionManagerRef = "appTransactionManager")

public class AppPersistenceConfiguration {

	@Autowired
	private Environment env;// Contains Properties loaded by @PropertySources

	// APP data source configuration
	@Bean(name = "appDataSource")
	public DataSource appDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name.post"));
		dataSource.setUrl(env.getProperty("spring.datasource.url.post"));// Different from acl database
		dataSource.setUsername(env.getProperty("spring.datasource.username.post"));
		dataSource.setPassword(env.getProperty("spring.datasource.password.post"));
		return dataSource;
	}

	// APP appEntityManager bean
	@Bean
	public LocalContainerEntityManagerFactoryBean appEntityManager() {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(appDataSource());
		// Scan Entities in Package:
		em.setPackagesToScan(AppPersistentConstant.APP_ENTITIES_ARRAY);		
		em.setPersistenceUnitName(AppPersistentConstant.APP_JPA_UNIT);// Important !!
		
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

		//jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
		em.setJpaPropertyMap(jpaProperties);
		em.afterPropertiesSet();

		return em;
	}

	// APP appTransactionManager bean
	@Bean(name = "appTransactionManager")
	public PlatformTransactionManager appTransactionManager() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(appEntityManager().getObject());
		return transactionManager;
	}

}// End of PersistenceAppAutoConfiguration
