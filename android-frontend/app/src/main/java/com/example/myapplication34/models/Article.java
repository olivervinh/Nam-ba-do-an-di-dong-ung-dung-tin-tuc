package com.example.myapplication34.models;

public class Article {
    private int id;
    private String title;
    private String content;
    private String imagepath;
    private String brief;
    private String status;
    private String likeArticle;
    private String saveArticle;
    private int idCategory;
    private String datePostArticle;

    public Article() {
    }

    public Article(int id, String title, String content, String imagepath, String brief, String status, String likeArticle, String saveArticle, int idCategory, String datePostArticle) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagepath = imagepath;
        this.brief = brief;
        this.status = status;
        this.likeArticle = likeArticle;
        this.saveArticle = saveArticle;
        this.idCategory = idCategory;
        this.datePostArticle = datePostArticle;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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

    public String getDatePostArticle() {
        return datePostArticle;
    }

    public void setDatePostArticle(String datePostArticle) {
        this.datePostArticle = datePostArticle;
    }
}
