package com.pym.imagegallary.eurekadiscoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * It is naming server or service registry. It's duty to give names to each microservice.
 *
 * Reason:
 * 	No Need to hardcode the ip addresses of microservice
 * 	What if services use dynamic ip addresses when we autoscale
 *
 * So every microservices registers itself with Eureka, and pings Eureka server to notify that it's alive
 *
 * If This Eureka server does't receive any notification from a service.
 */
@SpringBootApplication
@EnableEurekaServer // Enable Eureka Server
public class EurekaDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaDiscoveryServerApplication.class, args);
	}

}
