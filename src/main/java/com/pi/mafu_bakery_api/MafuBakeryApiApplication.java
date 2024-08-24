package com.pi.mafu_bakery_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MafuBakeryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MafuBakeryApiApplication.class, args);
	}

}
