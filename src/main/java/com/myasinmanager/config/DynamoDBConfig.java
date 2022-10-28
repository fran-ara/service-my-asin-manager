package com.myasinmanager.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.myasinmanager.repository")
public class DynamoDBConfig {

	@Bean
	  public DynamoDbClient getDynamoDbClient() {
	    AwsCredentialsProvider credentialsProvider = 
	              DefaultCredentialsProvider.builder()
	               .profileName("default")
	               .build();

	    return DynamoDbClient.builder()
	            .region(Region.US_WEST_2)
	            .credentialsProvider(credentialsProvider).build();
	  }
	  
	  @Bean
	  public DynamoDbEnhancedClient getDynamoDbEnhancedClient() {
	    return DynamoDbEnhancedClient.builder()
	                .dynamoDbClient(getDynamoDbClient())
	                .build();
	  }
}