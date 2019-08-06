package com.pym.imagegallary.imageservice.controller;

import com.pym.imagegallary.imageservice.domain.Image;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageResource {

    @GetMapping("/images")
    public List<Image> getImages() {
        List<Image> images = Arrays.asList(new Image(1, "Tree House of Horror", "https://www.imdb.com/title/tt0096697/mediaviewer/rm3842005760"),
                new Image(1, "The Town", "https://www.imdb.com/title/tt0096697/mediaviewer/rm3698134272"),
                new Image(1, "The Last Traction Hero", "https://www.imdb.com/title/tt0096697/mediaviewer/rm1445594112"));
        return images;
    }
}
