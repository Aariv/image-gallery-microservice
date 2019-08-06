package com.pym.imagegallery.galleryservice.domain;

import java.util.List;

public class Gallery {
    private Integer id;
    private List<Object> images;


    public Gallery() {

    }

    public Gallery(Integer id, List<Object> images) {
        this.id = id;
        this.images = images;
    }

    public Integer getId() {
        return id;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }
}
