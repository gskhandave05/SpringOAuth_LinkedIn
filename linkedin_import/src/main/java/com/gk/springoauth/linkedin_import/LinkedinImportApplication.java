package com.gk.springoauth.linkedin_import;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan("com.gk.springoauth.linkedin_import.config")
@ComponentScan("com.gk.springoauth.linkedin_import.controller")
public class LinkedinImportApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkedinImportApplication.class, args);
	}
}
