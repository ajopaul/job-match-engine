package com.ajopaul.jobmatchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class JobMatchEngineApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JobMatchEngineApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(JobMatchEngineApplication.class, args);
	}

}
