package com.pym.imagegallery.galleryservice.controller;

import com.pym.imagegallery.galleryservice.domain.Gallery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GalleryResource {

    @Autowired
    Environment env;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/gallery")
    public String home() {
        // This is useful for debugging
        // When having multiple instance of gallery service running at different ports.
        // We load balance among them, and display which instance received the request.
        return "Hello from Gallery Service running at port: " + env.getProperty("local.server.port");
    }

    @GetMapping("/gallery/{id}")
    public Gallery getGallery(@PathVariable Integer id) {
        // Create Gallery Object
        Gallery gallery = new Gallery();
        gallery.setId(id);
        /**
         * I did not mention the microservices with ip address and port
         * This image-service is registered in the Eureka Server and so this request goes to registry and routes to image service
         * For Load Balancing : Ribbon is used for load balancing.
         */
        List<Object> images = restTemplate.getForObject("http://image-service/api/images", List.class);
        gallery.setImages(images);
        return gallery;
     }

    @GetMapping("/admin")
    public String getAdmin() {
        return "This is admin area running at port: " + env.getProperty("local.server.port");
    }
}
