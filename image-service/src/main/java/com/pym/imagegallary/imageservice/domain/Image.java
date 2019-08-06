package com.pym.imagegallary.imageservice.domain;

public class Image {

    private Integer id;
    private String title;
    private String url;


    public Image() {

    }

    public Image(Integer id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
