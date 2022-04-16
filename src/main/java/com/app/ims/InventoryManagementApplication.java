package com.app.ims;

import com.app.ims.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InventoryManagementApplication extends SpringBootServletInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryManagementApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InventoryManagementApplication.class);
	}

	@Bean
	public CommandLineRunner testApp(InitService repo) {
		return args -> {
			if(repo.init1() && repo.init2()){
				LOGGER.debug("run method called successfully!");
			}
		};
	}
}
