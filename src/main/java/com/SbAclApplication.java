package com;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.config.exception.common.CustomRuntimeException;
import com.pvt.sec.env.service.MenuManagerService;

@EnableCaching
@SpringBootApplication
public class SbAclApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SbAclApplication.class);

	@Autowired
	private ApplicationContext appContext;

	// private static ConfigurableApplicationContext configurableAppContext;

	// @Autowired
	// LoadObjectsInContextAtStartUp loadObjectsInContextAtStartUp;

	@Autowired
	MenuManagerService menuManagerService;

	@Bean
	public ModelMapper modelMapper() {
		// System.out.println("Sanjeev: -- Creating Modal Mapper Bean.");
		return new ModelMapper();
	}

	@Bean
	public ServletContextInitializer initializer() {
		return new ServletContextInitializer() {
			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
				try {
					// Has been loaded while application is booting so that roles related tree can
					// be fetched while logging.
					servletContext.setAttribute("RoleWiseMenuCollectionMap",
							menuManagerService.getRoleWiseMenuCollectionMap());
				} catch (CustomRuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	public static void main(String[] args) {
		System.out.println("\n\n\n\tSanjeev: -- Appplication loading has been started.");
		SpringApplication.run(SbAclApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("\n\n\n\t###############################################################");
		System.out.println("\t#### Application :==> Started and deployed successfully.! #####");
		System.out.println("\t###############################################################\n\n\n");
		/*
		 * String[] beans = appContext.getBeanDefinitionNames(); Arrays.sort(beans); for
		 * (String bean : beans) { log.info("Sanjeev = "+bean); }
		 */
	}

}