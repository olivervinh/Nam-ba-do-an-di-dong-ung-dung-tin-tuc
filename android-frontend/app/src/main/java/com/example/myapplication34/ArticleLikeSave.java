package com.example.myapplication34;

public class ArticleLikeSave {
    private int  id;
    private String title;
    private String imagepath;

    public ArticleLikeSave(int id, String title, String imagepath) {
        this.id = id;
        this.title = title;
        this.imagepath = imagepath;
    }

    public ArticleLikeSave() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
