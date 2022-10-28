package com.myasinmanager;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.myasinmanager"})
@EnableDynamoDBRepositories(basePackages = "com.myasinmanager.repository")
public class ServiceMyAsinManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceMyAsinManagerApplication.class, args);
	}

}
