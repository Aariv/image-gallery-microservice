package com.pym.imagegallery.galleryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * java -Dserver.port=8888 -jar target/gallery-service-0.0.1-SNAPSHOT.jar - Instance 0
 * java -Dserver.port=8889 -jar target/gallery-service-0.0.1-SNAPSHOT.jar - Instance 1
 * java -Dserver.port=8850 -jar target/gallery-service-0.0.1-SNAPSHOT.jar - -Instance 2
 *
 * These above will be registered in the Zuul and routes accordingly
 */
@SpringBootApplication
@EnableEurekaClient
public class GalleryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalleryServiceApplication.class, args);
	}

}

@Configuration
class RestTemplateConfig {

	@Bean
	@LoadBalanced // Load balance between service instances running at different ports
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
