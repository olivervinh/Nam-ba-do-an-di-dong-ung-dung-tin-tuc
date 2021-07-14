package com.example.myapplication34;

public class Article {
    private int id;
    private String title;
    private String description;
    private String imagepath;
    private String summary;
    private String status;
    private String likeArticle;
    private String saveArticle;
    private int idCategory;

    public Article() {
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Article(int id, String title, String summary, String description, String imagepath, String status, String likeArticle, String saveArticle, int idCategory) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.description = description;
        this.imagepath = imagepath;
        this.status = status;
        this.likeArticle = likeArticle;
        this.saveArticle = saveArticle;
        this.idCategory = idCategory;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLikeArticle() {
        return likeArticle;
    }

    public void setLikeArticle(String likeArticle) {
        this.likeArticle = likeArticle;
    }

    public String getSaveArticle() {
        return saveArticle;
    }

    public void setSaveArticle(String saveArticle) {
        this.saveArticle = saveArticle;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
